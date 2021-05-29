package com.neet.javaRPG.RPG.ImplementSkill;

import com.neet.javaRPG.Entity.Combatant;
import com.neet.javaRPG.RPG.Skill;

public class PowerAttack extends Skill {
	private int baseMultiplier = 2;
	
	public PowerAttack(int baseDamage) {
		super(5, baseDamage, "Power Attack");
	}
	
	@Override
	public void execute(Combatant attacker, Combatant defender) {
		attacker.changeMP(-1 * mpCost);
		int multiplier = baseMultiplier + (int)attacker.getLevel()/3;
		int atk = attacker.getATK() * multiplier;
		int def = defender.getDEF();
		
		defender.changeHP(-1 * (atk - def));
	}

}
