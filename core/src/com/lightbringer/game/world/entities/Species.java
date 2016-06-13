package com.lightbringer.game.world.entities;

public enum Species {
	
	Human(Entity.SIZE_MEDIUM_M, 3f);
	
	private float size, speed;

	private Species(float size, float speed){
		this.size = size;
		this.speed = speed;
	}
	
	public float getSize() {
		return size;
	}
	
	public float getDefaultSpeed(){
		return speed;
	}
	
}
