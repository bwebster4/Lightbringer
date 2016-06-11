package com.lightbringer.game.world;

public enum MapElemType {

	Ground("Ground", "land0006", false),
	Stone("Stone", new String[]{"land0828", "land0829", "land0830", "land0884", "land0885", "land0886", "land0938", "land0939", "land0940"}, true),
	Empty("Empty", "", false);
	
	private String name, textureName;
	private String[] textureNames;
	private boolean isBlock;
	MapElemType(String name, String textureName, boolean isBlock){
		this.name = name;
		this.isBlock = isBlock;
		this.textureName = textureName;
	}
	MapElemType(String name, String[] textureNames, boolean isBlock){
		this.name = name;
		this.isBlock = isBlock;
		this.textureName = textureNames[4];
		this.textureNames = textureNames;
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
	public String getTextureNameAt(int index){
		return textureNames[index];
	}
}
