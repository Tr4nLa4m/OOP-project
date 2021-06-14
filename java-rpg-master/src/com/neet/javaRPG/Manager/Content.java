package com.neet.javaRPG.Manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Content {

	public static BufferedImage[][] HIT_EFFECT = load("/Resources/Effect/HitEffect.png", 24,24);
	public static BufferedImage[][] MENUBG = load("/Resources/Logo/Theme.png", 640, 640);
	public static BufferedImage[][] DEATH_EFFECT = load("/Resources/Effect/EnemyDeathEffect.png", 32,32);
	public static BufferedImage[][] PLAYER_T = load("/Resources/Sprites/Player.png", 64, 64);
	public static BufferedImage[][] DIAMOND = load("/Resources/Sprites/diamond.png", 16, 16);
	public static BufferedImage[][] MONSTER = load("/Resources/Sprites/Bat.png", 16, 16);
	public static BufferedImage[][] MONSTER1 = load("/Resources/Sprites/monster.png", 32,32);
	public static BufferedImage[][] ITEMS = load("/Resources/Sprites/Item.png", 32, 32);
	public static BufferedImage[][]	PORT = load("/Resources/Sprites/port.png", 80, 80);


	
	public static BufferedImage[][] font = load("/Resources/HUD/font.gif", 8, 8);

	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
	public static void drawString(Graphics2D g, String s, int x, int y) {
		s = s.toUpperCase();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) c = 36; // slash
			else if(c == 58) c = 37; // colon
			else if(c == 32) c = 38; // space
			else if(c >= 65 && c <= 90) c -= 65; // letters
			else if(c >= 48 && c <= 57) c -= 22; // numbers
			int row = c / font[0].length;
			int col = c % font[0].length;
			g.drawImage(font[row][col], x + 8 * i, y, null);
		}
	}
	public static void drawStringBig(Graphics2D g, String s, int x, int y,int size) {
		s = s.toUpperCase();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) c = 36; // slash
			else if(c == 58) c = 37; // colon
			else if(c == 32) c = 38; // space
			else if(c >= 65 && c <= 90) c -= 65; // letters
			else if(c >= 48 && c <= 57) c -= 22; // numbers
			int row = c / font[0].length;
			int col = c % font[0].length;
			g.drawImage(font[row][col], x + size * i, y,size,size, null);
		}
	}
}
