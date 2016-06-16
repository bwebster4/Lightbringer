package com.lightbringer.game.world.entities;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.lightbringer.game.world.InputHandler;
import com.lightbringer.game.world.WorldScreen;
import com.lightbringer.game.world.entities.items.Item;
import com.lightbringer.game.world.entities.items.LightItem;

public class ElementManager {
	
	public static final int RAYS_PER_BALL = 128;
	static final short C_CATEGORY_CHARACTERS = 0x0002;
	static final short C_CATEGORY_ENVIRONMENT = 0x001;
	
	WorldScreen worldScreen;
	InputHandler input;
	TextureAtlas landAtlas, charAtlas;
	World world;
	private RayHandler rayHandler;
	private Array<Light> lights;
	
	ArrayMap<String, Layer> layers;
	ArrayMap<String, Character> characters;
	MapElem emptyDummyElem;
	
	int worldSize;
	
	public void show(World world, int worldSize, RayHandler rayHandler, WorldScreen worldScreen, InputHandler input){
		this.world = world;
		this.worldScreen = worldScreen;
		this.worldSize = worldSize;
		this.input = input;
		this.rayHandler = rayHandler;
		
		landAtlas = new TextureAtlas("land.txt");
		charAtlas = new TextureAtlas("characters.txt");
		
		try {
			emptyDummyElem = new MapElem(MapElemType.Empty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Filter filter = new Filter();
		filter.maskBits = ~C_CATEGORY_CHARACTERS;
		
		lights = new Array<Light>();
		Light.setGlobalContactFilter(filter);

		characters = new ArrayMap<String, Character>();
		
		Player player = (Player) createCharacter(new Vector2(5, 5),
				new String[]{"character0", "character94", "character34"}, Species.Human, true);
		characters.insert(characters.size, "Player", player);
		
		Thread genThread = new Thread(new WorldGen(worldSize, world));
		genThread.start();
	}
	public void update(float delta){
		for(Character character : characters.values()){
			character.update(delta);
		}
	}
	
	public void render(Rectangle area, SpriteBatch batch){
		for(Layer layer : layers.values()){
			layer.render(area, batch);
		}
		
		for(Character character : characters.values()){
			character.draw(batch);
		}
	}
	
	public Character createCharacter(Vector2 pos, String[] textureNames, Species species, boolean isPlayer){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y);
		bodyDef.fixedRotation = true;
		
		Body body = world.createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox((.9f * species.getSize()) / 2, (.9f * species.getSize()) / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 1f;
		fixtureDef.friction = .1f;
		fixtureDef.restitution = .1f;
		fixtureDef.filter.categoryBits = C_CATEGORY_CHARACTERS;

		body.createFixture(fixtureDef);
		box.dispose();
		
		Array<TextureRegion> textures = new Array<TextureRegion>();
		for(String textureName : textureNames){
			textures.add(getCharacterTexture(textureName));
		}
		
		if(isPlayer){
			Player player = new Player(body, textures, species);
			player.setInput(input);
			
			LightItem flashlight = new LightItem(Item.Flashlight, player, getCharacterTexture(Item.Flashlight.getTextureName()), rayHandler);
			player.addItem(flashlight);
			player.equipItem(0);
			
			return player;
		}else{
			return null;
//			return new Character(pos, body, getCharacterTexture(textureName), species);
		}
	}
	
	public MapElem createMapElem(MapElemType type, int x, int y){
		return new MapElem(type, getLandTexture(type.getTextureName()), x, y);
	}
	public MapElem createMapElem(MapElemType type, Body body, int x, int y){
		return new MapElem(type, world, body, getLandTexture(type.getTextureName()), x, y);
	}
	
	public MapElem getElem(String layerName, int x, int y){

		try{
			MapElem elem =  layers.get(layerName).elems[x][y];
			if(elem == null){
				return emptyDummyElem;
			}else
				return elem;
		}catch(ArrayIndexOutOfBoundsException e){
			return emptyDummyElem;
		}
	}
	
	public Vector2 getPlayerPos(){
		return characters.get("Player").body.getPosition();
	}
	public Player getPlayer(){
		return (Player) characters.get("Player");
	}
	
	public int findTextureType(boolean[] areBlocks){
		if(areBlocks[3] == areBlocks[1] ==  areBlocks[2] == areBlocks[0] == true)
			return 4;
		if(areBlocks[3] == areBlocks[1] ==  areBlocks[2] == true && areBlocks[0] == false)
			return 1;
		if(areBlocks[3] == areBlocks[0] ==  areBlocks[2] == true && areBlocks[1] == false)
			return 3;
		if(areBlocks[3] == areBlocks[1] ==  areBlocks[0] == true && areBlocks[2] == false)
			return 5;
		if(areBlocks[0] == areBlocks[1] ==  areBlocks[2] == true && areBlocks[3] == false)
			return 7;
		if(areBlocks[0] == areBlocks[1] == false && areBlocks[2] == areBlocks[3] == true)
			return 0;
		if(areBlocks[0] == areBlocks[2] == false && areBlocks[1] == areBlocks[3] == true)
			return 2;
		if(areBlocks[1] == areBlocks[3] == false && areBlocks[2] == areBlocks[0] == true)
			return 6;
		if(areBlocks[0] == areBlocks[1] == true && areBlocks[2] == areBlocks[3] == false)
			return 8;
		return 4;
	}
	
	public void updateTextures(int[] bounds, String layerName){
		/* Texture Indices
		 * 0 1 2
		 * 3 4 5
		 * 6 7 8
		 * 
		 * areBlocks indices
		 * _ 0 _
		 * 1 _ 2
		 * _ 3 _
		 */		
		Layer layer = layers.get(layerName);
		
		boolean[] areBlocks = new boolean[4];
		for(int x = bounds[0]; x <= bounds[1]; x++){
			for (int y = bounds[2]; y <= bounds[3]; y++){
				MapElem elem = layer.elems[x][y];
				if(elem != null){
					areBlocks[0] = (elem.type == getElem(layerName, x, y + 1).type);
					areBlocks[1] = (elem.type == getElem(layerName, x - 1, y).type);
					areBlocks[2] = (elem.type == getElem(layerName, x + 1, y).type);
					areBlocks[3] = (elem.type == getElem(layerName, x, y - 1).type);
					
					elem.updateTexture(getLandTexture(elem.type.getTextureNameAt(findTextureType(areBlocks))));
				}
			}
		}
	}
	
	public void setLayers(ArrayMap<String, Layer> layers){
		this.layers = layers;
	}
	
	private TextureRegion getLandTexture(String name){
		return landAtlas.findRegion(name);
	}
	
	private TextureRegion getCharacterTexture(String name){
		return charAtlas.findRegion(name);
	}
	
	public void dispose(){
		landAtlas.dispose();
		charAtlas.dispose();
	}
	
	private class Layer{
		MapElem elems[][];
		String name;

		public Layer(int size, String name){
			elems = new MapElem[size][size];
			this.name = name;
		}
		
		public void render(Rectangle area, SpriteBatch batch){		
			for(int x = (int) (area.x - 1); x < area.x + area.width + 1; x++){
				for(int y = (int)( area.y - 1); y < area.y + area.height + 1; y++){
					try{
					if(elems[x][y] != null)
						elems[x][y].draw(batch);
					}catch(IndexOutOfBoundsException e){
						
					}
				}
			}
			
			
		}
		
		public String getName() {
			return name;
		}
		
		public void addElem(MapElem elem, int x, int y){
			elems[x][y] = elem;
		}
	}
	
	private class WorldGen implements Runnable{

		ArrayMap<String, Layer> layers;
		int size;
		World world;
		
		public WorldGen(int size, World world){
			this.size = size;
			this.world = world;
		}
		
		@Override
		public void run() {
			layers = new ArrayMap<String, Layer>();
			layers.insert(layers.size, "Ground", new Layer(size, "Ground"));
			layers.insert(layers.size, "Blocks", new Layer(size, "Blocks"));
			
			BodyDef stoneDef = new BodyDef();
			stoneDef.type = BodyType.StaticBody;
		
			
			PolygonShape square = new PolygonShape();
			square.setAsBox(.5f, .5f);
			

			Body stone;
			
			
			for(int x = 0; x < size; x++){
				for(int y = 0; y < size; y++){
					layers.get("Ground").addElem(createMapElem(MapElemType.Ground, x, y), x, y);
					if ((x < 3 || x > size - 4) || (y < 3 || y > size - 4)){
						stoneDef.position.set(x, y);
						stone = world.createBody(stoneDef);
						stone.createFixture(square, 0.0f);
						layers.get("Blocks").addElem(createMapElem(MapElemType.Stone, stone, x, y), x, y);
					}
				}
			}			
			setLayers(layers);
			
			square.dispose();
			
			updateTextures(new int[]{1, size - 2, 1, size - 2}, "Blocks");
			
			worldScreen.setState(WorldScreen.PLAY_STATE);
		}
		
		
	}
}
