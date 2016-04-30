package com.lightbringer.game.world;

import com.badlogic.gdx.utils.Array;

public class Map {

	Array<Layer> layers;
	int size;
	
	public void load(){
		
		layers = new Array<Layer>();
		
		
	}
	
	
	private class Layer {
		
		MapElem elems[][];
		
		public Layer(int size){
			elems = new MapElem[size][size];
		}
		
	}
}
