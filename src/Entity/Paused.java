package Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Paused{
	
	private BufferedImage image;
	
	public Paused() {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/pause.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
	}
}