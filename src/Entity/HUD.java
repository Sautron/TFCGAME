package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	private Player player;
	
	private BufferedImage image;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	private BufferedImage image5;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/Heart.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/HUD/HeartBlack.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/HUD/kunaiSingle.png"));
			image4 = ImageIO.read(getClass().getResourceAsStream("/HUD/key_disabled.png"));
			image5 = ImageIO.read(getClass().getResourceAsStream("/HUD/key_picked.png"));			
	
			font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		if(player.getHealth() == 5){
			g.drawImage(image, 9, 10, null);
			g.drawImage(image, 25, 10, null);
			g.drawImage(image, 41, 10, null);
			g.drawImage(image, 57, 10, null);
			g.drawImage(image, 73, 10, null);
		}
		
		if(player.getHealth() == 4){
			g.drawImage(image, 9, 10, null);
			g.drawImage(image, 25, 10, null);
			g.drawImage(image, 41, 10, null);
			g.drawImage(image, 57, 10, null);
			g.drawImage(image2, 73, 10, null);
		}
		
		if(player.getHealth() == 3){
			g.drawImage(image, 9, 10, null);
			g.drawImage(image, 25, 10, null);
			g.drawImage(image, 41, 10, null);
			g.drawImage(image2, 57, 10, null);
			g.drawImage(image2, 73, 10, null);
		}
		
		if(player.getHealth() == 2){
			g.drawImage(image, 9, 10, null);
			g.drawImage(image, 25, 10, null);
			g.drawImage(image2, 41, 10, null);
			g.drawImage(image2, 57, 10, null);
			g.drawImage(image2, 73, 10, null);
		}
		
		if(player.getHealth() == 1){
			g.drawImage(image, 9, 10, null);
			g.drawImage(image2, 25, 10, null);
			g.drawImage(image2, 41, 10, null);
			g.drawImage(image2, 57, 10, null);
			g.drawImage(image2, 73, 10, null);
		}
		
		if(player.getHealth() == 0){
			g.drawImage(image2, 9, 10, null);
			g.drawImage(image2, 25, 10, null);
			g.drawImage(image2, 41, 10, null);
			g.drawImage(image2, 57, 10, null);
			g.drawImage(image2, 73, 10, null);
		}
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		if(player.getLevel() == 1){
			g.drawString("LEVEL 1", 150, 25);
		}
		
		if(player.getLevel() == 2){
			g.drawString("LEVEL 2", 150, 25);
		}
		
		if(player.getLevel() == 3){
			g.drawString("LEVEL 3", 150, 25);
			if(player.getKey()){
				g.drawImage(image5, 9, 50, null);
			}
			else{
				g.drawImage(image4, 9, 50, null);
			}
		}
		
		
		//g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
		if(player.getKunai() || player.getLevel() == 2){
			g.drawImage(image3, 9, 35, null);
			g.drawString(player.getFire() / 400 + "/" + player.getMaxFire() / 400, 40, 45);
		}
		g.drawString(player.getTimerM() + ":" + player.getTimerS1() + "" + player.getTimerS(), 285, 25);
		g.drawString(player.getPoints() + "", 285, 45);
	}
}