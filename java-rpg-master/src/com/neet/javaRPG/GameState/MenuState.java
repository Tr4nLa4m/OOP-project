package com.neet.javaRPG.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;

public class MenuState extends GameState {
	
	private BufferedImage bg;
	private BufferedImage diamond;
	
	private int currentOption = 0;
	private String[] options = {
		"START", "HARDMODE","TUTORIAL", "ABOUT", "QUIT"
	};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		bg = Content.MENUBG[0][0];
		diamond = Content.DIAMOND[0][0];
	}
	
	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {

		g.drawImage(bg, 0, 0, null);

		Content.drawStringBig(g, options[0], 250, 200, 30);
		Content.drawStringBig(g, options[1], 250, 250, 30);
		Content.drawStringBig(g, options[2], 250, 300, 30);
		Content.drawStringBig(g, options[3], 250, 350, 30);
		Content.drawStringBig(g, options[4], 250, 400, 30);

		if(currentOption == 0) g.drawImage(diamond, 230, 210, null);
		else if(currentOption == 1) g.drawImage(diamond, 230, 260, null);
		else if(currentOption == 2) g.drawImage(diamond, 230, 310, null);
		else if(currentOption == 3) g.drawImage(diamond, 230, 360, null);
		else if(currentOption == 4) g.drawImage(diamond, 230, 410, null);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
			currentOption++;
		}
		else if(Keys.isPressed(Keys.UP) && currentOption > 0) {
			currentOption--;
		}
		else if(Keys.isPressed(Keys.ENTER)) {
			selectOption();
		}
	}

	private void selectOption() {
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 1) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 2){
			gsm.setState(GameStateManager.TUTOR);
		}
		if(currentOption ==3){
			gsm.setState(GameStateManager.INFO);
		}
		if(currentOption == 4){
			System.exit(0);
		}
	}

}
	

