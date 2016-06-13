package com.lightbringer.game.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public abstract class Character extends Entity {

	protected Species species;
	private Array<TextureRegion> bodyTextures;

	public Character(Body body, TextureRegion texture, Species species) {
		super(body, texture, species.getSize());
		
		bodyTextures = new Array<TextureRegion>();
		bodyTextures.add(texture);
		
		this.species = species;
		
	}

	public abstract void update(float delta);
	
	
}
