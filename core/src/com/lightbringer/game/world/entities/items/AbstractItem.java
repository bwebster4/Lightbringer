package com.lightbringer.game.world.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.lightbringer.game.world.entities.Character;

public abstract class AbstractItem {

	private Item item;
	private Character character;
	private Sprite sprite;
	protected ArrayMap<String, Integer> attributes;
	
	public AbstractItem(Item item, Character cha, TextureRegion texture){
		this.item = item;
		this.character = cha;
		
		sprite = new Sprite(texture);
		sprite.setSize(character.getSize(), character.getSize());
		sprite.setOriginCenter();
		
		attributes = new ArrayMap<String, Integer>();
	}
	
	public void initAttr(){
		attributes.insert(attributes.size, "Health", item.getHealth());
	}
	
	public abstract void update(float delta);
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public Item getItem(){
		return item;
	}

}
