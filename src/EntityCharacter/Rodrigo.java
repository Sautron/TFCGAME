//Character that helps player and gives him some info and unlocks moves
//Personagem que ajuda o jogador com informações e desbloqueia movimentos

package EntityCharacter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Character;
import TileMap.TileMap;

public class Rodrigo extends Character{
	
	private BufferedImage[] sprites;
	
	public Rodrigo(TileMap tm) {
		super(tm);
	
		width = 17;
		height = 32;
		cwidth = 20;
		cheight = 20;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
													 "/Sprites/Characters/Rodrigo.png"));
			
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
		animation.setDelay(Integer.MAX_VALUE);
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