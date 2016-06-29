//A Trap on ceiling that falls of it if the player touches de ground directly under it or bellow a certain X of tilemap
//Armadilha de tecto que cai caso o jogador toque no chão directamente abaixo dela ou este passe um determinado X do tilemap

package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class SpikeTecto2 extends Enemy {
	
	private BufferedImage[] sprites;
	
	public SpikeTecto2(TileMap tm) {

		super(tm);
		
		moveSpeed = 2.8;
		maxSpeed = 1;
		fallSpeed = 1;
		maxFallSpeed = 10.0;
		
		width = 64;
		height = 32;
		cwidth = 64;
		cheight = 32;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Enemies/SpikeMetal.png"));
			
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