package com.lightbringer.game.world.entities.items;

import com.lightbringer.game.world.entities.Character;

public enum Item {
	
	// Utility Items
	Flashlight("Flashlight", "character424", Character.O_HAND, "Cone", 10f, 0, 6, 2),
	
	// Weapons
	EnergyPistol("Energy Pistol", "character429", Character.P_HAND, "None", 15f, 0, 10, 3);
	
	int equipSlot;
	String name, lightType, textureName;
	float range;
	int armor, health, toughness;

	private Item(String name, String textureName, int equipSlot, String lightType, float range, 
			int armor, int health, int toughness){
		this.name = name;
		this.textureName = textureName;
		this.equipSlot = equipSlot;
		this.lightType = lightType;
		this.range = range;
		this.armor = armor;
		this.health = health;
		this.toughness = toughness;
	}
	public float getRange() {
		return range;
	}
	public int getArmor() {
		return armor;
	}
	public int getHealth() {
		return health;
	}
	public int getToughness() {
		return toughness;
	}
	public int getEquipSlot() {
		return equipSlot;
	}
	public String getName() {
		return name;
	}
	public String getLightType() {
		return lightType;
	}
	public String getTextureName(){
		return textureName;
	}
	
}
