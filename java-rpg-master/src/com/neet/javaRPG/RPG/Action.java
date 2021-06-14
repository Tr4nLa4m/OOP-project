package com.neet.javaRPG.RPG;

import com.neet.javaRPG.Entity.Combatant;
import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Player;
import com.neet.javaRPG.TileMap.TileMap;

public class Action {

	private static int old_X,old_Y,new_X, new_Y;


	public static void attackAction(Combatant attacker, Combatant defender) {
		int healthLost = attacker.getATK() - defender.getDEF();
		if(healthLost <= 0)
			healthLost = 1;
		defender.changeHP(-1 * healthLost);
	}
	
	public static void drinkHealthPot(Player player) {
		player.changeNumHealthPot(-1);
		player.changeHP(50);
	}
	
	public static void drinkManaPot(Player player) {
		player.changeNumManaPot(-1);
		player.changeMP(30);
	}
	
	public static void useSkill(Skill skill, Combatant attacker, Combatant defender) {
		skill.execute(attacker, defender);
	}

	public static void Combat(Player player, Enemy enemy) {
		if (player.getSBounds().intersects(enemy.getBound())) {
			enemy.setMonster_infor(true);
			if (player.isCanCombat()) {
				enemy.changeHP(-1 * player.getATK());
				player.setCombat(false);
			}
			old_X = player.getx() / 32;
			old_Y = player.gety() / 32;
			new_X = enemy.getx() / 32;
			new_Y = enemy.gety() / 32;
			}
		if (player.intersects(enemy)) {
			if(enemy.getTypeEnemy() == 0) {
					if (player.isRight()) player.setTilePosition(old_X - 1, old_Y);
					else if (player.isLeft()) player.setTilePosition(old_X, old_Y);
					else if (player.isDown()) player.setTilePosition(old_X, old_Y - 1);
					else if (player.isUp()) player.setTilePosition(old_X, old_Y + 1);
					player.changeHP(-1 * enemy.getATK());
			}
			else {
					player.attackedStatic(enemy.getATK());
			}

		}

		//level up
		if(player.canLevelUp())	{	player.levelUp();}
		// use skill / HP, MP


		}

}

