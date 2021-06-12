package com.neet.javaRPG.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;



import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Item;
import com.neet.javaRPG.Entity.Player;
import com.neet.javaRPG.HUD.Hud;
import com.neet.javaRPG.Main.GamePanel;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;
import com.neet.javaRPG.RPG.Skill;
import com.neet.javaRPG.RPG.ImplementSkill.PowerAttack;
import com.neet.javaRPG.TileMap.TileMap;

public class PlayState extends GameState {
	
	// player
	private static Player player;

	
	// tilemap
	private TileMap tileMap;
	
	// enemies
	private ArrayList<Enemy> enemies;
	private Enemy fightingEnemy;
	
	// items
	private ArrayList<Item> items;
	
	// sparkles
	private boolean isWin;
	
	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize;
	private float camx,camy;
	
	// hud
	private Hud hud;
	
	// transition box
	private ArrayList<Rectangle> boxes;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	

	
	public void init() {
		
		// create lists
		enemies = new ArrayList<Enemy>();
		items = new ArrayList<Item>();
		
		// load map
		tileMap = new TileMap();
		tileMap.loadMap("/Resources/Maps/testmap.map");
		
		// create player
		player = new Player(tileMap);
		player.addSkill(new PowerAttack(player.getATK()));
		
		// fill lists
		populateEnemies();
		populateItems();
		
		// initialize player
		player.setTilePosition(4, 37);
		
		// set up camera position
		sectorSize = GamePanel.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);
		
		// load hud
		hud = new Hud(player, enemies);
				
		// start event
		boxes = new ArrayList<Rectangle>();
		
		isWin = false;
	}
	
	private void populateEnemies() {
		
		Enemy d;
		List<Skill> skillList = new ArrayList<>();
		skillList.add(new PowerAttack(5));

		d = new Enemy(tileMap, 8, 4, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 4, 4, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 11, 33, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 4, 27, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 36, 37, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 34, 28, 2);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 24, 24, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 37, 2, 2);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 27, 26, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 27, 34, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 23, 14, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 32, 15, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 3, 13, 1);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 32, 2, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 2, 18, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 12, 24, 0);
		d.setSkillList(skillList);
		enemies.add(d);

		d = new Enemy(tileMap, 17, 4, 0);
		d.setSkillList(skillList);
		enemies.add(d);

	}
	
	//Add Sword
	private void populateItems() {
		
		Item[] item = new Item[3];
	
		item[0] = new Item(tileMap);
		item[0].setType(Item.HP_ITEM);
		item[1] = new Item(tileMap);
		item[1].setType(Item.MP_ITEM);
		item[2] = new Item(tileMap);
		item[2].setType(Item.SWORD);

		for(int i = 0; i < item.length; i++){
				if(item[i].getType() == 0)		item[i].setTilePosition(37, 28);
				if(item[i].getType() == 1)		item[i].setTilePosition(35, 21);
				if(item[i].getType() == 2)		item[i].setTilePosition(37, 37);

			items.add(item[i]);
		}

	}
	
	public void update() {
		handleInput();


		if(enemies.isEmpty()) {
			gsm.setIsWin(true);
			finish();
		}
		
		// update camera

		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		camx = (float) player.getx() / sectorSize;
		camy = (float) player.gety() / sectorSize;
		if (camx >0.5 && camx <1.5)
			camx = -player.getx() +(float)sectorSize/2;
		else
			camx = -xsector * sectorSize;
		if (camy >0.5 && camy <1.5)
			camy = -player.gety() +(float)sectorSize/2;
		else
			camy = -ysector * sectorSize;
		tileMap.setPosition((int)camx,(int)camy);

		tileMap.update();

		
		// update player
		player.update();

		if(player.getCurrentHP() <= 0)
		{
			playerDead();
		}
		
		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy d = enemies.get(i);
			d.update();
			if(canStaticAttack(d,player)) {
				player.attackedStatic(2);
			}
			if(player.intersects(d)) {

				fightingEnemy = d;
				//gsm.setCombat(true);
				player.changeHP(-2);

				//enemies.remove(i);
				//i--;
			}
			if(player.getSBounds().intersects(d.getBound())){
				if(player.isCanCombat()){
					d.changeHP(-1 * player.getATK());
					player.setCombat(false);
				}
			}
			if(d.isDead())
			{
				enemies.remove(i);
				i--;
			}
		}
				
		// update items
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.update();
			if(player.intersects(item)) {
				items.remove(i);
				i--;
				item.collected(player);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);

		// draw enemies
		for(Enemy d : enemies) {
			d.draw(g);
		}
		
		// draw items
		for(Item i : items) {
			i.draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < boxes.size(); i++) {
			g.fill(boxes.get(i));
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(true);
		}
		
		if(Keys.isDown(Keys.LEFT)) player.setLeft();
		if(Keys.isDown(Keys.RIGHT)) player.setRight();
		if(Keys.isDown(Keys.UP)) player.setUp();
		if(Keys.isDown(Keys.DOWN)) player.setDown();
		if(Keys.isDown(Keys.SPACE)) {
			player.setCombat(true);
			player.setSwordCombat();
		}
	}
	
	public void finish() {
		gsm.setState(gsm.GAMEOVER);
		//gsm.setIsWin(isWin);
	}
	
	public TileMap getTileMap() {
		return tileMap;
	}
	
	public void playerDead() {
		isWin = false;
		finish();
	}
	
	public void enemyDefeat() {
		enemies.remove(fightingEnemy);
		
		// make any changes to tile map
		ArrayList<int[]> ali = fightingEnemy.getChanges();
		for(int[] j : ali) {
			tileMap.setTile(j[0], j[1], j[2]);
		}
		player.increaseXP(fightingEnemy);
		if(player.canLevelUp()) {
			player.levelUp();
			player.changeSkill(0, new PowerAttack(player.getATK()));
		}
			
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public boolean canStaticAttack(Enemy d,Player p){
		double dx = Math.abs( p.getx() - d.getx());
		double dy = Math.abs( p.gety() - d.gety());
		if( dx < 64 &&  dy < 64)	return  true;
		return false;

	}
	public Enemy getEnemy() {
		return fightingEnemy;
	}
}
