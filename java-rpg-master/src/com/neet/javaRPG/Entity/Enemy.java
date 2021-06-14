package com.neet.javaRPG.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.neet.javaRPG.GameState.HardModeState;
import com.neet.javaRPG.GameState.PlayState2;
import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.GameState.PlayState;
import com.neet.javaRPG.RPG.Skill;
import com.neet.javaRPG.TileMap.TileMap;


public class Enemy extends Combatant {
	private int xMove;
	private int yMove;
	boolean movingX = true;
	boolean movingY = false;

	//
	private int xOffset;
	private int yOffset;

	private  Player player = PlayState.getPlayer();


	private BufferedImage[] sprites ;
	private BufferedImage[][] sprite1;
	private BufferedImage[] Down;
	private BufferedImage[] Up;
	private BufferedImage[] Right;
	private BufferedImage[] Left;
	
	private ArrayList<int[]> tileChanges;
	private int typeEnemy;
	private String name;

	private int DOWN = 1;
	private int UP = 2;
	private int RIGHT = 3;
	private int LEFT = 4;

	private long timer;
	private long lastTime;
	private long distance ;
	private int direction;



	private int homex,homey;
	private boolean flag = false;
	private boolean monster_infor = false;



	public Enemy(TileMap tm,int x, int y,int typeEnemy) {
		this(tm, "Monster", null);
		timer = 0;
		this.homex = x;
		this.homey = y;
		setTilePosition(x,y);
		setPlayer();

		lastTime = System.currentTimeMillis();
		if(typeEnemy == 0){
			this.typeEnemy = typeEnemy;
			width = 16;
			height = 16;
			cwidth = 16;
			cheight = 16;
			level = 1;
			name = "Bat";

			sprites = new BufferedImage[5];
			for(int i = 0;i < 5 ; i++){
				sprites[i] = Content.MONSTER[0][i];
			}
			animation.setFrames(sprites);
			animation.setDelay(10);

		}
		else if(typeEnemy == 1){
			this.typeEnemy = typeEnemy;
			width = 32;
			height = 32;
			cwidth = 28;
			cheight = 28;
			level = 2;
			name = "Duck";

			this.curHP = this.maxHP = 30;
			this.curMP = this.maxMP = 20;
			this.atk = 5;
			this.moveSpeed = 2;

			distance = 0;
			sprite1 = new BufferedImage[4][3];
			for(int i = 4;i < 8 ; i++){
				for(int j = 3; j < 6; j++){
					sprite1[i - 4][j - 3] = Content.MONSTER1[i][j];
				}
			}
			Down = sprite1[0];
			Left = sprite1[1];
			Right = sprite1[2];
			Up = sprite1[3];
			animation.setFrames(Down);
			animation.setDelay(5);

		}
		else if(typeEnemy == 2){
			this.typeEnemy = typeEnemy;
			width = 32;
			height = 32;
			cwidth = 28;
			cheight = 28;
			level = 3;


			this.name = " Spider";
			this.curHP = this.maxHP = 200;
			this.curMP = this.maxMP = 100;
			this.atk = 15;
			this.def = 5;

			this.moveSpeed = 3;

			sprite1 = new BufferedImage[4][3];
			for(int i = 0;i < 4 ; i++){
				for(int j = 0; j < 3; j++){
					sprite1[i][j] = Content.MONSTER1[i][j];
				}
			}

			down = true;
			Down = sprite1[0];
			Left = sprite1[1];
			Right = sprite1[2];
			Up = sprite1[3];
			animation.setFrames(Down);
			animation.setDelay(5);

		}
		else if(typeEnemy == 3){
			this.typeEnemy = typeEnemy;
			name = "Flame";
			width = 32;
			height = 32;
			cwidth = 28;
			cheight = 28;

			this.atk = 5;
			this.moveSpeed = 2;

			sprite1 = new BufferedImage[4][3];
			for(int i = 0;i < 4 ; i++){
				for(int j = 9; j < 12; j++){
					sprite1[i][j - 9] = Content.MONSTER1[i + 1][j];
				}
			}
			Down = sprite1[0];
			animation.setFrames(Down);
			animation.setDelay(5);

			tileChanges = new ArrayList<int[]>();
		}
		else if(typeEnemy == 4){
			this.typeEnemy = typeEnemy;
			width = 48;
			height = 72;
			cwidth = 44;
			cheight = 70;
			level = 4;


			this.name = " Buffalo";
			this.curHP = this.maxHP = 300;
			this.curMP = this.maxMP = 100;
			this.atk = 20;
			this.def = 10;

			this.moveSpeed = 3;

			sprite1 = new BufferedImage[4][3];
			for(int i = 0;i < 4 ; i++){
				for(int j = 0; j < 3; j++){
					sprite1[i][j] = Content.BAFFALO[i][j];
				}
			}

			down = true;
			Down = sprite1[0];
			Left = sprite1[1];
			Right = sprite1[2];
			Up = sprite1[3];
			animation.setFrames(Down);
			animation.setDelay(5);

		}
		else if(typeEnemy == 5){
			this.typeEnemy = typeEnemy;
			width = 48;
			height = 72;
			cwidth = 48;
			cheight = 52;
			level = 4;


			this.name = "Big Bat";
			this.curHP = this.maxHP = 350;
			this.curMP = this.maxMP = 100;
			this.atk = 20;
			this.def = 10;

			this.moveSpeed = 4;

			sprite1 = new BufferedImage[4][3];
			for(int i = 0;i < 4 ; i++){
				for(int j = 0; j < 3; j++){
					sprite1[i][j] = Content.BIGBAT[i][j];
				}
			}

			down = true;
			Down = sprite1[0];
			Left = sprite1[1];
			Right = sprite1[2];
			Up = sprite1[3];
			animation.setFrames(Down);
			animation.setDelay(5);

		}

		//init bounds
		setBound(x + xmap -width / 2,y + ymap-height / 2, cwidth, cheight);
	}
	
