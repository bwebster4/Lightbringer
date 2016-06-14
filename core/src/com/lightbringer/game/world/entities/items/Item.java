package com.lightbringer.game.world.entities.items;

import com.lightbringer.game.world.entities.Character;

public enum Item {
	
	// Utility Items
	Flashlight("Flashlight", Character.O_HAND, "Cone", 10f, 0),
	
	// Weapons
	EnergyPistol("Energy Pistol", Character.P_HAND, "None", 15f, 0);
	
	int equipSlot;
	String name, lightType;
	float range;
	int armor;

	private Item(String name, int equipSlot, String lightType, float range, int armor){
		this.name = name;
		this.equipSlot = equipSlot;
		this.lightType = lightType;
		this.range = range;
		this.armor = armor;
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
	
}
