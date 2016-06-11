package com.lightbringer.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

public class ElementManager {
	
	WorldScreen worldScreen;
	TextureAtlas landAtlas;
	World world;
	ArrayMap<String, Layer> layers;
	MapElem emptyDummyElem;
	
	int worldSize;
	
	public void show(World world, int worldSize, WorldScreen worldScreen){
		landAtlas = new TextureAtlas("land.txt");
		this.world = world;
		this.worldScreen = worldScreen;
		this.worldSize = worldSize;
		
		try {
			emptyDummyElem = new MapElem(MapElemType.Empty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Thread genThread = new Thread(new WorldGen(worldSize, world));
		genThread.start();
	}
	
	public void render(Rectangle area, SpriteBatch batch){
		for(Layer layer : layers.values()){
			layer.render(area, batch);
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
					
					if(x == 2){
						Gdx.app.log("EM", "areBlocks: " + areBlocks[0] + " "+ areBlocks[1] + " "+ areBlocks[2] + " "+ areBlocks[3]);
						Gdx.app.log("EM", "Type: " + findTextureType(areBlocks));
						Gdx.app.log("EM", "textureName: " + elem.type.getTextureNameAt(findTextureType(areBlocks)));
					}
					
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
	
	public void dispose(){
		landAtlas.dispose();
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
			
			updateTextures(new int[]{1, size - 2, 1, size - 2}, "Blocks");
			
			worldScreen.setState(WorldScreen.PLAY_STATE);
		}
		
		
	}
}
