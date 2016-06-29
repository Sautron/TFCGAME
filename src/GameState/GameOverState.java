//This Class serves the purpose of when the player loses the game. He has 2 options: Continue the game, or Quit to Menu
//Esta Classe serve o propósito de quando o jogador perde o jogo. Ele tem 2 opções: Continuar, ou Sair para o Menu

package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;

public class GameOverState extends GameState {
	
	private Background bg;
	private AudioPlayer confirmMusic;
	private AudioPlayer selectMusic;
	
	private int currentChoice = 0;
	private String[] options = {
		"Continue",
		"Quit to Menu"
	};
	
	private Font font;
	
	public GameOverState(GameStateManager gsm) {
		this.gsm = gsm;
		confirmMusic = new AudioPlayer("/SFX/Confirm8-Bit.mp3");
		selectMusic = new AudioPlayer("/SFX/Select8-Bit.mp3");
		
		try {
			bg = new Background("/HUD/GameOver.png", 1);
			bg.setVector(0, 0);
			
			font = new Font("Arial", Font.PLAIN, 12);
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
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			
			else {
				g.setColor(Color.GRAY);
			}
			g.drawString(options[i], 200, 210 + i * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			confirmMusic.play();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		
		if(currentChoice == 1) {
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