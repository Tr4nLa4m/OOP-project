package com.neet.javaRPG.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import com.neet.javaRPG.Main.GamePanel;
import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;

public class GameOverState extends GameState {
	
	private Color color;
	
	private boolean isWin;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		color = new Color(164, 198, 222);
		isWin = false;
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
		
		if(!isWin) {
			Content.drawString(g, "You Lose", 24, 60);
			Content.drawString(g, "Game Over", 24, 80);	
		}
		else {
			Content.drawString(g, "You Win", 24, 60);
		}
		
	}
	
	public void setIsWin(boolean b) {
		isWin = b;
	}
	
	public void handleInput() {
		if(Keys.anyKeyPress()) {
			gsm.setState(GameStateManager.MENU);
		}
	}
}