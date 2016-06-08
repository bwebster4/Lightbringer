package com.lightbringer.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

public class ElementManager {
	
	WorldScreen worldScreen;
	TextureAtlas landAtlas;
	World world;
	ArrayMap<String, Layer> layers;
	
	public void show(World world, WorldScreen worldScreen){
		landAtlas = new TextureAtlas("land.txt");
		this.world = world;
		this.worldScreen = worldScreen;
		
		Thread genThread = new Thread(new WorldGen(256));
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
//			The camera rectangle has incorrect values. This will need to be fixed
			for(int x = (int) area.x- 8; x < area.x + area.width - 4; x++){
				for(int y = (int) area.y - 2; y < area.y + area.height; y++){
					elems[x][y].draw(batch);
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
		
		public WorldGen(int size){
			this.size = size;
		}
		
		@Override
		public void run() {
			layers = new ArrayMap<String, Layer>();
			layers.insert(layers.size, "Ground", new Layer(size, "Ground"));
			for(int x = 0; x < size; x++){
				for(int y = 0; y < size; y++){
					layers.get("Ground").addElem(createMapElem(MapElemType.Ground, x, y), x, y);
				}
			}
			
			
			setLayers(layers);
			worldScreen.setState(WorldScreen.PLAY_STATE);
		}
		
		
	}
}
