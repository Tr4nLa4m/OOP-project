package com.neet.javaRPG.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.TileMap.TileMap;

public class Player extends Combatant {
	
	// sprites
	private BufferedImage[] downSprites = new BufferedImage[6];
	private BufferedImage[] leftSprites = new BufferedImage[6];
	private BufferedImage[] rightSprites = new BufferedImage[6];
	private BufferedImage[] upSprites = new BufferedImage[6];
	private BufferedImage[] RightSwordSprites = new BufferedImage[4];
	private BufferedImage[] UpSwordSprites = new BufferedImage[4];
	private BufferedImage[] LeftSwordSprites = new BufferedImage[4];
	private BufferedImage[] DownSwordSprites = new BufferedImage[4];

	//time attacked
	private long timer = 0;
	private long lastTime = 0;
	private final long attackCoolDown = 6000;

	// animation
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int RIGHTCOMBAT = 4;
	private final int UPCOMBAT = 5;
	private final int LEFTCOMBAT = 6;
	private final int DOWNCOMBAT = 7;

	//Weapon
	private  boolean hasSword = false;
	private  boolean canSwordCombat = false;
	public  boolean effect = false;
	// gameplay
	private long ticks;
	
	// stats
	private int xp;
	private int numHealthPot;
	private int numManaPot;

	
	public Player(TileMap tm) {
		
		super(tm);

		setMaxHP(50);
		setMaxMP(50);
		setCurrentHP(50);
		setCurrentMP(50);
		width = 32;
		height = 32;
		cwidth = 28;
		cheight = 28;
		
		moveSpeed = 5;

		for(int i = 0 ; i < 6 ; i++){
			rightSprites[i] = Content.PLAYER_T[0][i*2];
			upSprites[i] = Content.PLAYER_T[0][12 + i*2];
			leftSprites[i] = Content.PLAYER_T[0][24 + i*2];
			downSprites[i] = Content.PLAYER_T[0][36 + i*2];

		}

		for(int i = 0 ; i < 4 ; i++){
			RightSwordSprites[i] = Content.PLAYER_T[0][48+i*2];
			UpSwordSprites[i] = Content.PLAYER_T[0][56+i*2];
			LeftSwordSprites[i] = Content.PLAYER_T[0][64+i*2];
			DownSwordSprites[i] = Content.PLAYER_T[0][72+i*2];
		}
		
		animation.setFrames(downSprites);
		animation.setDelay(10);
		
		xp = 0;
		numHealthPot = 2;
		numManaPot = 2;
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}

	
	// Used to update time.
	public TileMap getTileMap() { return this.tileMap; }
	
	// Keyboard input. Moves the player.
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}
	public void setSwordCombat(){
		if(moving) return;
		if(hasSword == false) return;
		canSwordCombat = true;
	}

	//Got Sword
	public void gotSword() {
		hasSword = true;
		addATK( level);
		addDEF(2);
	}
	public boolean hasSword() { return hasSword; }

	public void update() {
		
		ticks++;

		if(currentAnimation == DOWNCOMBAT &&((ticks%27) == 0)){
			currentAnimation = DOWN;
			setAnimation(DOWN, downSprites, 5);
		}
		else if(currentAnimation == UPCOMBAT &&((ticks%27) == 0)){
			currentAnimation = UP;
			setAnimation(UP, upSprites, 5);
		}
		else if(currentAnimation == LEFTCOMBAT &&((ticks%27) == 0)){
			currentAnimation = LEFT;
			setAnimation(LEFT, leftSprites, 5);
		}
		else if(currentAnimation == RIGHTCOMBAT &&((ticks%27) == 0)){
			currentAnimation = RIGHT;
			setAnimation(RIGHT, rightSprites, 5);
		}
		// set animation
		if(canSwordCombat) {
			if(currentAnimation == DOWN){
				currentAnimation = DOWNCOMBAT;
				setAnimation(DOWNCOMBAT, DownSwordSprites, 5);
			}
			else if(currentAnimation == UP){
				currentAnimation = UPCOMBAT;
				setAnimation(UPCOMBAT, UpSwordSprites, 5);
			}
			else if(currentAnimation == RIGHT){
				currentAnimation = RIGHTCOMBAT;
				setAnimation(RIGHTCOMBAT, RightSwordSprites, 5);
			}
			else if(currentAnimation == LEFT){
				currentAnimation = LEFTCOMBAT;
				setAnimation(LEFTCOMBAT, LeftSwordSprites, 5);
			}

		}else {

			if (down) {
				if (currentAnimation != DOWN) {
					setAnimation(DOWN, downSprites, 5);
				}
			}
			if (left) {
				if (currentAnimation != LEFT) {
					setAnimation(LEFT, leftSprites, 5);
				}
			}
			if (right) {
				if (currentAnimation != RIGHT) {
					setAnimation(RIGHT, rightSprites, 5);
				}
			}
			if (up) {
				if (currentAnimation != UP) {
					setAnimation(UP, upSprites, 5);
				}
			}

		}
		// update position
		super.update();

		canSwordCombat = false;
	}
	
	// Draw Player.
	public void draw(Graphics2D g) {
		super.draw(g);

	}
	
	public void increaseXP(Combatant enemy) {
		int amount = enemy.xpCap[enemy.level - 1];
		this.xp += amount;
	}
	
	public boolean canLevelUp() {
		return xp >= xpCap[level -1];
	}
	
	public void levelUp() {
		xp = 0;
		addATK(3);
		addDEF(2);
		changeHP(20);
		changeMP(15);
		level++;
	}
	
	public int getNumHealthPot() {
		return numHealthPot;
	}
	
	public int getNumManaPot() {
		return numManaPot;
	}
	
	public void changeNumHealthPot(int numHealthPot) {
		this.numHealthPot += numHealthPot;
	}
	
	public void changeNumManaPot(int numManaPot) {
		this.numManaPot += numManaPot;
	}

	public void attackedStatic(int size) {

		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if(timer < attackCoolDown)	return;
		this.changeHP(-1* size);

		timer = 0;

	}



}