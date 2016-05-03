package com.lightbringer.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class ElementManager {
	
	TextureAtlas landAtlas;
	World world;
	
	public void show(World world){
		landAtlas = new TextureAtlas("land.txt");
		this.world = world;
		
	}
	
	public MapElem createMapElem(MapElemType type, int x, int y){
		return new MapElem(type, getLandTexture(type.getTextureName()), x, y);
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
		}
		
		
	}
}
