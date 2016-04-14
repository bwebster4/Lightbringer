package com.lightbringer.game.world;

public enum MapElemType {

	Ground("Ground", true);
	
	private String name;
	private boolean isWalkable;
	MapElemType(String name, boolean isWalkable){
		this.name = name;
		this.isWalkable = isWalkable;
	}
	public String getName() {
		return name;
	}
	public boolean isWalkable() {
		return isWalkable;
	}

}
