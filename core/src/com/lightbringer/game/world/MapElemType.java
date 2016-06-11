package com.lightbringer.game.world;

public enum MapElemType {

	Ground("Ground", "land0006", false, false),
	Stone("Stone", new String[]{"land0828", "land0829", "land0830", "land0884", "land0885", "land0886", "land0938", "land0939", "land0940"}, true, true);

	private String name, textureName;
	private String[] textureNames;
	private boolean isBlock, hasMultiple;
	MapElemType(String name, String textureName, boolean isBlock, boolean hasMultiple){
		this.name = name;
		this.isBlock = isBlock;
		this.textureName = textureName;
		this.hasMultiple = hasMultiple;
	}
	MapElemType(String name, String[] textureNames, boolean isBlock, boolean hasMultiple){
		this.name = name;
		this.isBlock = isBlock;
		this.hasMultiple = hasMultiple;
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
