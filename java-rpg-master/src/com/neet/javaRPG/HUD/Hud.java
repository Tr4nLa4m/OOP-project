package com.neet.javaRPG.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Player;

import com.neet.javaRPG.Manager.Content;

public class Hud {
	
	private int yoffset;

	private BufferedImage sword;
	
	private Player player;
	
	private Font font;
	private Color textColor; 
	
	public Hud(Player p, ArrayList<Enemy> d) {
		
		player = p;
		yoffset = 0;

		sword = Content.ITEMS[0][0];
		
		font = new Font("Arial", Font.PLAIN, 10);
		textColor = new Color(47, 64, 126);
	}
	
	public void draw(Graphics2D g) {
		
		// draw hud
		
		// draw bar
		g.setColor(new Color(255, 0, 0)); //red
		g.fillRect(10, yoffset + 16, (int)2*(36 * player.getCurrentHP() / player.getMaxHP()), 8);
		Content.drawString(g, "HP", 10, yoffset + 4);
		Content.drawString(g, "" + player.getCurrentHP(), 40, yoffset + 4);
		
		g.setColor(new Color(0, 0, 255));
		g.fillRect(10, yoffset + 40, (int)2*(36 * player.getCurrentMP() / player.getMaxMP()), 8);
		Content.drawString(g, "MP", 10, yoffset + 28);
		Content.drawString(g, "" + player.getCurrentMP(), 40, yoffset + 28);
		
		Content.drawString(g, "Lv. " + player.getLevel(), 100, yoffset + 4);
		Content.drawString(g, "ATK: " + player.getATK(), 100, yoffset + 18);
		Content.drawString(g, "DEF: " + player.getDEF(), 100, yoffset + 32);
		Content.drawString(g, "Item" , 200, yoffset +4);
		
		// draw diamond amount
		g.setColor(textColor);
		g.setFont(font);
		
		//draw items
		if(player.hasSword()) g.drawImage(sword, 200, yoffset + 12, null);
	}
}
