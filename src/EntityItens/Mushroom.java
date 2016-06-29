//Collectible item that raises player health if it is not full
//Item colecionável que aumenta a vida do jogador caso esta não esteja já cheia

package EntityItens;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Mushroom extends Itens {
	
	private BufferedImage[] sprites;
	
	public Mushroom(TileMap tm) {
		
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 16;
		cheight = 16;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Itens/Mushroom.png"));
			
			sprites = new BufferedImage[2];
			
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(500);
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