package com.neet.javaRPG.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Player;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;
import com.neet.javaRPG.RPG.Action;
import com.neet.javaRPG.RPG.Skill;

public class CombatState extends GameState {

	private Player player;
//	private String infoString;
	private Graphics g;
	
	private Enemy enemy;
	private BufferedImage[] deathEffect;
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

	
	public CombatState(GameStateManager gsm) {
		super(gsm);
		this.player = null;
		this.enemy = null;
		init();
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

		
		//Combat option
		Content.drawStringBig(g, optionList[option], 250, yOffset + 200, 20);
		g.drawImage(Content.DIAMOND[0][0], 220, yOffset + 204, null);
		if(enemyString != null)
			Content.drawStringBig(g, enemyString, 250, yOffset + 250, 12);
		if(playerString != null)
			Content.drawStringBig(g, playerString, 250, yOffset + 270,12);

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

			//draw DeathEffect


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
