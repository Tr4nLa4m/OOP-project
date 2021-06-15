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
    private static Player player;


    // tilemap
    private TileMap tileMap;

    // enemies
    private ArrayList<Enemy> enemies;

    // items
    private ArrayList<Item> items;
    private Item port;

    // sparkles
    private boolean isWin;

    // camera position
    private int xsector;
    private int ysector;
    private int sectorSize;
    private float camx,camy;

    private boolean flag = true;
    private long timeH;
    private long lastTimeH;
    //timer
    private long timer = 0, lastTime = 0;

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
        player = new Player(tileMap);
        if(PlayState.getPlayer() == null)   HardModeState.savePlayer(player);
        else                                PlayState.savePlayer(player);

        // fill lists
        populateEnemies();
        populateItems();

        // initialize player
        player.setTilePosition(2, 16);

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

        d = new Enemy(tileMap, 5, 6, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 8, 13, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 8, 7, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 25, 11, 2);
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 27, 15, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 21, 3, 1);
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 28, 2, 4);
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 38, 2, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 38, 3, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 38, 4, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 47, 3, 5);
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 52, 11, 4);
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 47, 13, 1);
        BuffEnenmy(d,1 );
        d.setSkillList(skillList);
        enemies.add(d);


    }

    //Add Sword
    private void populateItems() {

        Item item;
        item = new Item(tileMap,0);
        item.setTilePosition(29,17);
        items.add(item);

        item = new Item(tileMap,0);
        item.setTilePosition(41,10);
        items.add(item);

        item = new Item(tileMap,0);
        item.setTilePosition(44,1);
        items.add(item);

        item = new Item(tileMap,1);
        item.setTilePosition(10,18);
        items.add(item);


    }

    //update flag
    public void updateFlag(){
        timeH = System.currentTimeMillis() - lastTimeH;
        if(timeH > 3000) flag = true;
    }

    //flame attack
    public void updateEnemy(){

        Thread enemySpawner = new Thread(() -> {
            try {
                Thread.sleep(5000); //delay time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //TO_DO
            enemySpawn(3, 39, 3);
            enemySpawn(39, 3, 3);
            enemySpawn(39, 39, 3);
            enemySpawn(3, 3, 3);
        });
        enemySpawner.start();
    }

    public void enemySpawn(int x, int y, int typeEnemy){
        Enemy d;
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new PowerAttack(5));
        d = new Enemy(tileMap, x, y, typeEnemy);
        d.setSkillList(skillList);
        enemies.add(d);
    }


    public void update() {
        handleInput();
        updateFlag();

        //update flame Enemy
        timer += System.currentTimeMillis() - lastTime;
        if(timer > 10000) {
            updateEnemy();
            timer = 0;
        }
        lastTime = System.currentTimeMillis();


        if(enemies.size() == 4) {
            finish();
        }

        // update camera

        xsector = player.getx() / sectorSize;
        ysector = player.gety() / sectorSize;
        camx = (float) player.getx() / sectorSize;
        camy = (float) player.gety() / sectorSize;
        if (camx >0.5 && camx <1.5) {
            camx = -player.getx() + (float) sectorSize / 2;
            player.moveCamX = true;
        }
        else {
            camx = -xsector * sectorSize;
            player.moveCamX = false;
        }
/***
        if (camy >0.5 && camy <1.5) {
            camy = -player.gety() + (float) sectorSize / 2;
            player.moveCamY = true;
        }
        else {
            camy = -ysector * sectorSize;
            player.moveCamY = false;
        }
 ****/
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
            player.setCombat(false);
            handleInput();
            Action.Combat(player,d);

            if(d.isDead())
            {
                player.increaseXP(d);
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
        tileMap.draw1(g);

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
        if(Keys.isPressed(Keys.G) && flag == true){
            Action.drinkManaPot(player);
            flag = false;
            lastTimeH = System.currentTimeMillis();
        }
        if(Keys.isPressed(Keys.H) && flag == true){
            Action.drinkHealthPot(player);
            flag = false;
            lastTimeH = System.currentTimeMillis();
        }
        if(Keys.isDown(Keys.SPACE)) {
            player.setCombat(true);
            player.setSwordCombat();
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


    public static Player getPlayer() {
        return player;
    }


    public void BuffEnenmy(Enemy d,int type) {
        if(type == 0){
            d.changeHP(10);
            d.changeMP(10);
            d.addATK(3);
            d.addDEF(2);
        }
        else if(type == 1){
            d.addSpeed(1);
            d.changeHP(20);
            d.changeMP(20);
            d.addATK(5);
            d.addDEF(3);
        }
        else if(type == 2){
            d.addSpeed(1);
            d.changeHP(100);
            d.changeMP(50);
            d.addATK(10);
            d.addDEF(5);
        }
    }
}
