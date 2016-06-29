//Collectible item that allows the player to open doors
//Item colecionável que permite abrir portas

package EntityItens;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Key extends Itens {
	
	private BufferedImage[] sprites;
	
	public Key(TileMap tm) {
		
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 20;
		cheight = 20;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Itens/KeyYellow.png"));
			
			sprites = new BufferedImage[8];
			
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(250);
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