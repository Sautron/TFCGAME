//Enemy that walks horizontally and turns back if he hits a wall, also doesn't fall from
//platforms if only one of the sides is a wall and the other a fall
//Inimigo que anda horizontalmente e muda de sentido de marcha caso entre em contacto com
//uma parede, não cai das platformas caso um dos lados esteja fechado com 1 parede


package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Slugger extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Slugger(TileMap tm, boolean fallingR) {
		
		super(tm);
		moveSpeed = 0.3;
		maxSpeed = 0.6;
		fallingRL = fallingR;
		width = 30;
		height = 30;
		cwidth = 15;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Enemies/slugger.gif"));
			
			sprites = new BufferedImage[3];
			
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
		
		right = true;
		facingRight = true;
	}
	
	private void getNextPosition() {
		// movement
		if(left) {
			dx -= moveSpeed;
			
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		
		else if(right) {
			dx += moveSpeed;
			
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		// falling
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
		
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		if(falling) {
			if(fallingRL){
				falling = false;
				right = false;
				left = true;
			}
			else{
				falling = false;
				right = true;
				left = false;
			}
		}
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}
}