	public Enemy(TileMap tm, String name, List<Skill> skillList) {
		this(tm, name, 3, 2, new ArrayList<>());
	}
	
	public Enemy(TileMap tm, String name, int atk, int def, List<Skill> skillList) {
		this(tm, name, atk, def, 15, 15, 1, skillList);
	}
	
	public Enemy(TileMap tm, String name, int atk, int def, int hp, int mp, int level, List<Skill> skillList) {
		super(tm, atk, def, hp, mp, level);
		this.name = name;
		this.skillList = skillList;

	}

	public void addSpeed(int t){
		this.moveSpeed += t;
	}

	public void moveX(){
		if(timer < 2000){
			if(distance < 80){
				right = true;

				xMove = moveSpeed;
				x += xMove;
				timer += System.currentTimeMillis() - lastTime;
				distance += xMove;

			}else{
				right = false;
				xMove = 0;
				distance = 0;
				timer = 2000;
			}
		}
		else if(timer>=2000 && timer <= 4000){
			if(distance < 80){
				left = true;
				xMove = -moveSpeed;
				x += xMove;
				timer += System.currentTimeMillis() - lastTime;
				distance -= xMove;

			}else{
				left = false;
				xMove = 0;
				distance = 0;
				timer = 0;
			}
		}else{
			timer = 0;
		}
		lastTime = System.currentTimeMillis();
	}
	public void moveX(int a){
		if(timer < 2000){
			movingX = true;
			if(distance < 80){
				left = true;
				xMove = -moveSpeed;
				x += xMove;
				timer ++;
				distance -= xMove;
			}else{
				left = false;
				xMove = 0;
				distance = 0;
				timer = 2000;
			}
		}
		else if(timer>=2000 && timer <= 4000){
			movingX = true;
			if(distance < 80){
				right = true;
				xMove = moveSpeed;
				x += xMove;
				timer ++;
				distance += xMove;
			}else{
				right = false;
				xMove = 0;
				distance = 0;
				timer = 4001;
			}
		}else{
			movingX = false;
			timer =0;
		}

	}
	public void moveY(int a){
		if(timer < 2000){
			movingY = true;
			if(distance < 80){
				down = true;
				yMove = moveSpeed;
				y += yMove;
				timer++;
				//timer += System.currentTimeMillis() - lastTime;
				distance += yMove;
			}else{
				down = false;
				yMove = 0;
				distance = 0;
				timer = 2000;
			}
		}
		else if(timer>=2000 && timer <= 4000){
			movingY = true;
			if(distance < 80){
				up = true;
				yMove = -moveSpeed;
				y += yMove;
				timer ++;
				//timer += System.currentTimeMillis() - lastTime;
				distance -= yMove;
			}else{
				up = false;
				yMove = 0;
				distance = 0;
				timer = 4001;
			}
		}else  if(timer == 4001){
			movingY = false;
			timer = 0;
		}

		//lastTime = System.currentTimeMillis();
	}

