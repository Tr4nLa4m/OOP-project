package com.neet.javaRPG.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Player;
import com.neet.javaRPG.Main.GamePanel;
import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;
import com.neet.javaRPG.RPG.Action;
import com.neet.javaRPG.RPG.Skill;

public class CombatState extends GameState {

	private Player player;
//	private String infoString;
	
	private Enemy enemy;
//	private String infoString;
	
	private String enemyString;
	private String playerString;
	
	private PlayState playState;
	
	private int yOffset;
	
	private int option;
	private String[] optionList = new String[] {"Attack",
			"P Atk",
			"HP pot",
			"MP pot",
			"Retreat"
	};
	
	public CombatState(GameStateManager gsm, Player player, Enemy enemy) {
		super(gsm);
		this.player = player;
		this.enemy = enemy;
		init();
	}
	
	public CombatState(GameStateManager gsm) {
		super(gsm);
		this.player = null;
		this.enemy = null;
		init();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public void setPlayState(PlayState ps) {
		this.playState = ps;
		this.player = ps.getPlayer();
		this.enemy = ps.getEnemy();
	}
	
	public void init() {
		yOffset = 0;
		option = 0;
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		playState.draw(g);
		enemy.draw(g);
		
		//redraw hp, mp bar
		g.setColor(new Color(255, 0, 0)); //red
		g.fillRect(87, yOffset + 26, (int)(36 * player.getCurrentHP() / player.getMaxHP()), 5);
		Content.drawString(g, "HP", 81, yOffset + 18);
		
		g.setColor(new Color(0, 0, 255));
		g.fillRect(87, yOffset + 39, (int)(36 * player.getCurrentMP() / player.getMaxMP()), 5);
		Content.drawString(g, "MP", 81, yOffset + 32);
		
		Content.drawString(g, "Lv. " + player.getLevel(), 81, yOffset + 4);
		
		//Combat option
		Content.drawString(g, optionList[option], 14, yOffset + 4);
		g.drawImage(Content.DIAMOND[0][0], 0, yOffset + 2, null);
		if(enemyString != null)
			Content.drawString(g, enemyString, 0, yOffset + 36);
		if(playerString != null)
			Content.drawString(g, playerString, 0, yOffset + 20);
	}
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && option < optionList.length - 1) {
			option++;
		}
		if(Keys.isPressed(Keys.UP) && option > 0) {
			option--;
		}
		if(Keys.isPressed(Keys.ENTER)) {
			playerOption();
			enemyAction();
			delay();
		}
	}

	private void enemyAction() {
		if(enemy.isDead()) {
			gsm.setCombat(false);
//			enemyString = "Enemy is dead";
			playState.enemyDefeat();
			return;
		}
		double rng = Math.random();
		if(!enemy.canUseSkill()) {
			Action.attackAction(enemy, player);
			enemyString = "M: attack";
		}
			
		else if(rng < 0.75) {
			Action.attackAction(enemy, player);
			enemyString = "M: attack";
		}
		else {
			Skill skill = enemy.useSkill();
			Action.useSkill(skill, enemy, player);
			enemyString = "M: P Atk";
		}
	}
	
	private void playerOption() {
		if(player.isDead()) {
			playerString = "Game over";
			gsm.setCombat(false);
			gsm.setState(GameStateManager.GAMEOVER);
		}
		switch(option) {
		case 0:
			Action.attackAction(player, enemy);
			playerString = "P: attack";
			break;
		case 2:
			if(player.getNumHealthPot() > 0) {
				Action.drinkHealthPot(player);
				playerString = "P: HP pot";	
			}
			else
				playerString = "P: No HP pot";
			break;
		case 3:
			if(player.getNumManaPot() > 0) {
				Action.drinkManaPot(player);
				playerString = "P: MP pot";	
			}
			else
				playerString = "P: No MP pot";
			break;
		case 1:
			Skill s = player.getSkill(0);
			if(s.canCast(player)) {
				Action.useSkill(player.getSkill(0), player, enemy);
				playerString = "P: P Atk";
			}
			else{
				playerString = "P: No Mana";
			}
			break;
		case 4:
			if(Action.retreat()) {
				gsm.setCombat(false);
			}
			playerString = null;
			break;
		}
	}
	
	private void delay() {
		try	{
		    Thread.sleep(100);
		}
		catch(InterruptedException ex)	{
		    Thread.currentThread().interrupt();
		}
	}
}
