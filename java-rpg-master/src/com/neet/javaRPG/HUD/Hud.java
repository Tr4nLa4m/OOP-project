package com.neet.javaRPG.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.neet.javaRPG.Entity.Enemy;
import com.neet.javaRPG.Entity.Player;
import com.neet.javaRPG.Main.GamePanel;
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
		g.fillRect(87, yoffset + 26, (int)(36 * player.getCurrentHP() / player.getMaxHP()), 5);
		Content.drawString(g, "HP", 81, yoffset + 18);
		
		g.setColor(new Color(0, 0, 255));
		g.fillRect(87, yoffset + 39, (int)(36 * player.getCurrentMP() / player.getMaxMP()), 5);
		Content.drawString(g, "MP", 81, yoffset + 32);
		
		Content.drawString(g, "Lv. " + player.getLevel(), 81, yoffset + 4);
		Content.drawString(g, "ATK: " + player.getATK(), 130, yoffset + 18);
		Content.drawString(g, "DEF: " + player.getDEF(), 130, yoffset + 32);
		
		// draw diamond amount
		g.setColor(textColor);
		g.setFont(font);
		
		//draw items
		if(player.hasSword()) g.drawImage(sword, 30, yoffset + 4, null);
	}
}
