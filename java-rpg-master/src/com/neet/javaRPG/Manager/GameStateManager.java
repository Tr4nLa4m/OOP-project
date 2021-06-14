package com.neet.javaRPG.Manager;

import java.awt.Graphics2D;


import com.neet.javaRPG.GameState.*;

public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	
	private boolean combat;
	private CombatState combatState;
	
	private boolean isWin;
	
	private GameState[] gameStates;
	private int currentState;
	private int previousState;
	

	
	public static final int NUM_STATES = 8;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int HARDMODE = 4;
	public static final int PLAY2 = 5;
	public static final int TUTOR = 6;
	public static final int INFO = 7;
	
	public GameStateManager() {
		
		paused = false;
		pauseState = new PauseState(this);
		
		combat = false;
		combatState = new CombatState(this);
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);
		
		isWin = false;
	}
	
	public void setState(int i) {
		previousState = currentState;
		unloadState(previousState);
		currentState = i;
		switch(i) {
		case INTRO:{
			gameStates[i] = new IntroState(this);
			break;
		}
		case MENU:{
			gameStates[i] = new MenuState(this);
			break;
		}
		case PLAY:{

			gameStates[i] = new PlayState(this);
			break;
		}
		case PLAY2:{

			gameStates[i] = new PlayState2(this);
			break;
		}
		case HARDMODE:{
				gameStates[i] = new HardModeState(this);
				break;
		}
		case TUTOR: {
				gameStates[i] = new TutorialState(this);
				break;
		}
		case INFO: {
				gameStates[i] = new InfoState(this);
				break;
			}

		case GAMEOVER:
			gameStates[i] = new GameOverState(this);
			((GameOverState)gameStates[i]).setIsWin(isWin);
			break;
		}
		gameStates[i].init();
	}
	
	public void unloadState(int i) {
		gameStates[i] = null;
	}


	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void setCombat(boolean b) {
		combat = b;
	}
	
	public void setIsWin(boolean b) {
		isWin = b;
	}
	
	public void update() {
		if(paused) {
			pauseState.update();
		}
		else if(combat) {
			combatState.setPlayState((PlayState)gameStates[currentState]); 
			combatState.update();
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
		}
		else if(combat) {
			update();
			combatState.draw(g);
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	
}
