package com.neet.javaRPG.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.RPG.Skill;
import com.neet.javaRPG.TileMap.TileMap;

public class Enemy extends Combatant {
	private String name;

	private BufferedImage[] sprites ;
	private BufferedImage[][] sprite1;
	private BufferedImage[] Down;
	private BufferedImage[] Up;
	private BufferedImage[] Right;
	private BufferedImage[] Left;
	
	private ArrayList<int[]> tileChanges;
	private int typeEnemy;

	private int DOWN = 1;
	private int UP = 2;
	private int RIGHT = 3;
	private int LEFT = 4;


	
	public Enemy(TileMap tm,int typeEnemy) {
		this(tm, "Monster", null);
		if(typeEnemy == 0){
			this.typeEnemy = typeEnemy;
			width = 16;
			height = 16;
			cwidth = 12;
			cheight = 12;

			sprites = new BufferedImage[5];
			for(int i = 0;i < 5 ; i++){
				sprites[i] = Content.MONSTER[0][i];
			}
			animation.setFrames(sprites);
			animation.setDelay(10);


			tileChanges = new ArrayList<int[]>();
		}
		else if(typeEnemy == 1){
			this.typeEnemy = typeEnemy;
			width = 32;
			height = 32;
			cwidth = 28;
			cheight = 28;

			this.curHP = this.maxHP = 30;
			this.curMP = this.maxMP = 20;
			this.atk = 5;
			this.moveSpeed = 1;

			sprite1 = new BufferedImage[4][3];
			for(int i = 4;i < 8 ; i++){
				for(int j = 3; j < 6; j++){
					sprite1[i - 4][j - 3] = Content.MONSTER1[i][j];
				}
			}
			down = true;
			Down = sprite1[0];
			Left = sprite1[1];
			Right = sprite1[2];
			animation.setFrames(Down);
			animation.setDelay(10);


			tileChanges = new ArrayList<int[]>();
		}
		else if(typeEnemy == 2){
			this.typeEnemy = typeEnemy;
			width = 32;
			height = 32;
			cwidth = 28;
			cheight = 28;

			this.name = " Boss";
			this.curHP = this.maxHP = 200;
			this.curMP = this.maxMP = 100;
			this.atk = 15;
			this.def = 5;

			this.moveSpeed = 2;

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
			animation.setFrames(Down);
			animation.setDelay(10);


			tileChanges = new ArrayList<int[]>();
		}

	}
	
	public Enemy(TileMap tm, String name, List<Skill> skillList) {
		this(tm, name, 3, 2, new ArrayList<>());
	}
	
	public Enemy(TileMap tm, String name, int atk, int def, List<Skill> skillList) {
		this(tm, name, atk, def, 10, 10, 1, skillList);
	}
	
	public Enemy(TileMap tm, String name, int atk, int def, int hp, int mp, int level, List<Skill> skillList) {
		super(tm, atk, def, hp, mp, level);
		this.name = name;
		this.skillList = skillList;

	}
	public void update(){
		super.update();
		if(typeEnemy == 1){
			if(down){
				setRight();
				setAnimation(DOWN, Down, 10);
			}else if(right){
				setLeft();
				setAnimation(LEFT, Left, 10);
			}else{
				animation.setFrames(Down);
				animation.setDelay(10);
			}
		}
		down = false;
		right = false;
	}


	
	private static int getAtkFromLevel(int level) {
		return 5 + 3*(level - 1);
	}
	
	private static int getDefFromLevel(int level) {
		return 5 + 2*(level - 1);
	}
	
	private static int getHPFromLevel(int level) {
		return 10 + 5*(level - 1);
	}
	
	private static int getMPFromLevel(int level) {
		return 10 + 5*(level - 1);
	}

	
	public Skill useSkill() {
		int numUsableSkill = 0;
		if(skillList.size() == 1) {
			return skillList.get(0);
		}
		for(Skill s : skillList) {
			if(s.canCast(this))
				numUsableSkill++;
		}
		Random rnd = new Random();
		try {
			return skillList.get(rnd.nextInt(numUsableSkill));	
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean canUseSkill() {
		return !skillList.isEmpty();
	}
	
	public void addChange(int[] i) {
		tileChanges.add(i);
	}
	public ArrayList<int[]> getChanges() {
		return tileChanges;
	}

	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}

}
