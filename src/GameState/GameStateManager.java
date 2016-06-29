//Class that manages all the States existing on the aplication
//Classe que gere todos os Estados da aplicação

package GameState;

import Fonts.Fonts;

public class GameStateManager {
	
	
	private GameState[] gameStates;
	private int currentState;
	private boolean male;
	private int points;
	private int timerLVL1;
	private int hp;
	
	public static final int NUMGAMESTATES = 7;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int CHARACTERSEX = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int HELPSTATE = 6;
	
	public static final String RESOURCE_LOCATION ="./Resources/";
	public static final String FONT_LOCATION = RESOURCE_LOCATION + "Fonts/";
	
	public static void loadFonts(){
		Fonts.addFont(new Fonts("njnaruto.ttf"));
		Fonts.addFont(new Fonts("dh_gentry.ttf"));
		Fonts.addFont(new Fonts("BigKeyCaps.ttf"));
	}
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE){
			gameStates[state] = new MenuState(this);
		}
		
		if(state == LEVEL1STATE){
			gameStates[state] = new Level1State(this);
		}
		
		if(state == CHARACTERSEX){
			gameStates[state] = new CharacterSexState(this);
		}
		
		if(state == GAMEOVERSTATE){
			gameStates[state] = new GameOverState(this);
		}
		
		if(state == LEVEL2STATE){
			gameStates[state] = new Level2State(this);
		}
		
		if(state == LEVEL3STATE){
			gameStates[state] = new Level3State(this);
		}
		
		if(state == HELPSTATE){
			gameStates[state] = new HelpState(this);
		}
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public int getState(){
		return currentState;
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
	public void setMale(boolean b){
		male = b;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
	public void setHP(int hp){
		this.hp = hp;
	}
	
	public void setTimerLVL1(int timerLVL1){
		this.timerLVL1 = timerLVL1;
	}
	
	public int getTimerLVL1(){
		return timerLVL1;
	}
	
	public int getHP(){
		return hp;
	}
	
	public int getPoints(){
		return points;
	}
	
	public boolean getMale(){
		return male;
	}
}