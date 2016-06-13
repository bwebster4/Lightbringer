package com.lightbringer.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class MapElem {
	
	MapElemType type;
	Sprite sprite;
	
	Body body;
	
	public MapElem(MapElemType type) throws Exception{
		/*
		 * Used for creating empty dummy elements
		 */
		if(type == MapElemType.Empty){
			this.type = type;
		}else{
			throw new Exception("Tried to create non-empty MapElem without necessary parameters");
		}
	}
	
	public MapElem(MapElemType type, TextureRegion texture, int x, int y){
		this.type = type;
		sprite = new Sprite(texture);
		sprite.setBounds(x - .55f, y - .55f, 1.1f, 1.1f);
	}
	public MapElem(MapElemType type, World world, Body body, TextureRegion texture, int x, int y){
		this.type = type;
		sprite = new Sprite(texture);
		sprite.setBounds(x - .55f, y - .55f, 1.1f, 1.1f);
		if(type.isBlock()){
			this.body = body;
		}
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public void updateTexture(TextureRegion texture){
		sprite.setRegion(texture);
		
	}
}
