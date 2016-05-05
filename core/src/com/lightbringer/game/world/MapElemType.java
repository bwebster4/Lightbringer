package com.lightbringer.game.world;

public enum MapElemType {

	Ground("Ground", "land0006", false);
	

	private String name, textureName;
	private boolean isBlock;
	MapElemType(String name, String textureName, boolean isWalkable){
		this.name = name;
		this.isBlock = isWalkable;
		this.textureName = textureName;
	}
	public String getName() {
		return name;
	}
	public boolean isBlock() {
		return isBlock;
	}
	public String getTextureName() {
		return textureName;
	}
}
