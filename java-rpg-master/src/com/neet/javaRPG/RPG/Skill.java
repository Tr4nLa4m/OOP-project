package com.neet.javaRPG.RPG;

import com.neet.javaRPG.Entity.Combatant;

public abstract class Skill {
	protected int mpCost;
	protected int baseDamage;
	protected String name;
	
	public Skill(int mpCost, int baseDamage, String name) {
		this.mpCost = mpCost;
		this.baseDamage = baseDamage;
		this.name = name;
	}

	// return true when successfully executed
	// false otherwise
	public abstract void execute(Combatant attacker, Combatant defender);
	
	public boolean canCast(Combatant user) {
		return user.getCurrentMP() >= mpCost;
	}
	
	public String getName() {
		return name;
	}
}
