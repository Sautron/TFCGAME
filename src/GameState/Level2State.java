//package
package GameState;

//imports
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import EntityItens.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Entity.Enemies.*;

public class Level2State extends GameState{
	
	//variaveis
	private boolean flinch2;
	private int adversarioFlinch, adversarioFlinch4, adversarioFlinch3;
	private boolean flinch = false;
	private long flinchTimer;
	private boolean vez = true;
	private boolean interacting = false;
	private boolean first = true;
	private boolean pause = false;
	private boolean morreu3 = false;
	private boolean morreu2 = false;
	private boolean morreu = false;
	private boolean musica = true;
	private int secondsPassed = 0;
	private int points;
	private int sec = 0;
	private double width = 32;
	private double height = 32;
	
	//zombies
	private int[] zX = new int[10];
	private int[] zX2 = new int[10];
	private int[] zXL = new int[10];
	private int[] zXR = new int[10];
	private int[] zXP = new int[10];
	private boolean vez3 = true;
	
	//Fly
	private int[] Fup = new int[10];
	private int[] Fdown = new int[10];
	private int[] Fleft = new int[10]; 
	private int[] Fright = new int[10];
	private int[] FAup = new int[10];
	private int[] FAdown = new int[10];
	private int[] FAleft = new int[10];
	private int[] FAright = new int[10];
	private double[] enemyX = new double[10];
	private int[] enemyY = new int[10];

	private ArrayList<Itens> coins;
	private ArrayList<Itens> mushrooms;
	private ArrayList<Itens> portal;
	private ArrayList<Enemy> sluggers;
	private ArrayList<Enemy> zombies;
	private ArrayList<Enemy> fly;
	private ArrayList<Explosion> explosions;
	private ArrayList<Enemy> platforms;
	private ArrayList<Enemy> traps;

	//timer
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			secondsPassed++;
			if(secondsPassed % 82 == 0 && musica){
				bgMusic.play();
			}
			
			if(player.getHealth() == 0){
				bgMusic.stop();
			}
			
