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
		
		Content.drawStringBig(g, "paused", 240, 180, 30);
		
		Content.drawStringBig(g, "arrow", 250, 260,10);
		Content.drawStringBig(g, "keys", 310, 260,10);
		Content.drawStringBig(g, ": move", 350, 260,10);
		
		Content.drawStringBig(g, "space", 250, 290,10);
		Content.drawStringBig(g, ": restart ", 310, 290,10);
		
		Content.drawStringBig(g, "F1:", 250, 320,10);
		Content.drawStringBig(g, "return", 280, 320,10);
		Content.drawStringBig(g, "to menu", 350, 320,10);
		
	}
	public void handleInput() {

		if(Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.PLAY);
		}
		if(Keys.isPressed(Keys.F1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}
