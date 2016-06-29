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
import java.util.Random;

public class Level3State extends GameState{
	
	//variaveis
	private boolean flinch2;
	private int adversarioFlinch;
	private int adversarioFlinch4;
	private int adversarioFlinch3;
	private int adversarioFlinch5;
	private int k;
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
	private boolean keyboolean = false;
	private int sec = 0;
	private int secondsPassed = 0;
	private int points;
	private double width = 32;
	private double height = 32;	
	private double[] trapY = new double [10];
	private double[] trapY2 = new double [10];
	private double[] trapI = new double [10];
	private double[] trapI2 = new double [10];
	private boolean vez2 = true;
	private boolean vez1 = true;
	Random rand = new Random();
	private int n = rand.nextInt(36);
	
	private boolean keySlugger;
	private boolean keyZombie;
	private boolean keyRedZombie;
	private boolean keyFly;
	
	//zombies
	private int[] zX = new int[15];
	private int[] zX2 = new int[15];
	private int[] zXL = new int[15];
	private int[] zXR = new int[15];
	private int[] zXP = new int[15];
	private int[] rz_X = new int[15];
	private int[] rz_X2 = new int[15];
	private int[] rz_XL = new int[15];
	private int[] rz_XR = new int[15];
	private int[] rz_XP = new int[15];
	
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
	private ArrayList<Enemy> traps2;
	private ArrayList<Enemy> redzombies;
	
