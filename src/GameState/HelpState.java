//State where the User may go from the Menu to know the Keys used for playing the game
//Estado onde se encontra informação das teclas para o utilizador poder jogar

package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Audio.AudioPlayer;

import java.util.Random;

public class HelpState extends GameState {
	
	private Background bg;
	private AudioPlayer confirmMusic;
	private AudioPlayer selectMusic;
	private BufferedImage image, image2, image3, image4, image5, image6;
	
	private int currentChoice = 0;
	private String[] options = {
		"Back"
	};
	
	//private BufferedImage image;
	private Font font;
	private Font font2;
	private Font keyboard;
	Random rand = new Random();
	private int n = rand.nextInt(6)+1;

	
	public HelpState(GameStateManager gsm) {
		this.gsm = gsm;
		confirmMusic = new AudioPlayer("/SFX/Confirm8-Bit.mp3");
		selectMusic = new AudioPlayer("/SFX/Select8-Bit.mp3");
		
		try {
			bg = new Background("/HUD/BalaoHelp.png", 1);
			bg.setVector(0, 0);
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/NinjaBoyHead.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/HUD/NinjaGirlHead.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/HUD/SaraHead.png"));
			image4 = ImageIO.read(getClass().getResourceAsStream("/HUD/RodrigoHead.png"));
			image5 = ImageIO.read(getClass().getResourceAsStream("/HUD/shadowZombieHead.png"));
			image6 = ImageIO.read(getClass().getResourceAsStream("/HUD/RedZombieHead.png"));
			
			font = new Font("Arial", Font.PLAIN, 12);
			font2 = new Font("Arial", Font.BOLD, 14);
			keyboard = new Font ("BKCap", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		switch (n) {
        case 1:  
        	g.drawImage(image, 20, 176, null);;
        	break;
        case 2:  
        	g.drawImage(image2, 20, 176, null);
            break;
        case 3:  
        	g.drawImage(image3, 20, 176, null);
            break;
        case 4: 
        	g.drawImage(image4, 20, 176, null);
            break;
        case 5: 
        	g.drawImage(image5, 20, 176, null);
            break;
        case 6: 
        	g.drawImage(image6, 20, 176, null);
            break;
        default: 
        	g.drawImage(image2, 20, 176, null);
        	break;
		}
		/*if (n==1){
			g.drawImage(image, 20, 176, null);
		}
		if (n==1){
			g.drawImage(image, 20, 176, null);
		}
		if (n==1){
			g.drawImage(image, 20, 176, null);
		}
		else{
			g.drawImage(image2, 20, 176, null);
		}*/
		
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			
			else {
				g.setColor(Color.GRAY);
			}
			g.drawString(options[i], 220, 220 + i * 15);
		}
		g.setColor(Color.BLACK);
		
		g.drawString("UP - JUMP", 50, 30);
		g.drawString("LEFT - RUN LEFT", 50, 50);
		g.drawString("RIGHT - RUN RIGHT", 50, 70);
		g.drawString("P - PAUSE", 50, 90 );
		g.drawString("ENTER - INTERACT", 50, 110);
		g.drawString("E - GLIDE", 50, 130);
		g.drawString("R - MELEE ATTACK", 50, 150);
		g.drawString("F - RANGED ATTACK", 50, 170);
		
		g.setFont(keyboard);
		
		g.drawString("i", 10, 30);
		g.drawString("j", 10, 50);
		g.drawString("l", 10, 70);
		g.drawString("P", 10, 90);
		g.drawString("e", 10, 110);
		g.drawString("E", 10, 130);
		g.drawString("R", 10, 150);
		g.drawString("F", 10, 170);
		
		g.setFont(font2);
		g.drawString("KEYS", 140, 20);
	}
	
	private void select() {
		if(currentChoice == 0) {
			confirmMusic.play();
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		
		if(k == KeyEvent.VK_UP) {
			selectMusic.play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		
		if(k == KeyEvent.VK_DOWN) {
			selectMusic.play();
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
}