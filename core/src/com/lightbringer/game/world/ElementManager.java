package com.lightbringer.game.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ElementManager {
	
	TextureAtlas landAtlas;
	
	public void show(){
		landAtlas = new TextureAtlas("land.txt");
		
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
	
}
