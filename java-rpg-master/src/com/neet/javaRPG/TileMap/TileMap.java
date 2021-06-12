package com.neet.javaRPG.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.neet.javaRPG.Main.GamePanel;

public class TileMap {
	
	// position
	private int x;
	private int y;
	private int xdest;
	private int ydest;
	private int speed;
	private boolean moving;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// map
	private int[][] map;
	public final int tileSize  = 32;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[] tiles = new Tile[29];

	//ID Tile
	public int IDTile;



	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap() {
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		speed = 10;
	}
	
	public void loadTiles(String s, int IDTile) {

		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			BufferedImage subimage;
			if(IDTile == 4){
				subimage = tileset.getSubimage(0, 0, tileSize, 48);
			}
			else if(IDTile >= 6 && IDTile <= 9){
				subimage = tileset.getSubimage((IDTile - 6) * tileSize, 0, tileSize, tileSize);
			}else if(IDTile == 10){
				subimage = tileset.getSubimage(0, tileSize, tileSize, tileSize);
			}else if(IDTile == 11 || IDTile == 12) {
				subimage = tileset.getSubimage((IDTile - 9) * tileSize, tileSize, tileSize, tileSize);
			}else if(IDTile >= 13 && IDTile <= 16) {
				subimage = tileset.getSubimage((IDTile - 13) * tileSize, 2 * tileSize, tileSize, tileSize);
			}else if(IDTile >= 17 && IDTile <= 20) {
				subimage = tileset.getSubimage((IDTile - 17) * tileSize, 3 * tileSize, tileSize, tileSize);
			}else {
				subimage = tileset.getSubimage(0, 0, tileSize, tileSize);
			}
			int TileType = Tile.BLOCKED;
			if(IDTile == 1 || IDTile == 5 || IDTile == 21 || IDTile == 22 || IDTile == 24 || IDTile == 25)
					TileType = Tile.NORMAL;
			tiles[IDTile] = new Tile(subimage, TileType);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void loadMap(String s) {

		loadTiles("/Resources/Sprites/GrassBackGround.png",1);
		loadTiles("/Resources/Sprites/Grass.png",2);
		loadTiles("/Resources/Sprites/Bush.png",3);
		loadTiles("/Resources/Sprites/Tree.png",4);
		loadTiles("/Resources/Sprites/DirtTileset.png",5);
		for(int i = 6 ; i <= 20; i++)
			loadTiles("/Resources/Sprites/CliffTileset.png",i);
		loadTiles("/Resources/Sprites/tile21.png",21);
		loadTiles("/Resources/Sprites/tile22.png",22);
		loadTiles("/Resources/Sprites/tile23.png",23);
		loadTiles("/Resources/Sprites/tile24.png",24);
		loadTiles("/Resources/Sprites/tile25.png",25);
		loadTiles("/Resources/Sprites/tile26.png",26);
		loadTiles("/Resources/Sprites/tile27.png",27);
		loadTiles("/Resources/Sprites/tile28.png",28);
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			

			xmin = -width;
			xmax = 0;

			ymin = -height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public int getx() { return x; }
	public int gety() { return y; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public int getType(int row, int col) {
		return tiles[map[row][col]].getType();
	}
	public int getIndex(int row, int col) {
		return map[row][col];
	}
	public boolean isMoving() { return moving; }
	
	public void setTile(int row, int col, int index) {
		map[row][col] = index;
	}
	public void replace(int i1, int i2) {
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				if(map[row][col] == i1) map[row][col] = i2;
			}
		}
	}
	
	public void setPosition(int x, int y) {
		xdest = x;
		ydest = y;
	}
	public void setPositionImmediately(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void update() {
		if(x < xdest) {
			x += speed;
			if(x > xdest) {
				x = xdest;
			}
		}
		if(x > xdest) {
			x -= speed;
			if(x < xdest) {
				x = xdest;
			}
		}
		if(y < ydest) {
			y += speed;
			if(y > ydest) {
				y = ydest;
			}
		}
		if(y > ydest) {
			y -= speed;
			if(y < ydest) {
				y = ydest;
			}
		}
		
		//fixBounds();
		
		colOffset = -this.x / tileSize;
		rowOffset = -this.y / tileSize;
		
		if(x != xdest || y != ydest) moving = true;
		else moving = false;
		
	}
	
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				g.drawImage(tiles[1].getImage(),x + col*tileSize, y + row * tileSize, null);
				if(map[row][col] != 4)
					g.drawImage(tiles[map[row][col]].getImage(), x + col * tileSize, y + row * tileSize, null);
				else
					g.drawImage(tiles[map[row][col]].getImage(), x + col * tileSize ,
							y + row * tileSize - 16, null);
			}
			
		}
		
	}
	
}



















