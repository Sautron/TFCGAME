//Enemy that walks on both x and y axis and goes after the player if he enters is awareness area
//Inimigo que patrulha nos eixos x e y, e segue o jogador caso entre na sua area de percepção

package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Fly extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Fly(TileMap tm) {

		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 20;
		cheight = 18;
		moveSpeed = 1;
		
		health = maxHealth = 4;
		damage = 1;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Enemies/skull.png"));
			
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
		animation.setDelay(300);
		
		down = true;
	}
	
	private void getNextPosition(){
		//TODO
		if(e_up){
			dy += moveSpeed;
			if(dy > maxSpeed){
				moveSpeed = maxSpeed;
			}
		}
		if(dx > 0){
			facingRight = false;
		}
		else{
			facingRight = true;
		}
	}
	
	public void update() {
		// update position
		getNextPosition();

		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
}