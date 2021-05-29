package com.neet.javaRPG.RPG;

import com.neet.javaRPG.Entity.Combatant;
import com.neet.javaRPG.Entity.Player;

public class Action {
	public static void attackAction(Combatant attacker, Combatant defender) {
		int healthLost = attacker.getATK() - defender.getDEF();
		if(healthLost <= 0)
			healthLost = 1;
		defender.changeHP(-1 * healthLost);
	}
	
	public static void drinkHealthPot(Player player) {
		player.changeNumHealthPot(-1);
		player.changeHP(5 + 3 * (player.getLevel() - 1));
	}
	
	public static void drinkManaPot(Player player) {
		player.changeNumManaPot(-1);
		player.changeMP(5 + 2 * (player.getLevel() - 1));
	}
	
	public static void useSkill(Skill skill, Combatant attacker, Combatant defender) {
		skill.execute(attacker, defender);
	}
	
	public static boolean retreat() {
		return Math.random() < 0.9;
	}
}