	//timer
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			secondsPassed++;
			if(secondsPassed % 18 == 0 && musica){
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
	private Key key;
	private HUD hud;
	private PreGameOverState gameoverstates;
	private Paused paused;
	
	private AudioPlayer bgMusic;
	
	//construtor
	public Level3State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	//funçao que cria os enimigos
	private void populateEnemies() {
		sluggers = new ArrayList<Enemy>();
		platforms = new ArrayList<Enemy>();
		traps = new ArrayList<Enemy>();
		traps2 = new ArrayList<Enemy>();
		zombies = new ArrayList<Enemy>();
		fly = new ArrayList<Enemy>();
		redzombies = new ArrayList<Enemy>();
		
		Slugger sl;
		ShadowZombie sz;
		Fly s;
		Platform p;
		SpikeTecto st;
		SpikeTecto2 st2;
		RedZombie rz;
		
		//SLUGGER
		Point[] points1 = new Point[]{
			new Point((int)(width * 19.5), (int)(height * 61.7)),
			new Point((int)(width * 20.5), (int)(height * 61.7)),
			new Point((int)(width * 22.5), (int)(height * 61.7)),
			new Point((int)(width * 18.5), (int)(height * 47.7)),
			new Point((int)(width * 15.5), (int)(height * 47.7)),
			new Point((int)(width * 21.5), (int)(height * 45.7)),
			new Point((int)(width * 24.5), (int)(height * 45.7)),
			new Point((int)(width * 87.5), (int)(height * 70.7)),
			new Point((int)(width * 89.5), (int)(height * 50.7)),
			new Point((int)(width * 96.5), (int)(height * 50.7)),
			new Point((int)(width * 97.5), (int)(height * 50.7)),
			new Point((int)(width * 98.5), (int)(height * 50.7)),
			new Point((int)(width * 8.5), (int)(height * 56.7)),
			new Point((int)(width * 6.5), (int)(height * 56.7))			
		};
		
		for(int i = 0; i < points1.length; i++){
			if(i >= 12){
				sl = new Slugger(tileMap, true);
			}
			else{
				sl = new Slugger(tileMap, false);
			}
			sl.setPosition(points1[i].x, points1[i].y);
			sluggers.add(sl);
		}
		
		//SHADOWZOMBIE
		Point[] points2 = new Point[]{
			new Point((int)(width * 3.5), (int)(height * 63.5)),
			new Point((int)(width * 6.5), (int)(height * 63.5)),
			new Point((int)(width * 8.5), (int)(height * 63.5)),
			new Point((int)(width * 10.5), (int)(height * 63.5)),
			new Point((int)(width * 13.5), (int)(height * 52.5)),
			new Point((int)(width * 9.5), (int)(height * 52.5)),
			new Point((int)(width * 17.5), (int)(height * 47.5)),
			new Point((int)(width * 77.5), (int)(height * 70.5)),
			new Point((int)(width * 80.5), (int)(height * 70.5)),
			new Point((int)(width * 93.5), (int)(height * 70.5)),
			new Point((int)(width * 97.5), (int)(height * 70.5)),
			new Point((int)(width * 92.5), (int)(height * 57.5))		
		};
		
		for(int i = 0; i < points2.length; i++){
			sz = new ShadowZombie(tileMap);
			sz.setPosition(points2[i].x, points2[i].y);
			zombies.add(sz);
		}
		
		//FLY
		Point[] points3 = new Point[] {
				new Point((int)(width * 11.5), (int)(height * 43.5)),
				new Point((int)(width * 22.5), (int)(height * 40.5)),
				new Point((int)(width * 88.5), (int)(height * 59.5)),
				new Point((int)(width * 91.5), (int)(height * 41.5))
		};
		
		for(int i = 0; i < points3.length; i++) {
			s = new Fly(tileMap);
			s.setPosition(points3[i].x, points3[i].y);
			fly.add(s);
		}
		
		//PLATFORM
		Point[] points4 = new Point[]{
			new Point((int)(width * 58.5), (int)(height * 46))
		};
		
		for(int i = 0; i < points4.length; i++){
			p = new Platform(tileMap);
			p.setPosition(points4[i].x, points4[i].y);
			platforms.add(p);
		};
		
		//SPIKETECTO
		Point[] points5 = new Point[]{
			new Point((int)(width * 29), (int)(height * 66)),
			new Point((int)(width * 33), (int)(height * 66)),
			new Point((int)(width * 39), (int)(height * 66))
		};
		
		for(int i = 0; i < points5.length; i++){
			st = new SpikeTecto(tileMap);
			st.setPosition(points5[i].x, points5[i].y);
			traps.add(st);
		}
		//SPIKETECTO2
		Point[] points6 = new Point[]{
			new Point((int)(width * 43), (int)(height * 32)),
			new Point((int)(width * 45), (int)(height * 32))
		};
		
		for(int i = 0; i < points6.length; i++){
			st2 = new SpikeTecto2(tileMap);
			st2.setPosition(points6[i].x, points6[i].y);
			traps2.add(st2);
		}
		
		//REDZOMBIE
		Point[] points7 = new Point[]{
			new Point((int)(width * 36.5), (int)(height * 41.5)),
			new Point((int)(width * 41.5), (int)(height * 41.5)),
			new Point((int)(width * 69.5), (int)(height * 70.5)),	
			new Point((int)(width * 73.5), (int)(height * 70.5)),
			new Point((int)(width * 95.5), (int)(height * 70.5)),
			new Point((int)(width * 90.5), (int)(height * 50.5)),
			new Point((int)(width * 50.5), (int)(height * 35.5)),			
		};
		for(int i = 0; i < points7.length; i++){
			rz = new RedZombie(tileMap);
			rz.setPosition(points7[i].x, points7[i].y);
			redzombies.add(rz);
		}
	}
	
	//funçao que cria os itens
	private void populateItens(){
		coins = new ArrayList<Itens>();
		mushrooms = new ArrayList<Itens>();
		portal = new ArrayList<Itens>();
		key = new Key(tileMap);
		Coins c;
		Mushroom m;
		
		//Coins
		Point[] points = new Point[]{
			new Point((int)(width * 2.5), (int)(height * 63.5)),
			new Point((int)(width * 4.5), (int)(height * 60.5)),
			new Point((int)(width * 5.5), (int)(height * 60.5)),
			new Point((int)(width * 6.5), (int)(height * 60.5)),
			new Point((int)(width * 7.5), (int)(height * 63.5)),
			new Point((int)(width * 12.5), (int)(height * 63.5)),
			new Point((int)(width * 24.5), (int)(height * 61.5)),
			new Point((int)(width * 24.5), (int)(height * 60.5)),
			new Point((int)(width * 24.5), (int)(height * 55.5)),
			new Point((int)(width * 24.5), (int)(height * 54.5)),
			new Point((int)(width * 16.5), (int)(height * 56.5)),
			new Point((int)(width * 15.5), (int)(height * 55.5)),
			new Point((int)(width * 14.5), (int)(height * 56.5)),
			new Point((int)(width * 1.5), (int)(height * 45.5)),
			new Point((int)(width * 4.5), (int)(height * 46.5)),
			new Point((int)(width * 5.5), (int)(height * 46.5)),
			new Point((int)(width * 10.5), (int)(height * 46.5)),
			new Point((int)(width * 11.5), (int)(height * 46.5)),
			new Point((int)(width * 27.5), (int)(height * 39.5)),
			new Point((int)(width * 33.5), (int)(height * 39.5)),
			new Point((int)(width * 39.5), (int)(height * 39.5)),
			new Point((int)(width * 42.5), (int)(height * 45.5)),
			new Point((int)(width * 42.5), (int)(height * 46.5)),
			new Point((int)(width * 42.5), (int)(height * 47.5)),
			new Point((int)(width * 45.5), (int)(height * 52.5)),
			new Point((int)(width * 45.5), (int)(height * 54.5)),
			new Point((int)(width * 45.5), (int)(height * 53.5)),
			new Point((int)(width * 42.5), (int)(height * 57.5)),
			new Point((int)(width * 42.5), (int)(height * 58.5)),
			new Point((int)(width * 42.5), (int)(height * 59.5)),
			new Point((int)(width * 45.5), (int)(height * 65.5)),
			new Point((int)(width * 45.5), (int)(height * 66.5)),
			new Point((int)(width * 45.5), (int)(height * 67.5)),
			new Point((int)(width * 28.5), (int)(height * 67.5)),
			new Point((int)(width * 29.5), (int)(height * 67.5)),
			new Point((int)(width * 32.5), (int)(height * 67.5)),
			new Point((int)(width * 33.5), (int)(height * 67.5)),
			new Point((int)(width * 34.5), (int)(height * 68.5)),
			new Point((int)(width * 35.5), (int)(height * 68.5)),
			new Point((int)(width * 36.5), (int)(height * 68.5)),
			new Point((int)(width * 37.5), (int)(height * 68.5)),
			new Point((int)(width * 38.5), (int)(height * 67.5)),
			new Point((int)(width * 39.5), (int)(height * 67.5)),
			new Point((int)(width * 50.5), (int)(height * 68.5)),
			new Point((int)(width * 53.5), (int)(height * 68.5)),
			new Point((int)(width * 56.5), (int)(height * 68.5)),
			new Point((int)(width * 45.5), (int)(height * 33.5)),
			new Point((int)(width * 48.5), (int)(height * 35.5)),
			new Point((int)(width * 49.5), (int)(height * 35.5)),
			new Point((int)(width * 50.5), (int)(height * 35.5)),
			
			//50
			new Point((int)(width * 85.5), (int)(height * 66.5)),
			new Point((int)(width * 88.5), (int)(height * 67.5)),
			new Point((int)(width * 89.5), (int)(height * 69.5)),
			new Point((int)(width * 90.5), (int)(height * 65.5)),
			new Point((int)(width * 91.5), (int)(height * 69.5)),
			new Point((int)(width * 94.5), (int)(height * 66.5)),
			new Point((int)(width * 95.5), (int)(height * 67.5)),
			new Point((int)(width * 95.5), (int)(height * 69.5)),
			new Point((int)(width * 98.5), (int)(height * 69.5)),
			new Point((int)(width * 98.5), (int)(height * 68.5)),
			new Point((int)(width * 98.5), (int)(height * 66.5)),
			new Point((int)(width * 98.5), (int)(height * 55.5)),
			new Point((int)(width * 95.5), (int)(height * 55.5)),
			new Point((int)(width * 94.5), (int)(height * 61.5)),
			new Point((int)(width * 94.5), (int)(height * 60.5)),
			new Point((int)(width * 94.5), (int)(height * 59.5)),
			new Point((int)(width * 94.5), (int)(height * 58.5)),
			new Point((int)(width * 86.5), (int)(height * 48.5)),
			new Point((int)(width * 95.5), (int)(height * 46.5)),
			new Point((int)(width * 96.5), (int)(height * 50.5)),
			new Point((int)(width * 98.5), (int)(height * 50.5)),
			new Point((int)(width * 98.5), (int)(height * 49.5)),
			new Point((int)(width * 98.5), (int)(height * 48.5)),
			new Point((int)(width * 98.5), (int)(height * 42.5)),
			new Point((int)(width * 94.5), (int)(height * 42.5)),
			new Point((int)(width * 80.5), (int)(height * 42.5)),
			new Point((int)(width * 79.5), (int)(height * 42.5)),
			new Point((int)(width * 78.5), (int)(height * 42.5)),
			new Point((int)(width * 72.5), (int)(height * 44.5)),
			new Point((int)(width * 65.5), (int)(height * 43.5)),
			new Point((int)(width * 64.5), (int)(height * 43.5)),
			new Point((int)(width * 57.5), (int)(height * 41.5)),
			new Point((int)(width * 57.5), (int)(height * 40.5)),
			new Point((int)(width * 57.5), (int)(height * 39.5)),
			new Point((int)(width * 57.5), (int)(height * 38.5)),
			new Point((int)(width * 57.5), (int)(height * 37.5)),
			new Point((int)(width * 57.5), (int)(height * 36.5)),
			new Point((int)(width * 57.5), (int)(height * 34.5)),
			new Point((int)(width * 57.5), (int)(height * 33.5)),
			new Point((int)(width * 57.5), (int)(height * 32.5)),
			new Point((int)(width * 57.5), (int)(height * 31.5)),
			new Point((int)(width * 57.5), (int)(height * 30.5)),
			new Point((int)(width * 57.5), (int)(height * 29.5)),
			new Point((int)(width * 57.5), (int)(height * 26.5)),
			new Point((int)(width * 60.5), (int)(height * 32.5)),
			new Point((int)(width * 61.5), (int)(height * 31.5)),
			new Point((int)(width * 62.5), (int)(height * 30.5)),
			new Point((int)(width * 63.5), (int)(height * 29.5)),
			new Point((int)(width * 64.5), (int)(height * 27.5)),
			new Point((int)(width * 64.5), (int)(height * 26.5))
		};
		
		for(int i = 0; i < points.length; i++){
			c = new Coins(tileMap);
			c.setPosition(points[i].x, points[i].y);
			coins.add(c);
		}
		
		//Mushrooms
		Point[] points2 = new Point[]{
			new Point((int)(width * 2.5), (int)(height * 54.5)),
			new Point((int)(width * 7.5), (int)(height * 47.5)),
			new Point((int)(width * 23.5), (int)(height * 69.5)),
			new Point((int)(width * 60.5), (int)(height * 54.5)),
			new Point((int)(width * 97.5), (int)(height * 50.5)),
			new Point((int)(width * 65.5), (int)(height * 29.5)),
			new Point((int)(width * 66.5), (int)(height * 30.5)),
			new Point((int)(width * 67.5), (int)(height * 31.5)),
			new Point((int)(width * 68.5), (int)(height * 32.5))
		};
		
		for(int i = 0; i < points2.length; i++){
			m = new Mushroom(tileMap);
			m.setPosition(points2[i].x, points2[i].y);
			mushrooms.add(m);
		}
				
	}
	
	public void init(){
		start();
		//tilemap e posiçao inicial
		tileMap = new TileMap((int)width);
		tileMap.loadTiles("/Tilesets/TileDesert3.png");
		tileMap.loadMap("/Maps/level3.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		//background
		bg = new Background("/Backgrounds/BG_deserto.png", 0);
		
		//criaçao do player e posiçao
		player = new Player(tileMap, gsm.getMale());
		player.setPosition(width * 13, height * 55);
		
		enemyAI = new EnemyAI(tileMap);
		
		//display de vida/tempo/pontos/poder de fogo
		hud = new HUD(player);
		gameoverstates = new PreGameOverState();
		paused = new Paused();
		
		//musica
		bgMusic = new AudioPlayer("/Music/MadPlans.mp3");
		bgMusic.play();
		
		//definir os adversarios itens e personagens e a sua localizaçao
		populateEnemies();
		explosions = new ArrayList<Explosion>();
		player.setLevel(3);
		player.setKunai();
		populateItens();
		if(vez){
			points = gsm.getPoints();
			//TODO
			//player.setHealth(gsm.getHP());
			vez = false;
			System.out.println(n);
		}
		if(n < sluggers.size()){
			keySlugger = true;
		}
		else if(n < sluggers.size() + zombies.size()){
			keyZombie = true;
			n-=(sluggers.size()-1);
		}
		else if(n < sluggers.size() + zombies.size() + fly.size()){
			keyFly = true;
			n-=((sluggers.size()-1) + (zombies.size()-1));
		}
		else if(n < sluggers.size() + zombies.size() + fly.size() + redzombies.size()){
			keyRedZombie = true;
			n-=((sluggers.size()-1) + (zombies.size()-1) + (fly.size()-1));
		}
		
	}

	public void update() {
		
		//System.out.println((int)(player.getx() / width) + " " + (int)(player.gety() / height));
		//morreu
		//caso o jogador morra volta ao menu inicial
		if(player.getHealth() == 0 && player.getDY() == 0){
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
		
		//picos
		if(player.getx() <= 37.8 * width && player.getx() >= 34.1 * width && player.gety() > 69.5 * height){
			player.setHealth(0);
		}

		if(player.getx() >= 15 * width && player.getx() <= 18.8 * width && player.gety() > 68.9 * height){
			player.setHealth(0);
		}
		
		if(vez2){
			//Traps
			vez2 = false;
			trapY[0] = 4.5;
			trapY[1] = 4.5;
			trapY[2] = 4.5;
			trapY2[0] = 10;
			trapY2[1] = 10;
			trapI[0] = trapI[1] = trapI[2] = 66;
			trapI2[0] = 33;
			trapI2[1] = 33;
			
			//zombies
			zX[0] = 3;
			zX2[0] = 1;
			zXL[0] = 3;
			zXR[0] = 1;
			zXP[0] = 10;
			
			zX[1] = 3;
			zX2[1] = 3;
			zXL[1] = 6;
			zXR[1] = 4;
			zXP[1] = 7;
			
			zX[2] = 3;
			zX2[2] = 3;
			zXL[2] = 8;
			zXR[2] = 6;
			zXP[2] = 5;
			
			zX[3] = 3;
			zX2[3] = 2;
			zXL[3] = 10;
			zXR[3] = 10;
			zXP[3] = 1;
			
			zX[4] = 2;
			zX2[4] = 2;
			zXL[4] = 13;
			zXR[4] = 6;
			zXP[4] = 3;
			
			zX[5] = 2;
			zX2[5] = 2;
			zXL[5] = 9;
			zXR[5] = 2;
			zXP[5] = 7;
			
			zX[6] = 2;
			zX2[6] = 2;
			zXL[6] = 17;
			zXR[6] = 4;
			zXP[6] = 2;
			
			zX[7] = 3;
			zX2[7] = 3;
			zXL[7] = 77;
			zXR[7] = 4;
			zXP[7] = 8;
			
			zX[8] = 3;
			zX2[8] = 3;
			zXL[8] = 80;
			zXR[8] = 4;
			zXP[8] = 5;
			
			zX[9] = 2;
			zX2[9] = 3;
			zXL[9] = 93;
			zXR[9] = 2;
			zXP[9] = 6;
			
			zX[10] = 2;
			zX2[10] = 3;
			zXL[10] = 97;
			zXR[10] = 6;
			zXP[10] = 2;
			
			zX[11] = 1;
			zX2[11] = 2;
			zXL[11] = 92;
			zXR[11] = 1;
			zXP[11] = 2;
			
			//Fly
			Fup[0] = 0;
			Fdown[0] = 1;
			Fleft[0] = 3;
			Fright[0] = 3;
			FAup[0] = 2;
			FAdown[0] = 4;
			FAleft[0] = 5;
			FAright[0] = 9;
			enemyX[0] = 11.5;
			enemyY[0] = 43;

			Fup[1] = 0;
			Fdown[1] = 1;
			Fleft[1] = 3;
			Fright[1] = 3;
			FAup[1] = 1;
			FAdown[1] = 5;
			FAleft[1] = 8;
			FAright[1] = 6;
			enemyX[1] = 22.5;
			enemyY[1] = 40;
			
			
			Fup[2] = 0;
			Fdown[2] = 1;
			Fleft[2] = 3;
			Fright[2] = 3;
			FAup[2] = 6;
			FAdown[2] = 4;
			FAleft[2] = 8;
			FAright[2] = 11;
			enemyX[2] = 88.5;
			enemyY[2] = 59;
			
			Fup[3] = 0;
			Fdown[3] = 1;
			Fleft[3] = 3;
			Fright[3] = 3;
			FAup[3] = 1;
			FAdown[3] = 3;
			FAleft[3] = 10;
			FAright[3] = 9;
			enemyX[3] = 91.5;
			enemyY[3] = 41;
			
			//Red zombies
			rz_X[0] = 3;
			rz_X2[0] = 3;
			rz_XL[0] = 36;
			rz_XR[0] = 11;
			rz_XP[0] = 6;
			
			rz_X[1] = 3;
			rz_X2[1] = 0;
			rz_XL[1] = 41;
			rz_XR[1] = 16;
			rz_XP[1] = 1;
			
			rz_X[2] = 4;
			rz_X2[2] = 4;
			rz_XL[2] = 69;
			rz_XR[2] = 15;
			rz_XP[2] = 16;
			
			rz_X[3] = 4;
			rz_X2[3] = 4;
			rz_XL[3] = 73;
			rz_XR[3] = 18;
			rz_XP[3] = 12;
			
			rz_X[4] = 4;
			rz_X2[4] = 4;
			rz_XL[4] = 95;
			rz_XR[4] = 4;
			rz_XP[4] = 5;
			
			rz_X[5] = 4;
			rz_X2[5] = 4;
			rz_XL[5] = 90;
			rz_XR[5] = 4;
			rz_XP[5] = 5;
			
			rz_X[6] = 4;
			rz_X2[6] = 4;
			rz_XL[6] = 50;
			rz_XR[6] = 4;
			rz_XP[6] = 7;
		}
		
		for(int i = 0; i < zombies.size(); i++){
			enemyAI.enemyAI2(zombies.get(i), zX[i], zX2[i], zXL[i], zXR[i], zXP[i], player.getx());
		}
		
		for(int i = 0; i < fly.size(); i++){
			enemyAI.enemyAI(fly.get(i), Fup[i], Fdown[i], Fleft[i], Fright[i], FAup[i], FAdown[i], FAleft[i], 
							FAright[i], enemyX[i], enemyY[i], player.getx(), player.gety());
		}
		
		enemyAI.trapAi(traps, trapY, trapI, player);
		enemyAI.trapAi2(traps2, trapY2, trapI2, player);
		
		for(int i = 0; i < platforms.size(); i++){
			player.platformAI2(platforms, 3);
		}
		
		for(int i = 0; i < redzombies.size(); i++){
			enemyAI.enemyAI2(redzombies.get(i), rz_X[i], rz_X2[i], rz_XL[i], rz_XR[i], rz_XP[i], player.getx());
		}
		
		//verifica se o iten foi colecionado
		player.checkPick(coins, 0);
		//verifica a necessidade de remover o item
		for(int i = 0; i < coins.size(); i++){
			if(player.getHealth() > 0 && !pause){
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
			
		player.checkPick(mushrooms, 2);
		for(int i= 0; i < mushrooms.size(); i++){
			if(player.getHealth() > 0 && !pause){
				Itens in = mushrooms.get(i);
				in.update();
				//caso o iten seja colecionavel o mesmo é retirado
				if(in.isPicked()){
					mushrooms.remove(i);
					i--;
				}
			}
		}
		
		
		//TODO
		//System.out.println(player.getx() + " " + player.gety());
		
		//TODO
		for(int i = 0; i < traps.size(); i++){
			Enemy t= traps.get(i);
			t.update();
		}
		
		//platform update
		for(int i = 0; i < platforms.size(); i++){
			Enemy p = platforms.get(i);
			p.update();
		}

		// update player
		if(!pause){
			player.update();
		}
		
		// update key
		key.update();
		
		//verifica se o adversario esta morto e caso esteja retira o do jogo
		for(int i = 0; i < sluggers.size(); i++) {
			Enemy e = sluggers.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					e.update();
				}
			}
			
			if(e.isDead()) {
				if(i<n){
					n--;
				}
				else if(i==n){
					keyboolean = true;
					if(vez1){
						vez1=false;
						k = n;

						key.setPosition((int)(sluggers.get(k).getx()),(int)(sluggers.get(k).gety()));
					}
				}
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
				if(i<n){
					n--;
				}
				else if(i==n){
					keyboolean = true;
					if(vez1){
						vez1=false;
						k=n;

						key.setPosition((int)(zombies.get(k).getx()),(int)(zombies.get(k).gety()));
					}
				}
				
				//zombies.size() - 1
				for(int j = i; j < zombies.size(); j++){
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
				
				if(i<n){
					n--;
				}
				else if(i==n){
					keyboolean = true;
					if(vez1){
						vez1=false;
						k=n;

						key.setPosition((int)(fly.get(k).getx()),(int)(fly.get(k).gety()));
					}
				}
				
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
		
		//redzombies
		for(int i = 0; i < redzombies.size(); i++) {
			Enemy e = redzombies.get(i);
			if(!pause){
				if(player.getHealth() > 0){
					e.update();
				}
			}
			
			if(e.isDead()){
			if(i<n){
				n--;
			}
			else if(i==n){
				keyboolean = true;
				if(vez1){
					vez1=false;
					k=n;

					key.setPosition((int)(redzombies.get(k).getx()),(int)(redzombies.get(k).gety()));
				}
			}
				
			//redzombies.size() - 1
			for(int j = i; j < redzombies.size(); j++){
				rz_X[j] = rz_X[j + 1];
				rz_X2[j] = rz_X2[j + 1];
				rz_XL[j] = rz_XL[j + 1];
				rz_XR[j] = rz_XR[j + 1];
				rz_XP[j] = rz_XP[j + 1];
			}
			
			redzombies.remove(i);
			i--;
			points += 30;
			player.setPoints(points);
			explosions.add(new Explosion(e.getx(), e.gety()));
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
	}
	
	public void draw(Graphics2D g) {
		
		//desenha o background
		bg.draw(g);
		
		//desenha o tilemap
		tileMap.draw(g);
		
		adversarioFlinch = player.checkAttack(sluggers);
		if(adversarioFlinch != 50){
			flinch = true;
		}

		if(keyboolean && !key.isPicked()){			
			key.draw(g);
		}
		//System.out.println(key.isPicked());
		if(player.intersects(key)){
			key.picked();
			player.setKey(true);
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
		if(keyboolean){System.out.print(key.getx() + " " + key.gety() );
		}
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
		
		//redzombies
		
		//verifica se o ataque acertou na personagem
		adversarioFlinch5 = player.checkAttack(redzombies);
		
		if(adversarioFlinch5 != 50){
			flinch = true;
		}
		for(int i = 0; i < redzombies.size(); i++) {
			//flinching
			if(i == adversarioFlinch5 && flinch){

				if(flinch2){
					flinch2 = false;
					flinchTimer = System.nanoTime();
				}
				
				if(System.nanoTime() < flinchTimer + 1000){
					long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 4 == 0) {
						redzombies.get(adversarioFlinch5).draw(g);
					}
				}
				else{
					flinch2 = true;
				}
			}
			
			else{
				redzombies.get(i).draw(g);
			}
		}	
		
		//desenha as traps
		for(int i = 0; i < traps.size(); i++){
			traps.get(i).draw(g);
		}
		
		for(int i = 0; i < traps2.size(); i++){
			traps2.get(i).draw(g);
		}
		
		//desenha os itens
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
		
		//desenha os itens
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
		if(player.getHealth() != 0){
			for(int i = 0; i < traps.size(); i++){
				if(player.getTrapA(i)){
					if(k == KeyEvent.VK_UP){
						player.setDy(-4.8);
					}
				}
			}
			
			if(player.getPlatform7()){
				if(k == KeyEvent.VK_UP){
					player.setPlatform6(true);
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