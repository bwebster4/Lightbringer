package com.lightbringer.game.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ElementManager {
	
	MapLoc[][] mapLocations;
	TextureAtlas landAtlas;
	
	public ElementManager(int worldSize){
		mapLocations = new MapLoc[worldSize][worldSize];
		
	}
	
	public void show(){
		landAtlas = new TextureAtlas("land.txt");
	}
	
	public TextureRegion getTexture(String name){
		return landAtlas.findRegion(name);
	}
	
	public void dispose(){
		landAtlas.dispose();
	}
	
}
