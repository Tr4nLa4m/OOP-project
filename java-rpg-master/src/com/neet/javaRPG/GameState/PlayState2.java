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
import com.neet.javaRPG.RPG.Action;
import com.neet.javaRPG.RPG.Skill;
import com.neet.javaRPG.RPG.ImplementSkill.PowerAttack;
import com.neet.javaRPG.TileMap.TileMap;

public class PlayState2 extends GameState {

    // player
    private static Player player2;


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

    public PlayState2(GameStateManager gsm) {
        super(gsm);
    }



    public void init() {

        // create lists
        enemies = new ArrayList<Enemy>();
        items = new ArrayList<Item>();

        // load map
        tileMap = new TileMap();
        tileMap.loadMap("/Resources/Maps/map2.map");

        // create player
        player2 = new Player(tileMap);


        // fill lists
        populateEnemies();
        //populateItems();

        // initialize player
        player2.setTilePosition(4, 4);

        // set up camera position
        sectorSize = GamePanel.WIDTH;
        xsector = player2.getx() / sectorSize;
        ysector = player2.gety() / sectorSize;
        tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);

        // load hud
        hud = new Hud(player2, enemies);

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
 /*****
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
****/
    }
/****
    private void populateItems() {

        Item[] item = new Item[3];

        item[0] = new Item(tileMap,0);
        item[1] = new Item(tileMap,1);
        item[2] = new Item(tileMap,2);


        for(int i = 0; i < item.length; i++){
            if(item[i].getType() == 0)		item[i].setTilePosition(37, 28);
            if(item[i].getType() == 1)		item[i].setTilePosition(35, 21);
            if(item[i].getType() == 2)		item[i].setTilePosition(37, 37);
            items.add(item[i]);
        }

    }
*****/
    public void update() {
        handleInput();

/****
        if(enemies.isEmpty()) {
            port = new Item(tileMap,3);
            port.setTilePosition(37,4);
            items.add(port);

            finish();
        }
***/
        // update camera

        xsector = player2.getx() / sectorSize;
        ysector = player2.gety() / sectorSize;
        camx = (float) player2.getx() / sectorSize;
        camy = (float) player2.gety() / sectorSize;
        if (camx >0.5 && camx <1.5) {
            camx = -player2.getx() + (float) sectorSize / 2;
            player2.moveCamX = true;
        }
        else {
            camx = -xsector * sectorSize;
            player2.moveCamX = false;
        }
        if (camy >0.5 && camy <1.5) {
            camy = -player2.gety() + (float) sectorSize / 2;
            player2.moveCamY = true;
        }
        else {
            camy = -ysector * sectorSize;
            player2.moveCamY = false;
        }
        tileMap.setPosition((int)camx,(int)camy);


        tileMap.update();


        // update player
        player2.update();
        if(player2.getCurrentHP() <= 0)
        {
            playerDead();
        }


        // update enemies
        for(int i = 0; i < enemies.size(); i++) {

            Enemy d = enemies.get(i);
            d.update();
            player2.setCombat(false);
            handleInput();
            Action.Combat(player2,d);

            if(d.isDead())
            {
                player2.increaseXP(d);
                enemies.remove(i);
                i--;
            }
        }

        // update items
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.update();
            if(player2.intersects(item)) {
                if(item.getType() != 3)
                {
                    items.remove(i);
                    i--;
                    item.collected(player2);
                }else{
                    finish();
                }
            }
        }

    }

    public void draw(Graphics2D g) {

        // draw tilemap
        tileMap.draw1(g);

        // draw player
        player2.draw(g);

        // draw enemies
        for(Enemy d : enemies) {
            d.draw(g);
        }

        // draw items
    /**

        for(Item i : items) {
            i.draw(g);
        }
**/
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

        if(Keys.isDown(Keys.LEFT)) player2.setLeft();
        if(Keys.isDown(Keys.RIGHT)) player2.setRight();
        if(Keys.isDown(Keys.UP)) player2.setUp();
        if(Keys.isDown(Keys.DOWN)) player2.setDown();
        if(Keys.isDown(Keys.SPACE)) {
            player2.setCombat(true);
            player2.setSwordCombat();
        }
    }

    public void finish() {
        gsm.setIsWin(true);
        gsm.setState(gsm.GAMEOVER);
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void playerDead() {
        isWin = false;
        GameOver();
    }

    public void GameOver(){
        gsm.setIsWin(false);
        gsm.setState(gsm.GAMEOVER);
    }


    public void enemyDefeat() {
        enemies.remove(fightingEnemy);

        // make any changes to tile map
        ArrayList<int[]> ali = fightingEnemy.getChanges();
        for(int[] j : ali) {
            tileMap.setTile(j[0], j[1], j[2]);
        }
        player2.increaseXP(fightingEnemy);
        if(player2.canLevelUp()) {
            player2.levelUp();
            player2.changeSkill(0, new PowerAttack(player2.getATK()));
        }

    }

    public static Player getPlayer() {
        return player2;
    }


}
