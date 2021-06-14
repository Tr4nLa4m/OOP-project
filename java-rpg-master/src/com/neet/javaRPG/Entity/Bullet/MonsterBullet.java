package com.neet.javaRPG.Entity.Bullet;


import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Entity;
import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MonsterBullet extends Entity {

    protected int x;
    protected int y;
    private int typeBullet;
    private int homex;
    private int homey;


    private BufferedImage[] Sprite;




    public MonsterBullet(TileMap tm, Enemy enemy) {
        super(tm);
        this.homex = x;
        this.homey = y;
        setTilePosition(x, y);

        width = 32;
        height = 32;
        cwidth = 28;
        cheight = 28;

        Sprite = new BufferedImage[3];
        Sprite[0] = Content.MONSTER1[0][9];
        Sprite[1] = Content.MONSTER1[0][10];
        Sprite[2] = Content.MONSTER1[0][11];
        setBound(x + xmap -width / 2,y + ymap-height / 2, cwidth, cheight);
    }

    public void update(Enemy enemy){

    }

    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }
}

