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

public class HardModeState extends GameState {

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

    //timer
    private long timer = 0, lastTime = 0;

    private boolean flag = true;
    private long timeH;
    private long lastTimeH;

    // hud
    private Hud hud;

    // transition box
    private ArrayList<Rectangle> boxes;

    public HardModeState(GameStateManager gsm) {
        super(gsm);
    }



    public void init() {

        // create lists
        enemies = new ArrayList<Enemy>();
        items = new ArrayList<Item>();

        // load map
        tileMap = new TileMap();
        tileMap.loadMap("/Resources/Maps/map1.map");

        // create player
        player = new Player(tileMap);
        player.addSkill(new PowerAttack(10,7));

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
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 4, 4, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 11, 33, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 4, 27, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 36, 37, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 33, 28, 2);
        d.setSkillList(skillList);
        BuffEnenmy(d,2 );
        enemies.add(d);

        d = new Enemy(tileMap, 24, 24, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 37, 2, 2);
        d.setSkillList(skillList);
        BuffEnenmy(d,2 );
        enemies.add(d);

        d = new Enemy(tileMap, 27, 26, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 27, 34, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 23, 14, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 32, 15, 1);
        d.setSkillList(skillList);
        BuffEnenmy(d,1 );
        enemies.add(d);

        d = new Enemy(tileMap, 3, 13, 1);
        BuffEnenmy(d,1 );
        d.setSkillList(skillList);
        enemies.add(d);

        d = new Enemy(tileMap, 32, 2, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 2, 18, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 12, 24, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

        d = new Enemy(tileMap, 17, 4, 0);
        d.setSkillList(skillList);
        BuffEnenmy(d,0 );
        enemies.add(d);

    }

    //Add Sword
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

    //update flag
    public void updateFlag(){
        timeH = System.currentTimeMillis() - lastTimeH;
        if(timeH > 3000) flag = true;
    }

    public void update() {
        handleInput();
        updateFlag();

        //update flame Enemy
        timer += System.currentTimeMillis() - lastTime;
        if(timer > 12000) {
            updateEnemy();
            timer = 0;
        }
        lastTime = System.currentTimeMillis();


        if(enemies.isEmpty()) {
            port = new Item(tileMap,3);
            port.setTilePosition(37,4);
            items.add(port);

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
        if (camy >0.5 && camy <1.5) {
            camy = -player.gety() + (float) sectorSize / 2;
            player.moveCamY = true;
        }
        else {
            camy = -ysector * sectorSize;
            player.moveCamY = false;
        }
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
                if(item.getType() != 3)
                {
                    items.remove(i);
                    i--;
                    item.collected(player);
                }else{
                    finish();
                }
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
        gsm.setState(gsm.PLAY2);
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

    public static void savePlayer(Player p) {
        p.changeHP(-100 + player.getCurrentHP());
        p.changeMP(-50 + player.getCurrentMP());
        p.changeNumHealthPot(-2 + player.getNumHealthPot());
        p.changeNumManaPot(-2 + player.getNumManaPot());
        p.addLevel(player.getLevel() - 1);

        if(player.hasSword())	p.gotSword();

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
