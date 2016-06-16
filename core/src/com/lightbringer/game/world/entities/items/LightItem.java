package com.lightbringer.game.world.entities.items;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lightbringer.game.world.entities.Character;
import com.lightbringer.game.world.entities.ElementManager;


public class LightItem extends AbstractItem{

	Light light;
	
	public LightItem(Item item, Character character, TextureRegion texture, RayHandler rayHandler) {
		super(item, character, texture);
		
		if(item.getLightType() == "Cone"){
			light = new ConeLight(rayHandler, ElementManager.RAYS_PER_BALL, null, item.getRange(), 0, 0, 0, 45);
		}else if(item.getLightType() == "Point"){
			light = new PointLight(rayHandler, ElementManager.RAYS_PER_BALL, null, item.getRange(), 0f, 0f);
		}
		light.setColor(1f, 1f, 1f, 1f);
//		light.attachToBody(character.body);
		light.setSoftnessLength(1);
//		light.setIgnoreAttachedBody(true);
	}
	
	public void update(float delta){
		light.setPosition(character.getPosition());
		light.setDirection(character.getDirection());
	}
	
}
