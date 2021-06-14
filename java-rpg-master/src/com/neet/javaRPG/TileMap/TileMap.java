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
	private Tile[] tiles = new Tile[70];

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
		speed = 4;
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
			}
			else if(IDTile == 29) {
				subimage = tileset.getSubimage(0, 0, 6 * tileSize, 9 * tileSize);
			}else if(IDTile == 30 || IDTile == 31 || IDTile == 65) {
				subimage = tileset.getSubimage(0, 0, 2 * tileSize, 4 * tileSize);
			}else if(IDTile == 68) {
				subimage = tileset.getSubimage(0, 0, 2 * tileSize, 3 * tileSize);
			}
			else {
				subimage = tileset.getSubimage(0, 0, tileSize, tileSize);
			}
			int TileType = Tile.BLOCKED;
			if(IDTile == 0 ||IDTile == 1 || IDTile == 5 || IDTile == 21 || IDTile == 22 || IDTile == 24 || IDTile == 25
					 || IDTile == 30 || IDTile == 31 ||IDTile == 66 || IDTile == 67 ||(IDTile >= 40 && IDTile <= 58))
					TileType = Tile.NORMAL;
			tiles[IDTile] = new Tile(subimage, TileType);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void loadMap(String s) {

		loadTiles("/Resources/Tilesets/tile0.png",0);
		loadTiles("/Resources/Tilesets/GrassBackGround.png",1);
		loadTiles("/Resources/Tilesets/Grass.png",2);
		loadTiles("/Resources/Tilesets/Bush.png",3);
		loadTiles("/Resources/Tilesets/Tree.png",4);
		loadTiles("/Resources/Tilesets/DirtTileset.png",5);
		for(int i = 6 ; i <= 20; i++)
			loadTiles("/Resources/Tilesets/CliffTileset.png",i);
		loadTiles("/Resources/Tilesets/tile21.png",21);
		loadTiles("/Resources/Tilesets/tile22.png",22);
		loadTiles("/Resources/Tilesets/tile23.png",23);
		loadTiles("/Resources/Tilesets/tile24.png",24);
		loadTiles("/Resources/Tilesets/tile25.png",25);
		loadTiles("/Resources/Tilesets/tile26.png",26);
		loadTiles("/Resources/Tilesets/tile27.png",27);
		loadTiles("/Resources/Tilesets/tile28.png",28);
		loadTiles("/Resources/Tilesets/tile29.png",29);
		loadTiles("/Resources/Tilesets/tile30.png",30);
		loadTiles("/Resources/Tilesets/tile31.png",31);
		loadTiles("/Resources/Tilesets/tile32.png",32);
		loadTiles("/Resources/Tilesets/tile33.png",33);
		loadTiles("/Resources/Tilesets/tile34.png",34);
		loadTiles("/Resources/Tilesets/tile35.png",35);
		loadTiles("/Resources/Tilesets/tile36.png",36);
		loadTiles("/Resources/Tilesets/tile37.png",37);
		loadTiles("/Resources/Tilesets/tile38.png",38);
		loadTiles("/Resources/Tilesets/tile39.png",39);
		loadTiles("/Resources/Tilesets/tile40.png",40);
		loadTiles("/Resources/Tilesets/tile41.png",41);
		loadTiles("/Resources/Tilesets/tile42.png",42);
		loadTiles("/Resources/Tilesets/tile43.png",43);
		loadTiles("/Resources/Tilesets/tile44.png",44);
		loadTiles("/Resources/Tilesets/tile45.png",45);
		loadTiles("/Resources/Tilesets/tile46.png",46);
		loadTiles("/Resources/Tilesets/tile47.png",47);
		loadTiles("/Resources/Tilesets/tile48.png",48);
		loadTiles("/Resources/Tilesets/tile49.png",49);
		loadTiles("/Resources/Tilesets/tile50.png",50);
		loadTiles("/Resources/Tilesets/tile51.png",51);
		loadTiles("/Resources/Tilesets/tile52.png",52);
		loadTiles("/Resources/Tilesets/tile53.png",53);
		loadTiles("/Resources/Tilesets/tile54.png",54);
		loadTiles("/Resources/Tilesets/tile55.png",55);
		loadTiles("/Resources/Tilesets/tile56.png",56);
		loadTiles("/Resources/Tilesets/tile57.png",57);
		loadTiles("/Resources/Tilesets/tile58.png",58);
		loadTiles("/Resources/Tilesets/tile59.png",59);
		loadTiles("/Resources/Tilesets/tile60.png",60);
		loadTiles("/Resources/Tilesets/tile61.png",61);
		loadTiles("/Resources/Tilesets/tile62.png",62);
		loadTiles("/Resources/Tilesets/tile63.png",63);
		loadTiles("/Resources/Tilesets/tile64.png",64);
		loadTiles("/Resources/Tilesets/tile65.png",65);
		loadTiles("/Resources/Tilesets/tile66.png",66);
		loadTiles("/Resources/Tilesets/tile67.png",67);
		loadTiles("/Resources/Tilesets/tile68.png",68);
		loadTiles("/Resources/Tilesets/tile69.png",69);


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
	public void draw1(Graphics2D g) {

		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

			if(row >= numRows) break;

			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {

				if(col >= numCols) break;
				if(map[row][col] == 0 || map[row][col] == 69) continue;
				g.drawImage(tiles[40].getImage(),x + col*tileSize, y + row * tileSize, null);

				g.drawImage(tiles[map[row][col]].getImage(), x + col * tileSize ,
							y + row * tileSize , null);
			}

		}

	}
	
}



















