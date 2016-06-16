package com.lightbringer.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.lightbringer.game.world.entities.items.AbstractItem;

public abstract class Character extends Entity {

	// Equip slots
	public static final int HEAD = 0, CHEST = 1, LEGS = 2, ARMS = 3, HANDS = 4, 
			COAT = 5, BELT = 6, BACK = 7, NECK = 8, FACE = 9, 
			P_HAND = 10, O_HAND = 11, B_HANDS = 12; 
	
	protected Vector2 dirVec;
	
	protected Species species;
	private Array<TextureRegion> bodyTextures;
	protected Array<AbstractItem> inventory;
	protected ArrayMap<Integer, AbstractItem> equipped;
	protected Array<Sprite> sprites;
//	protected ArrayMap<String, Integer> attributes;

	public Character(Body body, Array<TextureRegion> textures, Species species) {
		super(body, textures.first(), species.getSize());
		
		bodyTextures = textures;
		
		sprites = new Array<Sprite>();
		sprites.add(sprite);
		
//		attributes = new ArrayMap<String, Integer>();
		
		inventory = new Array<AbstractItem>();
		equipped = new ArrayMap<Integer, AbstractItem>();
		
		for(TextureRegion texture : textures){
			if(texture != textures.first()){
				Sprite sprite = new Sprite(texture);
				sprite.setBounds(body.getPosition().x - species.getSize() / 2, body.getPosition().y - species.getSize() / 2, species.getSize(), species.getSize());
				sprite.setOriginCenter();
				sprites.add(sprite);
			}
						
		}
		
		this.species = species;
		
		dirVec = new Vector2();
	}
	
	public void draw(SpriteBatch batch){
		for(Sprite sprite: sprites){
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);			
		}
		for(AbstractItem item : equipped.values()){
			item.draw(batch);
		}
	}

	public abstract void update(float delta);
	
	public float getSize(){
		return species.getSize();
	}
	
	public float getDirection(){
		Gdx.app.log("Character", "DirVec" + dirVec);
		float angle = (float) (MathUtils.radiansToDegrees * Math.atan2(dirVec.y, dirVec.x));
		Gdx.app.log("Character","" + angle);
	    if(angle < 0){
	        angle += 360;
	    }
		return angle;
	}
	
}
