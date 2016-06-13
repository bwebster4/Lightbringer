package com.lightbringer.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lightbringer.game.world.InputHandler;

public class Player extends Character{

	InputHandler input;
	
	public Player(Body body, TextureRegion texture, Species species) {
		super(body, texture, species);
		// TODO Auto-generated constructor stub
	}
	
	public void setInput(InputHandler input){
		this.input = input;
	}

	public void update(float delta){
		Vector2 vel = body.getLinearVelocity();
		
		if(input.isWPressed() && vel.y < species.getDefaultSpeed()){
			body.applyLinearImpulse(0, species.getDefaultSpeed() * delta, body.getPosition().x, body.getPosition().y, true);
		}
		else if(input.isSPressed() && vel.y > - species.getDefaultSpeed()){
			body.applyLinearImpulse(0, -species.getDefaultSpeed() * delta, body.getPosition().x, body.getPosition().y, true);
		}
		else{
			body.applyLinearImpulse(0, -vel.y * delta, body.getPosition().x, body.getPosition().y, true);
		}
		if(input.isAPressed() && vel.x > - species.getDefaultSpeed()){
			body.applyLinearImpulse(- species.getDefaultSpeed() * delta, 0, body.getPosition().x, body.getPosition().y, true);
		}
		else if(input.isDPressed() && vel.x < species.getDefaultSpeed()){
			body.applyLinearImpulse(species.getDefaultSpeed() * delta, 0, body.getPosition().x, body.getPosition().y, true);
		}else{
			body.applyLinearImpulse(- vel.x * delta, 0, body.getPosition().x, body.getPosition().y, true);
		}	
				
	}
	
}
