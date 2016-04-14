package com.lightbringer.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapElem {
	
	MapElemType type;
	Sprite sprite;
	
	public MapElem(MapElemType type, int x, int y){
		this.type = type;
		sprite = new Sprite(); // Need to implement a texture managing class
		sprite.setCenter(x, y);
		sprite.scale(1f);
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
}
