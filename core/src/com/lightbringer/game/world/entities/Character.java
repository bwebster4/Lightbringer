package com.lightbringer.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.lightbringer.game.world.entities.items.AbstractItem;

public abstract class Character extends Entity {

	// Equip slots
	public static final int HEAD = 0, CHEST = 1, LEGS = 2, ARMS = 3, HANDS = 4, 
			COAT = 5, BELT = 6, BACK = 7, NECK = 8, FACE = 9, 
			P_HAND = 10, O_HAND = 11, B_HANDS = 12; 
	
	protected Species species;
	private Array<TextureRegion> bodyTextures;
	protected Array<AbstractItem> items;
	protected Array<Sprite> sprites;

	public Character(Body body, Array<TextureRegion> textures, Species species) {
		super(body, textures.first(), species.getSize());
		
		bodyTextures = textures;
		
		sprites = new Array<Sprite>();
		sprites.add(sprite);
		
		for(TextureRegion texture : textures){
			if(texture != textures.first()){
				Sprite sprite = new Sprite(texture);
				sprite.setBounds(body.getPosition().x - species.getSize() / 2, body.getPosition().y - species.getSize() / 2, species.getSize(), species.getSize());
				sprite.setOriginCenter();
				sprites.add(sprite);
			}
						
		}
		
		this.species = species;
		
	}
	
	public void draw(SpriteBatch batch){
		for(Sprite sprite: sprites){
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);			
		}
	}

	public abstract void update(float delta);
	
	public float getSize(){
		return species.getSize();
	}
	
}
