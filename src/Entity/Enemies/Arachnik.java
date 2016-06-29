//Enemy that moves veritcally and shoots a ranged attack -net shot- (associated on lvl 1)
//Inimigo que se desloca verticalmente e ataca à distância -net shot- (associado no nivel 1)

package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Arachnik extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Arachnik(TileMap tm) {

		super(tm);
		
		moveSpeed = 0.4;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 5;
		damage = 2;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Enemies/arachnik.gif"));
			
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
	
	private void getNextPosition() {
		// movement
		if(down) {
			dy -= moveSpeed;
			
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		}
		
		else if(up) {
			dy += moveSpeed;
			
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
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
		
		// posiçao
		if(y < 45){
			down = false;
			up = true;
		}
		
		if(y > 120){
			up = false;
			down = true;
		}
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
}