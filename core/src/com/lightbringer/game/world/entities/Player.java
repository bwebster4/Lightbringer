package com.lightbringer.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.lightbringer.game.world.InputHandler;
import com.lightbringer.game.world.entities.items.AbstractItem;
import com.lightbringer.game.world.entities.items.LightItem;

public class Player extends Character{

	InputHandler input;
	
	public Player(Body body, Array<TextureRegion> textures, Species species) {
		super(body, textures, species);

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
		

		dirVec.set(getPosition().add(input.getMousePos().scl(-1f))).scl(-1);
		
		
		for(AbstractItem item : equipped.values()){
			item.update(delta);
		}
				
	}
	
	public void addItem(AbstractItem item){
		inventory.add(item);
	}
	
	public void equipItem(int index){
		AbstractItem item = inventory.removeIndex(index);
		equipped.insert(equipped.size, item.getItem().getEquipSlot(), item);
	}
	
}