			if(player.getHealth() < 0){
				player.setHealth(0);
			}
		}
	};
	
	//timer
	public void start(){
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	//timer
	static Thread thread = new Thread();
	
	//criacao de classes
	private TileMap tileMap;
	private Background bg;
	private Player player;
	private EnemyAI enemyAI;
	
	private HUD hud;
	private PreGameOverState gameoverstates;
	private Paused paused;
	
	private AudioPlayer bgMusic;
	
	//construtor
	public Level2State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	//funçao que cria os enimigos
	private void populateEnemies() {
		sluggers = new ArrayList<Enemy>();
		zombies = new ArrayList<Enemy>();
		fly = new ArrayList<Enemy>();
		platforms = new ArrayList<Enemy>();
		traps = new ArrayList<Enemy>();
		
		Slugger sl;
		ShadowZombie sz;
		Fly s;
		Platform p;
		SpikeTecto st;
		
		
		//SLUGGER
		Point[] points5 = new Point[]{
				new Point((int)(width * 14), (int)(height * 24.7)),
				new Point((int)(width * 21), (int)(height * 24.7)),
				new Point((int)(width * 23), (int)(height * 22.7)),
				new Point((int)(width * 33), (int)(height * 19.7)),
				new Point((int)(width * 42), (int)(height * 11.7)),
				new Point((int)(width * 45), (int)(height * 10.7)),
				new Point((int)(width * 61), (int)(height * 25.7)),
				new Point((int)(width * 65), (int)(height * 25.7)),
				new Point((int)(width * 67), (int)(height * 25.7)),
				new Point((int)(width * 90), (int)(height * 6.7)),
				new Point((int)(width * 94), (int)(height * 6.7)),
				new Point((int)(width * 104), (int)(height * 6.7))
		};
		
		//True queda à esquerda - False queda à direita
		for(int i = 0; i < points5.length; i++){
			if(i == 0 || (i > 5 && i <= 8)){
				sl = new Slugger(tileMap, true);
			}
			else{
				sl = new Slugger(tileMap, false);
			}
			sl.setPosition(points5[i].x, points5[i].y);
			sluggers.add(sl);
		}
		
		//SHADOWZOMBIE
		Point[] points2 = new Point[]{
				new Point((int)(width * 54.5), (int)(height * 20.5)),
				new Point((int)(width * 74.5), (int)(height * 25.5)),
				new Point((int)(width * 82.5), (int)(height * 25.5)),
				new Point((int)(width * 80.5), (int)(height * 15.5)),
				new Point((int)(width * 93.5), (int)(height * 6.5)),
				new Point((int)(width * 97.5), (int)(height * 6.5)),
				new Point((int)(width * 103.5), (int)(height * 6.5)),
		};
		
		for(int i = 0; i < points2.length; i++){
			sz = new ShadowZombie(tileMap);
			sz.setPosition(points2[i].x, points2[i].y);
			zombies.add(sz);
		}
		
		//FLY
		Point[] points = new Point[] {
				new Point((int)(width * 24.5), (int)(height * 11.5)),
				new Point((int)(width * 15.5), (int)(height * 3.5)),
				new Point((int)(width * 92.5), (int)(height * 17.5)),
				new Point((int)(width * 79.5), (int)(height * 17.5))
		};
		
		for(int i = 0; i < points.length; i++) {
			s = new Fly(tileMap);
			s.setPosition(points[i].x, points[i].y);
			fly.add(s);
		}
		
		//PLATFORM
		Point[] points4 = new Point[]{
			new Point((int)(width * 95), (int)(height * 25)),
			new Point((int)(width * 105.5), (int)(height * 25.5)),
			new Point((int)(width * 85.5), (int)(height * 16.5))
		};
		
		for(int i = 0; i < points4.length; i++){
			p = new Platform(tileMap);
			p.setPosition(points4[i].x, points4[i].y);
			platforms.add(p);
		};
		
		//SPIKETECTO
		Point[] points3 = new Point[]{
				new Point((int)(width * 50), (int)(height * 16))
		};
		
		for(int i = 0; i < points3.length; i++){
			st = new SpikeTecto(tileMap);
			st.setPosition(points3[i].x, points3[i].y);
			traps.add(st);
		}
	}
	
	//funçao que cria os itens
	private void populateItens(){
		coins = new ArrayList<Itens>();
		mushrooms = new ArrayList<Itens>();
		portal = new ArrayList<Itens>();
		Portal p;
		Coins c;
		Mushroom m;
		
		Point[] points2 = new Point[]{
			new Point((int)(width * 84), (int)(height * 32))	
		};
		
		Point[] points3 = new Point[]{
			new Point((int)(width * 21.5), (int)(height * 3.5)),
			new Point((int)(width * 49.5), (int)(height * 21.5)),
			new Point((int)(width * 50.5), (int)(height * 21.5)),
			new Point((int)(width * 78.5), (int)(height * 19.5)),
			new Point((int)(width * 73.5), (int)(height * 6.5)),
			new Point((int)(width * 74.5), (int)(height * 6.5))
		};
		
		Point[] points = new Point[]{
			//C
			new Point((int)(width * 39.5), (int)(height * 35.5)),
			new Point((int)(width * 39.5), (int)(height * 34.5)),
			new Point((int)(width * 39.5), (int)(height * 33.5)),
			new Point((int)(width * 39.5), (int)(height * 32.5)),
			new Point((int)(width * 39.5), (int)(height * 31.5)),
			new Point((int)(width * 40.5), (int)(height * 35.5)),
			new Point((int)(width * 41.5), (int)(height * 35.5)),
			new Point((int)(width * 42.5), (int)(height * 35.5)),
			new Point((int)(width * 40.5), (int)(height * 31.5)),
			new Point((int)(width * 41.5), (int)(height * 31.5)),
			new Point((int)(width * 42.5), (int)(height * 31.5)),
			                                                
			//A                                             
			new Point((int)(width * 44.5), (int)(height * 31.5)),
			new Point((int)(width * 44.5), (int)(height * 32.5)),
			new Point((int)(width * 44.5), (int)(height * 33.5)),
			new Point((int)(width * 44.5), (int)(height * 34.5)),
			new Point((int)(width * 44.5), (int)(height * 35.5)),
			new Point((int)(width * 45.5), (int)(height * 31.5)),
			new Point((int)(width * 45.5), (int)(height * 33.5)),
			new Point((int)(width * 46.5), (int)(height * 33.5)),
			new Point((int)(width * 46.5), (int)(height * 31.5)),
			new Point((int)(width * 47.5), (int)(height * 31.5)),
			new Point((int)(width * 47.5), (int)(height * 32.5)),
			new Point((int)(width * 47.5), (int)(height * 33.5)),
			new Point((int)(width * 47.5), (int)(height * 34.5)),
			new Point((int)(width * 47.5), (int)(height * 35.5)),
			                                                
			//J                                             
			new Point((int)(width * 49.5), (int)(height * 35.5)),
			new Point((int)(width * 49.5), (int)(height * 34.5)),
			new Point((int)(width * 50.5), (int)(height * 35.5)),
			new Point((int)(width * 50.5), (int)(height * 31.5)),
			new Point((int)(width * 51.5), (int)(height * 31.5)),
			new Point((int)(width * 51.5), (int)(height * 32.5)),
			new Point((int)(width * 51.5), (int)(height * 33.5)),
			new Point((int)(width * 51.5), (int)(height * 34.5)),
			new Point((int)(width * 51.5), (int)(height * 35.5)),
			new Point((int)(width * 52.5), (int)(height * 31.5)),
			                                                
			//O                                             
			new Point((int)(width * 54.5), (int)(height * 31.5)),
			new Point((int)(width * 54.5), (int)(height * 32.5)),
			new Point((int)(width * 54.5), (int)(height * 33.5)),
			new Point((int)(width * 54.5), (int)(height * 34.5)),
			new Point((int)(width * 54.5), (int)(height * 35.5)),
			new Point((int)(width * 55.5), (int)(height * 35.5)),
			new Point((int)(width * 55.5), (int)(height * 31.5)),
			new Point((int)(width * 56.5), (int)(height * 35.5)),
			new Point((int)(width * 56.5), (int)(height * 31.5)),
			new Point((int)(width * 57.5), (int)(height * 31.5)),
			new Point((int)(width * 57.5), (int)(height * 32.5)),
			new Point((int)(width * 57.5), (int)(height * 33.5)),
			new Point((int)(width * 57.5), (int)(height * 34.5)),
			new Point((int)(width * 57.5), (int)(height * 35.5)),
			                                                
			//X                                             
			new Point((int)(width * 60.5), (int)(height * 35.5)),
			new Point((int)(width * 60.5), (int)(height * 31.5)),
			new Point((int)(width * 61.5), (int)(height * 35.5)),
			new Point((int)(width * 61.5), (int)(height * 31.5)),
			new Point((int)(width * 61.5), (int)(height * 34.5)),
			new Point((int)(width * 61.5), (int)(height * 32.5)),
			new Point((int)(width * 62.5), (int)(height * 33.5)),
			new Point((int)(width * 63.5), (int)(height * 32.5)),
			new Point((int)(width * 63.5), (int)(height * 34.5)),
			new Point((int)(width * 63.5), (int)(height * 31.5)),
			new Point((int)(width * 63.5), (int)(height * 35.5)),
			new Point((int)(width * 64.5), (int)(height * 31.5)),
			new Point((int)(width * 64.5), (int)(height * 35.5)),
			                                                
			//I                                             
			new Point((int)(width * 66.5), (int)(height * 35.5)),
			new Point((int)(width * 66.5), (int)(height * 31.5)),
			new Point((int)(width * 69.5), (int)(height * 35.5)),
			new Point((int)(width * 69.5), (int)(height * 31.5)),
			new Point((int)(width * 68.5), (int)(height * 35.5)),
			new Point((int)(width * 68.5), (int)(height * 31.5)),
			new Point((int)(width * 68.5), (int)(height * 34.5)),
			new Point((int)(width * 68.5), (int)(height * 32.5)),
			new Point((int)(width * 68.5), (int)(height * 33.5)),
			new Point((int)(width * 67.5), (int)(height * 35.5)),
			new Point((int)(width * 67.5), (int)(height * 31.5)),
			new Point((int)(width * 67.5), (int)(height * 34.5)),
			new Point((int)(width * 67.5), (int)(height * 32.5)),
			new Point((int)(width * 67.5), (int)(height * 33.5)),
			                                                
			//C                                             
			new Point((int)(width * 71.5), (int)(height * 35.5)),
			new Point((int)(width * 71.5), (int)(height * 34.5)),
			new Point((int)(width * 71.5), (int)(height * 33.5)),
			new Point((int)(width * 71.5), (int)(height * 32.5)),
			new Point((int)(width * 71.5), (int)(height * 31.5)),
			new Point((int)(width * 72.5), (int)(height * 35.5)),
			new Point((int)(width * 73.5), (int)(height * 35.5)),
			new Point((int)(width * 74.5), (int)(height * 35.5)),
			new Point((int)(width * 72.5), (int)(height * 31.5)),
			new Point((int)(width * 73.5), (int)(height * 31.5)),
			new Point((int)(width * 74.5), (int)(height * 31.5)),
			                                                
			//O                                             
			new Point((int)(width * 76.5), (int)(height * 31.5)),
			new Point((int)(width * 76.5), (int)(height * 32.5)),
			new Point((int)(width * 76.5), (int)(height * 33.5)),
			new Point((int)(width * 76.5), (int)(height * 34.5)),
			new Point((int)(width * 76.5), (int)(height * 35.5)),
			new Point((int)(width * 77.5), (int)(height * 35.5)),
			new Point((int)(width * 77.5), (int)(height * 31.5)),
			new Point((int)(width * 78.5), (int)(height * 35.5)),
			new Point((int)(width * 78.5), (int)(height * 31.5)),
			new Point((int)(width * 79.5), (int)(height * 31.5)),
			new Point((int)(width * 79.5), (int)(height * 32.5)),
			new Point((int)(width * 79.5), (int)(height * 33.5)),
			new Point((int)(width * 79.5), (int)(height * 34.5)),
			new Point((int)(width * 79.5), (int)(height * 35.5)),
			

			new Point((int)(width * 18.5), (int)(height * 15.5)),
			new Point((int)(width * 18.5), (int)(height * 16.5)),
			new Point((int)(width * 18.5), (int)(height * 17.5)),
			new Point((int)(width * 37.5), (int)(height * 9.5)),
			new Point((int)(width * 38.5), (int)(height * 24.5)),
			new Point((int)(width * 38.5), (int)(height * 25.5)),
			new Point((int)(width * 38.5), (int)(height * 26.5)),
			new Point((int)(width * 38.5), (int)(height * 27.5)),
			new Point((int)(width * 38.5), (int)(height * 28.5)),
			new Point((int)(width * 38.5), (int)(height * 29.5)),
			new Point((int)(width * 39.5), (int)(height * 24.5)),
			new Point((int)(width * 39.5), (int)(height * 25.5)),
			new Point((int)(width * 39.5), (int)(height * 26.5)),
			new Point((int)(width * 39.5), (int)(height * 27.5)),
			new Point((int)(width * 39.5), (int)(height * 28.5)),
			new Point((int)(width * 39.5), (int)(height * 29.5)),
			new Point((int)(width * 45.5), (int)(height * 19.5)),
			new Point((int)(width * 46.5), (int)(height * 19.5)),
			new Point((int)(width * 47.5), (int)(height * 19.5)),
			new Point((int)(width * 48.5), (int)(height * 19.5)),
			new Point((int)(width * 45.5), (int)(height * 20.5)),
			new Point((int)(width * 46.5), (int)(height * 20.5)),
			new Point((int)(width * 47.5), (int)(height * 20.5)),
			new Point((int)(width * 48.5), (int)(height * 20.5)),
			new Point((int)(width * 49.5), (int)(height * 18.5)),
			new Point((int)(width * 50.5), (int)(height * 18.5)),
			new Point((int)(width * 55.5), (int)(height * 10.5)),
			new Point((int)(width * 56.5), (int)(height * 11.5)),
			new Point((int)(width * 57.5), (int)(height * 12.5)),
			new Point((int)(width * 58.5), (int)(height * 13.5)),
			new Point((int)(width * 59.5), (int)(height * 14.5)),
			new Point((int)(width * 73.5), (int)(height * 23.5)),
			new Point((int)(width * 78.5), (int)(height * 23.5)),
			new Point((int)(width * 83.5), (int)(height * 23.5)),
			new Point((int)(width * 92.5), (int)(height * 23.5)),
			new Point((int)(width * 95.5), (int)(height * 23.5)),
			new Point((int)(width * 96.5), (int)(height * 23.5)),
			new Point((int)(width * 97.5), (int)(height * 23.5)),
			new Point((int)(width * 98.5), (int)(height * 23.5)),
			new Point((int)(width * 99.5), (int)(height * 23.5)),
			new Point((int)(width * 92.5), (int)(height * 10.5)),
			new Point((int)(width * 91.5), (int)(height * 10.5)),
			new Point((int)(width * 92.5), (int)(height * 11.5)),
			new Point((int)(width * 93.5), (int)(height * 11.5)),
			new Point((int)(width * 94.5), (int)(height * 12.5)),
			new Point((int)(width * 93.5), (int)(height * 12.5)),
			new Point((int)(width * 94.5), (int)(height * 13.5)),
			new Point((int)(width * 95.5), (int)(height * 13.5)),
			new Point((int)(width * 85.5), (int)(height * 10.5)),
			new Point((int)(width * 85.5), (int)(height * 11.5)),
			new Point((int)(width * 85.5), (int)(height * 12.5)),
			new Point((int)(width * 85.5), (int)(height * 13.5)),
			new Point((int)(width * 85.5), (int)(height * 14.5)),
			new Point((int)(width * 86.5), (int)(height * 10.5)),
			new Point((int)(width * 86.5), (int)(height * 11.5)),
			new Point((int)(width * 86.5), (int)(height * 12.5)),
			new Point((int)(width * 86.5), (int)(height * 13.5)),
			new Point((int)(width * 86.5), (int)(height * 14.5)),
			new Point((int)(width * 86.5), (int)(height * 8.5)),
			new Point((int)(width * 86.5), (int)(height * 9.5)),
			new Point((int)(width * 19.5), (int)(height * 6.5)),
			new Point((int)(width * 21.5), (int)(height * 7.5)),
			new Point((int)(width * 22.5), (int)(height * 8.5)),
			new Point((int)(width * 32.5), (int)(height * 3.5)),
			new Point((int)(width * 33.5), (int)(height * 3.5)),
			new Point((int)(width * 33.5), (int)(height * 4.5)),
			new Point((int)(width * 34.5), (int)(height * 4.5)),
			new Point((int)(width * 34.5), (int)(height * 5.5)),
			new Point((int)(width * 35.5), (int)(height * 5.5)),
			new Point((int)(width * 35.5), (int)(height * 6.5)),
			new Point((int)(width * 36.5), (int)(height * 6.5)),
			new Point((int)(width * 68.5), (int)(height * 4.5)),
			new Point((int)(width * 69.5), (int)(height * 4.5)),
			new Point((int)(width * 70.5), (int)(height * 4.5)),
			new Point((int)(width * 71.5), (int)(height * 4.5)),
			new Point((int)(width * 72.5), (int)(height * 4.5)),
			new Point((int)(width * 68.5), (int)(height * 5.5)),
			new Point((int)(width * 69.5), (int)(height * 5.5)),
			new Point((int)(width * 70.5), (int)(height * 5.5)),
			new Point((int)(width * 71.5), (int)(height * 5.5)),
			new Point((int)(width * 72.5), (int)(height * 5.5)),
			new Point((int)(width * 68.5), (int)(height * 6.5)),
			new Point((int)(width * 69.5), (int)(height * 6.5)),
			new Point((int)(width * 70.5), (int)(height * 6.5)),
			new Point((int)(width * 71.5), (int)(height * 6.5)),
			new Point((int)(width * 72.5), (int)(height * 6.5)),
			new Point((int)(width * 90.5), (int)(height * 9.5)),
			new Point((int)(width * 91.5), (int)(height * 9.5)),
			new Point((int)(width * 85.5), (int)(height * 8.5)),
			new Point((int)(width * 85.5), (int)(height * 9.5)),
			new Point((int)(width * 100.5), (int)(height * 23.5)),
			new Point((int)(width * 101.5), (int)(height * 23.5)),
			new Point((int)(width * 102.5), (int)(height * 23.5)),
			new Point((int)(width * 106.5), (int)(height * 21.5)),
			new Point((int)(width * 106.5), (int)(height * 20.5)),
			new Point((int)(width * 106.5), (int)(height * 19.5)),
			new Point((int)(width * 106.5), (int)(height * 18.5)),
			new Point((int)(width * 106.5), (int)(height * 17.5)),
			new Point((int)(width * 106.5), (int)(height * 16.5)),
			new Point((int)(width * 106.5), (int)(height * 15.5))
			
		};
		
		for(int i = 0; i < points2.length; i++){
			p = new Portal(tileMap);
			p.setPosition(points2[i].x, points2[i].y);
			portal.add(p);
		}
		
		for(int i = 0; i < points.length; i++){
			c = new Coins(tileMap);
			c.setPosition(points[i].x, points[i].y);
			coins.add(c);
		}
		
		for(int i = 0; i < points3.length; i++){
			m = new Mushroom(tileMap);
			m.setPosition(points3[i].x, points3[i].y);
			mushrooms.add(m);
		}
	}
	
	public void init(){
		start();
		//tilemap e posiçao inicial
		tileMap = new TileMap((int)width);
		tileMap.loadTiles("/Tilesets/TileRelva3.png");
		tileMap.loadMap("/Maps/level2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		//background
		bg = new Background("/Backgrounds/BG.png", 0);
		
		//criaçao do player e posiçao
		player = new Player(tileMap, gsm.getMale());		
		player.setPosition(width * 20, height * 20);
		
		enemyAI = new EnemyAI(tileMap);
		
		//display de vida/tempo/pontos/poder de fogo
		hud = new HUD(player);
		gameoverstates = new PreGameOverState();
		paused = new Paused();
		
		//musica
		bgMusic = new AudioPlayer("/Music/Pino.mp3");
		bgMusic.play();
		
		//definir os adversarios itens e personagens e a sua localizaçao
		populateEnemies();
		explosions = new ArrayList<Explosion>();
		player.setLevel(2);
		populateItens();
		if(vez){
			points = gsm.getPoints();
			//player.setHealth(gsm.getHP());
			vez = false;
		}
	}

	public void update() {
		if(vez3){
			vez3 = false;
			
			//zombies
			zX[0] = 3;
			zX2[0] = 4;
			zXL[0] = 54;
			zXR[0] = 3;
			zXP[0] = 4;
			
			zX[1] = 3;
			zX2[1] = 3;
			zXL[1] = 74;
			zXR[1] = 3;
			zXP[1] = 12;
			
			zX[2] = 3;
			zX2[2] = 3;
			zXL[2] = 82;
			zXR[2] = 12;
			zXP[2] = 3;
			
			zX[3] = 2;
			zX2[3] = 3;
			zXL[3] = 80;
			zXR[3] = 2;
			zXP[3] = 3;
			
			zX[4] = 3;
			zX2[4] = 4;
			zXL[4] = 93;
			zXR[4] = 6;
			zXP[4] = 14;
			
			zX[5] = 3;
			zX2[5] = 4;
			zXL[5] = 97;
			zXR[5] = 10;
			zXP[5] = 9;
			
			zX[6] = 3;
			zX2[6] = 4;
			zXL[6] = 103;
			zXR[6] = 16;
			zXP[6] = 4;
			
			//Fly
			Fup[0] = 0;
			Fdown[0] = 1;
			Fleft[0] = 3;
			Fright[0] = 3;
			FAup[0] = 3;
			FAdown[0] = 4;
			FAleft[0] = 8;
			FAright[0] = 8;
			enemyX[0] = 23.5;
			enemyY[0] = 11;

			Fup[1] = 0;
			Fdown[1] = 1;
			Fleft[1] = 3;
			Fright[1] = 3;
			FAup[1] = 3;
			FAdown[1] = 7;
			FAleft[1] = 3;
			FAright[1] = 12;
			enemyX[1] = 15.5;
			enemyY[1] = 3;
			
			Fup[2] = 0;
			Fdown[2] = 1;
			Fleft[2] = 3;
			Fright[2] = 3;
			FAup[2] = 9;
			FAdown[2] = 4;
			FAleft[2] = 24;
			FAright[2] = 3;
			enemyX[2] = 92.5;
			enemyY[2] = 17;
			
			Fup[3] = 0;
			Fdown[3] = 1;
			Fleft[3] = 3;
			Fright[3] = 3;
			FAup[3] = 9;
			FAdown[3] = 4;
			FAleft[3] = 11;
			FAright[3] = 7;
			enemyX[3] = 79.5;
			enemyY[3] = 17;
			
		}
		
		//morreu
		//caso o jogador morra volta ao menu inicial
		if(player.getHealth() == 0 && player.getDY() == 0)
		{
			if(!morreu){
				interacting = true;
				sec = secondsPassed;
				player.setDead();
				morreu = true;
			}
			bgMusic.stop();
			if(secondsPassed > sec + 4){
				secondsPassed = 0;
				musica = false;
				morreu2 = true;
			}
		}
		//armadilhas e agua
		if(player.getx() >= 553 && player.getx() <= 631 && player.gety() > 820){
			player.setHealth(0);
		}
		
		if(player.getx() >= 937 && player.getx() <= 1015 && player.gety() > 657){
			player.setHealth(0);
		}
		
		if(player.getx() >= 1543 && player.getx() <= 1671 && player.gety() > 340 && 
		   player.gety() < 350){
			player.setHealth(0);
		}
		
		if(player.getx() >= 1673 && player.getx() <= 1736 && player.gety() > 370 &&
		   player.gety() < 380){
			player.setHealth(0);
		}
		
		if(player.getx() >= 1737 && player.getx() <= 1798 && player.gety() > 435 && 
		   player.gety() < 445){
			player.setHealth(0);
		}
		
		if(player.getx() >= 1801 && player.getx() <= 1863 && player.gety() > 500 && 
		   player.gety() < 510){
			player.setHealth(0);
		}
		
		if(player.getx() >= 1865 && player.getx() <= 1928 && player.gety() > 690 &&
		   player.gety() < 700){
			player.setHealth(0);
		}
		
		if(player.getx() >= 2217 && player.getx() <= 2263 && player.gety() > 880 &&
		   player.gety() < 890){
			player.setHealth(0);
		}
		
		if(player.getx() >= 2761 && player.getx() <= 3415 && player.gety() > 850 && 
		   player.gety() < 860){
			player.setHealth(0);
		}

		for(int i = 0; i < zombies.size(); i++){
			enemyAI.enemyAI2(zombies.get(i), zX[i], zX2[i], zXL[i], zXR[i], zXP[i], player.getx());
		}
		
		for(int i = 0; i < fly.size(); i++){
			enemyAI.enemyAI(fly.get(i), Fup[i], Fdown[i], Fleft[i], Fright[i], FAup[i], FAdown[i], FAleft[i], 
							FAright[i], enemyX[i], enemyY[i], player.getx(), player.gety());
		}
		
		//player.enemyAI2(enemies, slugger, enemy, zombieC);
		double[] trapI = new double[10];
		trapI[0] = 16;
		double[] trapY = new double[10];
		trapY[0] = 5.5;
		for(int i = 0; i < traps.size(); i++){
			enemyAI.trapAi(traps, trapY, trapI, player);
		}
		
		player.platformAI(platforms);
		player.platformAI2(platforms, 2);

		// update player
		if(!pause){
			player.update();
		}
		
		for(int i = 0; i < traps.size(); i++){
			Enemy t = traps.get(i);
			t.update();
		}
		
		//verifica se o adversario esta morto e caso esteja retira o do jogo
		//sluggers
		for(int i = 0; i < sluggers.size(); i++) {
			Enemy e = sluggers.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					e.update();
				}
			}
			
			if(e.isDead()) {
				sluggers.remove(i);
				i--;
				points += 30;
				player.setPoints(points);
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		//zombies
		for(int i = 0; i < zombies.size(); i++) {
			Enemy e = zombies.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					e.update();
				}
			}
			
			if(e.isDead()){
				for(int j = i; j < zombies.size(); j++){
					//System.out.println("entrou");
					zX[j] = zX[j + 1];
					zX2[j] = zX2[j + 1];
					zXL[j] = zXL[j + 1];
					zXR[j] = zXR[j + 1];
					zXP[j] = zXP[j + 1];
				}
				
				zombies.remove(i);
				i--;
				points += 30;
				player.setPoints(points);
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		//fly
		for(int i = 0; i < fly.size(); i++) {
			Enemy e = fly.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					e.update();
				}
			}
			
			if(e.isDead()) {
				
				for(int j = i; j < fly.size(); j++){
					Fup[j] = Fup[j + 1];
					Fdown[j] = Fdown[j + 1];
					Fleft[j] = Fleft[j + 1];
					Fright[j] = Fright[j + 1];
					FAup[j] = FAup[j + 1];
					FAdown[j] = FAdown[j + 1];
					FAleft[j] = Fleft[j + 1];
					FAright[j] = FAright[j + 1];
					enemyX[j] = enemyX[j + 1];
					enemyY[j] = enemyY[j + 1];
				}
				
				fly.remove(i);
				i--;
				points += 30;
				player.setPoints(points);
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		//update platforms
		for(int i = 0; i < platforms.size(); i++){
			Enemy p = platforms.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					p.update();
				}
			}
		}
		
		//caso a explosao tenha ocorrido uma vez e removida
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		//fazer com que o mapa se mova em funçao do jogador
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), 
							GamePanel.HEIGHT / 2 - player.gety());
		
		//define a posiçao inicial da imagem do background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//verifica se o iten foi colecionado
		player.checkPick(coins, 0);
		player.checkPick(portal, 1);
		player.checkPick(mushrooms, 2);
		
		for(int i = 0; i < portal.size(); i++){
			if(!pause){
				Itens p = portal.get(i);
				p.update();
			}
		}
		
		//verifica a necessidade de remover o item
		for(int i = 0; i < coins.size(); i++){
			if(player.getHealth() > 0){
				if(!pause){
					Itens in = coins.get(i);
					in.update();
					
					//caso o iten seja colecionavel o mesmo é retirado
					if(in.isPicked()){
						coins.remove(i);
						i--;
						points += 10;
						player.setPoints(points);
					}
				}
			}
		}
		
		for(int i = 0; i < mushrooms.size(); i++){
			if(player.getHealth() > 0){
				if(!pause){
					Itens in = mushrooms.get(i);
					in.update();
					
					//caso o iten seja colecionavel o mesmo é retirado
					if(in.isPicked()){
						mushrooms.remove(i);
						i--;
					}
				}
			}
		}
	}
	
	public void draw(Graphics2D g) {
		
		//desenha o background
		bg.draw(g);
		
		//desenha o tilemap
		tileMap.draw(g);
		
		//desenha os adversarios
		//zombies
		
		//verifica se o ataque acertou na personagem
		adversarioFlinch4 = player.checkAttack(zombies);
		
		if(adversarioFlinch4 != 50){
			flinch = true;
		}
		for(int i = 0; i < zombies.size(); i++) {
			//flinching
			if(i == adversarioFlinch4 && flinch){

				if(flinch2){
					flinch2 = false;
					flinchTimer = System.nanoTime();
				}
				
				if(System.nanoTime() < flinchTimer + 1000){
					long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 4 == 0) {
						zombies.get(adversarioFlinch4).draw(g);
					}
				}
				else{
					flinch2 = true;
				}
			}
			
			else{
				zombies.get(i).draw(g);
			}
		}


		adversarioFlinch = player.checkAttack(sluggers);
		if(adversarioFlinch != 50){
			flinch = true;
		}
		//sluggers
		for(int i = 0; i < sluggers.size(); i++) {
			//flinching
			if(i == adversarioFlinch && flinch){

				if(flinch2){
					flinch2 = false;
					flinchTimer = System.nanoTime();
				}
				
				if(System.nanoTime() < flinchTimer + 1000){
					long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 4 == 0) {
						sluggers.get(adversarioFlinch).draw(g);
					}
				}
				else{
					flinch2 = true;
				}
			}
			
			else{
				sluggers.get(i).draw(g);
			}
		}
		

		
		adversarioFlinch3 = player.checkAttack(fly);
		if(adversarioFlinch3 != 50){
			flinch = true;
		}
		
		//fly
		for(int i = 0; i < fly.size(); i++) {
			//flinching
			if(i == adversarioFlinch3 && flinch){

				if(flinch2){
					flinch2 = false;
					flinchTimer = System.nanoTime();
				}
				
				if(System.nanoTime() < flinchTimer + 1000){
					long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 4 == 0) {
						fly.get(adversarioFlinch3).draw(g);
					}
				}
				else{
					flinch2 = true;
				}
			}
			
			else{
				fly.get(i).draw(g);
			}
		}
		
		//desenha os itens
		//coins
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
		
		//mushroom
		for(int i = 0; i < mushrooms.size(); i++){
			mushrooms.get(i).draw(g);
		}
		
		//desenha o jogador
		player.draw(g);
		
		//desenha as plataformas
		for(int i = 0; i < platforms.size(); i++){
			platforms.get(i).draw(g);
		}
		
		for(int i = 0; i < traps.size(); i ++){
			traps.get(i).draw(g);
		}
			
		//desenha o portal
		for(int i = 0; i < portal.size(); i++){
			portal.get(i).draw(g);
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		//desenha o hud
		hud.draw(g);
		
		if(morreu2){
			if(first){
				sec = secondsPassed;
				first = false;
			}
			morreu3 = true;
			if(secondsPassed > sec + 2){
				gsm.setState(GameStateManager.GAMEOVERSTATE);
			}
		}
		
		//se estiver em pausa desenha o background de pausa
		if(pause){
			paused.draw(g);
		}

		if(morreu3){
			gameoverstates.draw(g);
		}
	}
	
	public void keyPressed(int k) {

		for(int i = 0; i < traps.size(); i++){
			if(player.getTrapA(i)){
				if(k == KeyEvent.VK_UP){
					player.setDy(-4.8);
				}
			}
		}
		
		if(player.getPlatform2()){
			if(k == KeyEvent.VK_UP){
				player.setPlatform1(true);
				player.setDy(-4.8);
			}
		}
		
		if(player.getPlatform7()){
			if(k == KeyEvent.VK_UP){
				player.setPlatform6(true);
				player.setDy(-4.8);
			}
		}
		
		if(player.getPlatform12()){
			if(k == KeyEvent.VK_UP){
				player.setPlatform11(true);
				player.setDy(-4.8);
			}
		}
		
		if(!pause && !interacting && player.getHealth() > 0){
			//o que acontece quando o jogador clica em cada uma das seguintes letras
			if(k == KeyEvent.VK_LEFT){
				player.setLeft(true);
			}
			
			if(k == KeyEvent.VK_RIGHT){
				player.setRight(true);
			}
			
			if(k == KeyEvent.VK_DOWN){
				player.setDown(true);
			}
			
			if(k == KeyEvent.VK_UP){
				player.setJumping(true);
			}

			if(k == KeyEvent.VK_E){
				player.setGliding(true);
			}
			
			if(k == KeyEvent.VK_R){
				player.setScratching();
			}
			
			if(k == KeyEvent.VK_F){
				player.setFiring();
			}
		}
		
		if(k == KeyEvent.VK_P){
			if(!pause){
				bgMusic.stop();
				player.setPause(true);
				pause = true;
			}
			
			else if(pause){
				bgMusic.play();
				pause = false;
				player.setPause(false);
			}
		}
	}
		
	public void keyReleased(int k) {
		//o que para de acontecer quando o jogador larga a tecla
		if(k == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		
		if(k == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		
		if(k == KeyEvent.VK_DOWN){
			player.setDown(false);
		}
		
		if(k == KeyEvent.VK_UP){
			player.setJumping(false);
		}
		
		if(k == KeyEvent.VK_E){
			player.setGliding(false);
		}
		
		if(player.getHealth() == 0){
			player.setGliding(false);
			player.setJumping(false);
			player.setDown(false);
			player.setLeft(false);
			player.setRight(false);
		}
	}
}