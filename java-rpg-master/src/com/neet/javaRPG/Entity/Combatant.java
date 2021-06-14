package com.neet.javaRPG.Entity;

import java.util.ArrayList;
import java.util.List;

import com.neet.javaRPG.RPG.Skill;
import com.neet.javaRPG.TileMap.TileMap;

public abstract class Combatant extends Entity {
	// stats
	protected int atk;
	protected int def;
	protected int curHP;
	protected int curMP;
	protected int maxHP;
	protected int maxMP;
	protected int level;
	
	protected int[] xpCap = new int[] {1, 3, 5, 10, 16};
	
	protected List<Skill> skillList;
	
	public Combatant(TileMap tm) {
		this(tm, 5, 2, 10, 10, 10, 10, 1, new ArrayList<>());
	}

	public Combatant(TileMap tm, int atk, int def, int maxHP, int maxMP, int level) {
		this(tm, atk, def, maxHP, maxMP, maxHP, maxMP, level, new ArrayList<>());
	}
	
	public Combatant(TileMap tm, int atk, int def, 
			int curHP, int curMP, int maxHP, int maxMP, 
			int level, List<Skill> skillList) {
		super(tm);
		this.atk = atk;
		this.def = def;
		this.curHP = curHP;
		this.curMP = curMP;
		this.maxHP = maxHP;
		this.maxMP = maxMP;
		this.level = level;
		this.skillList = skillList;
	}
	
	public int getATK() {
		return atk;
	}
	
	public int getDEF() {
		return def;
	}
	
	public int getCurrentHP() {
		return curHP;
	}
	
	public int getCurrentMP() {
		return curMP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getMaxMP() {
		return maxMP;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public void setDef(int def) {
		this.def = def;
	}
	
	public void setCurrentHP(int curHP) {
		this.curHP = curHP;
	}
	
	public void setCurrentMP(int curMP) {
		this.curMP = curMP;
	}
	
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	
	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addATK(int num) {
		this.atk += num;
	}
	
	public void addDEF(int num) {
		this.def += num;
	}
	
	public void changeHP(int num) {
		curHP += num;
		if(curHP >= maxHP) {
			curHP = maxHP;
		}
	}
	
	public void changeMP(int num) {
		curMP += num;
		if(curMP >= maxMP) {
			curMP = maxMP;
		}
	}
	
	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}
	
	public void addSkill(Skill skill) {
		this.skillList.add(skill);
	}
	
	public Skill getSkill(int i) {
		return skillList.get(i);
	}

	public void changeSkill(int i, Skill s) {
		skillList.add(i, s);
	}
	
	public boolean isDead() {
		return curHP <= 0;
	}
}
