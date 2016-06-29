//Class that displays an image when the player dies and precedes the GameOverState
//Classe que reproduz uma imagem a quando a morte do jogador e precede o Estado de Game Over

package Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PreGameOverState{
	
	private BufferedImage image;
	
	public PreGameOverState() {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/GameOver.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
	}
}