//Class State for character sex selection and procedes to level 1
//Classe Estado para escolha do sexo do jogador e que segue para o nivel 1

package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;

public class CharacterSexState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
		"Male", "Female"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	private AudioPlayer confirmMusic;
	private AudioPlayer selectMusic;
	
	public CharacterSexState(GameStateManager gsm) {
		this.gsm = gsm;
		
		confirmMusic = new AudioPlayer("/SFX/Confirm8-Bit.mp3");
		selectMusic = new AudioPlayer("/SFX/Select8-Bit.mp3");
		
		try {
			bg = new Background("/Backgrounds/menubg1.gif", 1);
			//bg.setVector(-0.1, 0);			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
			
			font = new Font("Arial", Font.PLAIN, 35);
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
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		//g.drawString("Ninja Adventures", 40, 70);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 60 + i * 120, 200);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			confirmMusic.play();
			gsm.setMale(true);
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		
		if(currentChoice == 1) {
			confirmMusic.play();
			gsm.setMale(false);
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		
		if(k == KeyEvent.VK_RIGHT) {
			selectMusic.play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		
		if(k == KeyEvent.VK_LEFT) {
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