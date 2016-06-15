package com.lightbringer.game.world.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity{
	
	public static final float SIZE_PROJECTILE = .08f,
				SIZE_MEDIUM_S = .5f, SIZE_MEDIUM_M = .73f, SIZE_MEDIUM_L = .93f;
	
	public Body body;
	Sprite sprite;
	
	public Entity(Body body, TextureRegion texture, float size){
		this.body = body;
		sprite = new Sprite(texture);
		sprite.setBounds(body.getPosition().x - size / 2, body.getPosition().y - size / 2, size, size);
		sprite.setOriginCenter();
	}
	
	public abstract void update(float delta);
	
	public void draw(SpriteBatch batch){
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

		sprite.draw(batch);
	}
	
	public Vector2 getPosition(){
		return body.getPosition();
	}
	
	
	
}