	public boolean combatDistance(Player player){
		return ((player.getx() - getx())*(player.getx() - getx()) + (player.gety() - gety())*(player.gety() - gety()) < 25600);
	}


	public void chasePlayerMove(){
		flag = true;

		if(y > player.gety() + 1){
			direction = 1;
			yMove = -moveSpeed;
			y += yMove;
		}
		if(y < player.gety() - 1){
			direction = 2;
			yMove = moveSpeed;
			y += yMove;
		}
		if(x > player.getx() + 1){
			direction = 3;
			xMove = -moveSpeed;
			x += xMove;
		}
		if(x < player.getx() -1 ){
			direction = 4;
			xMove = moveSpeed;
			x += xMove;
		}

	}

	public void backHomeMove(){

		if(y > homey * 32 + 16 + 1){
			direction = 1;
			yMove = -moveSpeed;
			y += yMove;
		}
		if(y < homey * 32 + 16 - 1){
			direction = 2;
			yMove = moveSpeed;
			y += yMove;
		}
		if(x > homex * 32 + 16 + 1){
			direction = 3;
			xMove = -moveSpeed;
			x += xMove;
		}
		if(x < homex * 32 + 16 -1 ){
			direction = 4;
			xMove = moveSpeed;
			x += xMove;
		}
		if(x == homex * 32 + 16 && y == homey * 32 + 16) flag = false;

	}

	public void update(){

		if(typeEnemy != 3) {
			if (combatDistance(player)) chasePlayerMove();
			if (!combatDistance(player) && flag) backHomeMove();
		}else{
			chasePlayerMove();
		}

		if (typeEnemy == 1 || typeEnemy == 5) {
			moveX();
			setAnimationFrame();
		} else if (typeEnemy == 2 || typeEnemy == 4) {
			if (!movingY) {
				moveX(2);
				setAnimationFrame();
				movingY = false;
			}
			if (!movingX) {
				moveY(2);
				setAnimationFrame();
				movingX = false;
			}
		}

		xOffset = x + xmap - width / 2;
		yOffset = y + ymap - height / 2;
		super.update();

		updateBound(x + xmap -width / 2,y + ymap-height / 2);

	}

	private void setAnimationFrame() {
		if(right){
			if(animation.getCurrentSprite() != Right)
				animation.setFrames(Right);
		}
		else if(left){
			if(animation.getCurrentSprite() != Left)
				animation.setFrames(Left);
		}else if(up){
			if(animation.getCurrentSprite() != Up)
				animation.setFrames(Up);
		}else if(down){
			if(animation.getCurrentSprite() != Down)
				animation.setFrames(Down);
		}
	}



	
	public int getTypeEnemy() {
		return typeEnemy;
	}
	public ArrayList<int[]> getChanges() {
		return tileChanges;
	}
	public void setMonster_infor(boolean b)	{ monster_infor = b;}

	public void setPlayer(){
		if(PlayState2.getPlayer() != null)	player = PlayState2.getPlayer();
		else if(PlayState.getPlayer() != null) player = PlayState.getPlayer();
		else player = HardModeState.getPlayer();
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);

		//draw HP, MP
		g.setColor(new Color(255, 0, 0));
		if(typeEnemy != 0)
			g.fillRect(xOffset + 4, yOffset - 10, (int)2*(10 * this.getCurrentHP() / this.getMaxHP()), 2);
		else
			g.fillRect(xOffset , yOffset - 10, (int)2*(10 * this.getCurrentHP() / this.getMaxHP()), 2);

		g.setColor(new Color(0, 0, 255));
		if(typeEnemy != 0)
			g.fillRect(xOffset + 4, yOffset - 6, (int)2*(10 * this.getCurrentMP() / this.getMaxMP()), 2);
		else
			g.fillRect(xOffset , yOffset - 6, (int)2*(10 * this.getCurrentMP() / this.getMaxMP()), 2);

		//draw bounds
		g.setColor(Color.black);
		g.drawRect(x + xmap -width / 2,y + ymap-height / 2, cwidth,cheight);

		//draw Monster Information
		if(monster_infor){
			Content.drawString(g, name, 560,  4 );
			Content.drawString(g, "HP:", 560, 16);
			Content.drawString(g, "" + curHP, 600, 16);
			Content.drawString(g, "MP:", 560, 24);
			Content.drawString(g, "" + curMP, 600, 24);
		}

	}

}
