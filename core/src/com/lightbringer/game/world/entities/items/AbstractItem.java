package com.lightbringer.game.world.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lightbringer.game.world.entities.Character;

public abstract class AbstractItem {

	private Item item;
	private Character character;
	private Sprite sprite;
	
	public AbstractItem(Item item, Character cha, TextureRegion texture){
		this.item = item;
		this.character = cha;
		
		sprite = new Sprite(texture);
		sprite.setSize(character.getSize(), character.getSize());
		sprite.setOriginCenter();
	}
	
	public void draw(SpriteBatch batch){
		sprite.setPosition(character.getPosition().x, character.getPosition().y);

		sprite.draw(batch);
	}
	
	public Item getItem(){
		return item;
	}

}
