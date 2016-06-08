package com.lightbringer.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class MapElem {
	
	MapElemType type;
	Sprite sprite;
	
	Body body;
	
	public MapElem(MapElemType type, TextureRegion texture, int x, int y){
		this.type = type;
		sprite = new Sprite(texture);
		sprite.setCenter(x, y);
		sprite.setSize(1.1f, 1.1f);
		if(type.isBlock()){
			
		}
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
}
