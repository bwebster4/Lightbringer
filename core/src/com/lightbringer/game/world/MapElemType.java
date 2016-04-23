package com.lightbringer.game.world;

public enum MapElemType {

	Ground("Ground", "land0006.png", true);
	

	private String name, textureName;
	private boolean isWalkable;
	MapElemType(String name, String textureName, boolean isWalkable){
		this.name = name;
		this.isWalkable = isWalkable;
	}
	public String getName() {
		return name;
	}
	public boolean isWalkable() {
		return isWalkable;
	}
	public String getTextureName() {
		return textureName;
	}
}
