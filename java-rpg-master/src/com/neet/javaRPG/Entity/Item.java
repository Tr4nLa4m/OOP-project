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
	public static final int PORT = 3;

	//position item
	public int ItemRow = -1;
	public int ItemCol = -1;


	//get position
	public int getItemRow(){ return ItemRow; }
	public int getItemCol(){ return ItemCol; }


	public Item(TileMap tm, int type) {
		super(tm);
		setType(type);
		if(type == 0 || type == 1){
			width = height = 32;
			cwidth = 18;
			cheight = 30;
			setBound(x + xmap -width/2 + 6,y + ymap-height/2,cwidth,cheight);
		}else if(type == 2){
			width = height = 32;
			cwidth = 28;
			cheight = 28;
			setBound(x + xmap -width/2 ,y + ymap-height/2,cwidth,cheight);
		}else if(type == 3){
			width = height = 80;
			cwidth = cheight = 40;
			setBound(x + xmap -width/2 + 12 ,y + ymap-height/2 + 12,cwidth,cheight);
		}
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
		}else if(type == PORT){
			sprite = Content.PORT[0][0];
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
			p.changeNumHealthPot(1);
		}
		else if(type == MP_ITEM){
			p.changeNumManaPot(1);
		}
		else if(type == PORT){

		}
	}

	@Override
	public void update() {
		super.update();
		if(type <= 1)	updateBound(x + xmap -width/2 + 6,y + ymap-height/2);
		else if(type == 2)	updateBound(x + xmap -width/2 ,y + ymap-height/2);
		else if(type == 3)	updateBound(x + xmap -width/2 + 12 ,y + ymap-height/2 + 12);

	}

	public void draw(Graphics2D g) {

		setMapPosition();
		g.drawImage(sprite, x + xmap - width / 2, y + ymap - height / 2, null);

		//draw bounds
		//drawBound(bound, Color.yellow, g);
	}
	
}
