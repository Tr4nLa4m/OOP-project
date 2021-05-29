package com.neet.javaRPG.GameState;

import java.awt.Graphics2D;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;

public class PauseState extends GameState {
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		Content.drawStringMenu(g, "paused", 240, 180);
		
		Content.drawString(g, "arrow", 250, 260);
		Content.drawString(g, "keys", 296, 260);
		Content.drawString(g, ": move", 336, 260);
		
		Content.drawString(g, "space", 250, 290);
		Content.drawString(g, ": action", 290, 290);
		
		Content.drawString(g, "F1:", 250, 320);
		Content.drawString(g, "return", 276, 320);
		Content.drawString(g, "to menu", 332, 320);
		
	}
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(false);
		}
		if(Keys.isPressed(Keys.F1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}
