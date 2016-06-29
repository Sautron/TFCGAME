//First State of the app where the user may Start the game, go for Help, check Highs Scores quit the application
//Primeiro Estado da aplicação onde o utilizador pode Iniciar jogo, aceder à Ajuda, ver Pontuações Altas ou Sair da aplicação

package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


import Audio.AudioPlayer;
import Main.GamePanel;

public class MenuState extends GameState {
	
	private boolean musica = true;
	private int secondsPassed;
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			secondsPassed++;
			if(secondsPassed % 101 == 0 && musica){
				bgMusic.play();
			}
		}
	};
		
	//timer
	public void start(){
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	//timer
	static Thread thread = new Thread();
	
	private AudioPlayer bgMusic;
	private AudioPlayer confirmMusic;
	private AudioPlayer selectMusic;
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {		
		"Start",
		"High Scores",
		"Help",
		"Quit",		
	};
	
	private Font naruto;	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		start();
		confirmMusic = new AudioPlayer("/SFX/Confirm8-Bit.mp3");
		bgMusic = new AudioPlayer("/Music/menu.mp3");
		selectMusic = new AudioPlayer("/SFX/Select8-Bit.mp3");
		
		bgMusic.play();
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/menubg.png", 1);
			bg.setVector(-0.1, 0);
			
			naruto = new Font ("Ninja Naruto", Font.PLAIN, 20);
			
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
		
		// draw title
		g.setColor(Color.BLACK);
		g.setFont(naruto);
		g.drawString("Ninja  Adventures", 75+2, 80+2);
		g.setColor(Color.WHITE);
		g.drawString("Ninja  Adventures", 75, 80);
		
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], GamePanel.WIDTH /2 - (GamePanel.WIDTH /20), (GamePanel.HEIGHT /10) *6 + i * (GamePanel.HEIGHT / 12));
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			confirmMusic.play();
			musica = false;
			bgMusic.stop();
			gsm.setState(GameStateManager.CHARACTERSEX);
		}
		
		if(currentChoice == 1) {
			confirmMusic.play();
			musica = false;
			bgMusic.stop();
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		
		if(currentChoice == 2) {
			confirmMusic.play();
			musica = false;
			bgMusic.stop();
			gsm.setState(GameStateManager.HELPSTATE);
		}
		
		if(currentChoice == 3) {
			confirmMusic.play();
			musica = false;
			bgMusic.stop();
			gsm.setState(GameStateManager.LEVEL3STATE);
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