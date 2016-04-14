package com.lightbringer.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class MapLoc {
	
	int x, y;
	Array<MapElem> elements;
	
	public MapLoc(int x, int y){
		this.x = x;
		this.y = y;
		
		elements = new Array<MapElem>();
	}
	
	
	public void draw(SpriteBatch batch){
		while(elements.iterator().hasNext()){
			elements.iterator().next().draw(batch);
		}
	}
	
	public void addElement(MapElem element){
		elements.add(element);
	}
	
}
