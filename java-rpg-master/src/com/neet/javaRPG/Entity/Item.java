// Possibly redundant subclass of Entity.
// There are two types of items: Axe and boat.
// Upon collection, informs the Player
// that the Player does indeed have the item.

package com.neet.javaRPG.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;


import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.TileMap.TileMap;

public class Item extends Entity{
	
	private BufferedImage sprite;
	private int type;
	public static final int HP_ITEM = 0;
	public static final int MP_ITEM = 1;
	public static final int SWORD = 2;

	//position item
	public int ItemRow = -1;
	public int ItemCol = -1;


	//get position
	public int getItemRow(){ return ItemRow; }
	public int getItemCol(){ return ItemCol; }


	public Item(TileMap tm) {
		super(tm);
		type = -1;
		width = height = 32;
		cwidth = cheight = 28;
		setBound(x + xmap -width/2,y + ymap-height/2,cwidth,cheight);
	}
	
	public void setType(int i) {
		type = i;
		if(type == SWORD) {
			sprite = Content.ITEMS[0][0];
		}
		else if(type == HP_ITEM) {
			sprite = Content.ITEMS[0][1];
		}
		else if(type == MP_ITEM){
			sprite = Content.ITEMS[0][2];
		}
	}

	public int getType(){
		return this.type;
	}

	public void collected(Player p) {
		if(type == SWORD) {
			p.gotSword();
		}
		else if (type == HP_ITEM){
			p.changeHP(50);
		}
		else if(type == MP_ITEM){
			p.changeMP(50);
		}
	}

	@Override
	public void update() {
		super.update();
		updateBound(x + xmap -width/2,y + ymap-height/2);
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(sprite, x + xmap - width / 2, y + ymap - height / 2, null);

		//draw bounds
		drawBound(bound, Color.yellow, g);
	}
	
}
