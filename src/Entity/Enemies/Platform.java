//Is a platform that moves in vertical or horizontal axis for player transportation
//Uma plataforma utilizada para transportar o jogador no eixo horizontal ou vertical

package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Platform extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Platform(TileMap tm) {

		super(tm);
		
		moveSpeed = 1;
		maxSpeed = 1;
		fallSpeed = 1;
		maxFallSpeed = 10;
		
		width = 64;
		height = 64;
		cwidth = 60;
		cheight = 32;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Enemies/cloud_plat.png"));
			
			sprites = new BufferedImage[1];
			
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		down = true;
	}
	
	public void update() {

		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
}