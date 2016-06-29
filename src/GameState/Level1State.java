//package
package GameState;

//imports
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Character;
import Entity.Enemies.*;
import EntityCharacter.Sara;
import EntityCharacter.Conversation;
import EntityCharacter.Rodrigo;
import EntityItens.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

public class Level1State extends GameState{
	
	//variaveis
	private boolean vez6 = true;
	private int trunkT = 0;
	private boolean trunk = true;
	private boolean flinch2;
	private boolean level2 = false;
	private boolean pause = false;
	private boolean levelStateChange = true;
	private boolean morreu2 = false;
	private boolean morreu = false;
	private boolean musica = true;
	private boolean vez = true;
	private boolean caracol = false;
	private boolean netshots = false;
	private boolean interacting;
	private boolean gliding = false;
	private boolean scratching = false;
	private boolean firing = false;
	private double NSx;
	private double NSy;
	private int secondsPassed = 0;
	private int points = 0;
	private int netShotDamage = 2;
	private int SaraTalk = 0;
	private int RodrigoTalk = 0;
	private int Rodrigo2Talk = 0;
	private int sec = 0;
	private double width = 32;
	private double height = 32;
	private int adversarioFlinch;
	private boolean flinch = false;
	private long flinchTimer;
	private boolean walking;
	private boolean vez2 = true;
	private boolean vez3 = true;
	private boolean vez4 = true;
	private boolean vez5 = true;
	private boolean rodrigoC = true;
	private boolean specTrunk1 = false, specTrunk2 = false;
	private boolean mudar = false;

	//timer
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			//System.out.println(secondsPassed + " " + musica);
			secondsPassed++;
			if(secondsPassed % 23 == 0 && musica){
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
	private int mushroom = 2;
	private TileMap tileMap;
	private Background bg;
	private Player player;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Itens> coins;
	private ArrayList<Itens> mushrooms;
	private ArrayList<Itens> specItens; 
	private ArrayList<Character> characters;
	private ArrayList<NetShot> netShots;
	
	private HUD hud;
	private Conversation conversation;
	private PreGameOverState gameoverstates;
	private Paused paused;
	
	private AudioPlayer bgMusic;
	private AudioPlayer rodrigoTalk;
	private AudioPlayer rodrigoTalk2;
	private AudioPlayer rodrigoTalk3;
	private AudioPlayer rodrigoTalk4;
	private AudioPlayer rodrigoTalk5;
	private AudioPlayer rodrigo2Talk1;
	private AudioPlayer rodrigo2Talk2;
	private AudioPlayer rodrigo2Talk3;
	private AudioPlayer saraTalk1;
	private AudioPlayer saraTalk2;
	private AudioPlayer saraTalk3;
	private AudioPlayer saraTalk4;
	private AudioPlayer saraTalk5;
	private AudioPlayer saraTalk6;
	
	//construtor
	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init(){
		start();
		//tilemap e posiçao inicial
		tileMap = new TileMap((int)width);
		tileMap.loadTiles("/Tilesets/TileRelva3.png");
		tileMap.loadMap("/Maps/level1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		//falas
		
		//background
		bg = new Background("/Backgrounds/BG.png", 0.005);
		
		//criaçao do player e posiçao
		player = new Player(tileMap, gsm.getMale());
		player.setPosition(width * 3.3, height * 3.3);
		
		//definir os adversarios itens e personagens e a sua localizaçao
		populateEnemies();
		populateItens();
		populateCharacters();
		
		//criar uma array de explosoes e ataque ranged do adversario
		explosions = new ArrayList<Explosion>();
		netShots = new ArrayList<NetShot>();
		
		//display de vida/tempo/pontos/poder de fogo
		conversation = new Conversation(player);
		hud = new HUD(player);
		gameoverstates = new PreGameOverState();
		paused = new Paused();
		
		//musica e falas dos jogadores
		bgMusic = new AudioPlayer("/Music/CourseOfNature.mp3");
		rodrigoTalk = new AudioPlayer("/Conversations/rodrigo1.mp3");
		rodrigoTalk2 = new AudioPlayer("/Conversations/rodrigo2.mp3");
		rodrigoTalk3 = new AudioPlayer("/Conversations/rodrigo3.mp3");
		rodrigoTalk4 = new AudioPlayer("/Conversations/rodrigo4.mp3");
		rodrigoTalk5 = new AudioPlayer("/Conversations/rodrigo5.mp3");
		
		rodrigo2Talk1 = new AudioPlayer("/Conversations/2rodrigo1.mp3");
		rodrigo2Talk2 = new AudioPlayer("/Conversations/2rodrigo2.mp3");
		rodrigo2Talk3 = new AudioPlayer("/Conversations/2rodrigo3.mp3");
		
		saraTalk1 = new AudioPlayer("/Conversations/sara1.mp3");
		saraTalk2 = new AudioPlayer("/Conversations/sara2.mp3");
		saraTalk3 = new AudioPlayer("/Conversations/sara3.mp3");
		saraTalk4 = new AudioPlayer("/Conversations/sara4.mp3");
		saraTalk5 = new AudioPlayer("/Conversations/sara5.mp3");
		saraTalk6 = new AudioPlayer("/Conversations/sara6.mp3");
		
		player.setMushroom(mushroom);
		player.setLevel(1);
		player.setPoints(points);
		
		bgMusic.play();
	}
	
	//funçao que cria os adversarios
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Arachnik a;
		
		Point[] points = new Point[] {
			new Point((int)(width * 28.3), (int)(height * 10.7))
		};
		
		Point[] points2 = new Point[] {
				new Point((int)(width * 42), (int)(height * 4))
		};
		
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap, true);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
		for(int i = 0; i < points2.length; i++){
			a = new Arachnik(tileMap);
			a.setPosition(points2[i].x, points2[i].y);
			enemies.add(a);
		}
	}
	
	//funçao que cria as personagens
	private void populateCharacters(){
		characters = new ArrayList<Character>();
		
		Rodrigo s;
		Sara sa;
		
		Point[] points = new Point[]{
			new Point((int)(width * 13.33), (int)(height * 10.57)),
			new Point((int)(width * 34.7), (int)(height * 10.57))
		};
		
		for(int i = 0; i < points.length; i++){
			s = new Rodrigo(tileMap);
			s.setPosition(points[i].x, points[i].y);
			characters.add(s);
		}
		
		sa = new Sara(tileMap);
		sa.setPosition((int)(width * 21.9), (int)(height * 10.57));
		characters.add(sa);
	}

	//funçao que cria os itens
	private void populateItens(){
		coins = new ArrayList<Itens>();
		mushrooms = new ArrayList<Itens>();
		specItens = new ArrayList<Itens>();
		
		Coins c;
		Point[] points = new Point[]{
			new Point((int)(width * 6.6), (int)(height * 2.6)),
			new Point((int)(width * 5), (int)(height * 8.3))
		};
		
		for(int i = 0; i < points.length; i++){
			c = new Coins(tileMap);
			c.setPosition(points[i].x, points[i].y);
			coins.add(c);
		}
		
		Mushroom m;
		Point[] points2 = new Point[]{
				new Point((int)(width * 39.5), (int)(height * 8.5)),
				new Point((int)(width * 41.5), (int)(height * 6.5))
		};
		
		for(int i = 0; i < points2.length; i++){
			m = new Mushroom(tileMap);
			m.setPosition(points2[i].x, points2[i].y);
			mushrooms.add(m);
		}
		
		TrunkA t;
		Point[] points3 = new Point[]{
				new Point((int)(width * 12.5), (int)(height * 10.6)),
				new Point((int)(width * 21.9), (int)(height * 10.57)),
				new Point((int)(width * 12.5), (int)(height * 10.6)),
				new Point((int)(width * 21.9), (int)(height * 10.57))
		};
		
		for(int i = 0; i < points3.length; i++){
			t = new TrunkA(tileMap);
			t.setPosition(points3[i].x, points3[i].y);
			specItens.add(t);
		}
	}

	//Verificar se o netShot acertou no jogador
	private void checkAttackPlayer(){
		NSx -= 3.8;
		
		if(NSx > player.getx() - 10 && NSx < player.getx() + 10 && NSy > player.gety() - 13 && NSy < player.gety() + 13){
			
			netShots.remove(0);
			player.hit(netShotDamage);
		}
	}
	
	public void update() {
		if(!player.getWalking()){
			walking = false;
		}
		
		//morreu
		//caso o jogador morra volta ao menu inicial
		if(player.getHealth() == 0){
			if(vez6){
				vez6 = false;
				player.setTime(secondsPassed);
				System.out.println("cheguei");
				//player.SQLite();
			}
			
			if(!morreu && player.getDY() == 0){
				RodrigoTalk = 10;
				sec = secondsPassed;
				player.setDead();
				morreu = true;
				bgMusic.stop();
			}
			
			if(secondsPassed > sec + 4 && player.getDY() == 0){
				morreu2 = true;
			}
		}

		if((player.gety() > 370 && player.getx() > 520) || player.gety() > 438){
			if(!morreu){
				RodrigoTalk = 10;
				sec = secondsPassed;
				player.setDead();
				morreu = true;
				player.setHealth(0);
			}
			
			bgMusic.stop();
			if(secondsPassed > sec + 4){
				morreu2 = true;
			}
		}
		
		// update player
		if(!pause){
			player.update();
		}
		
		//fazer com que o mapa se mova em funçao do jogador
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), 
							GamePanel.HEIGHT / 2 - player.gety());
		
		//define a posiçao inicial da imagem do background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//verifica se o ataque acertou na personagem
		adversarioFlinch = player.checkAttack(enemies);
		
		if(adversarioFlinch != 50){
			flinch = true;
		}
		
		//verifica se o ataque acertou no jogador
		checkAttackPlayer();
		
		//verifica se o iten foi colecionado
		if(player.getHealth() > 0){
			player.checkPick(mushrooms, 2);
			player.checkPick(coins, 0);
		}
			
		//verifica se o jogador esta a interagir com a personagem
		//player.checkInteract(characters);
		
		//verifica se o adversario esta morto e caso esteja retira o do jogo
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if(!pause){
				e.update();
			}
			
			if(e.isDead()) {
				enemies.remove(i);
				
				//variavel que verifica se a aranha esta morta
				if(enemies.size() == 0){
					//caso morra paramos de desenhar os netshots
					netshots = true;
				}
				
				//verifica se o caracol esta morto
				if(!caracol){
					//caso esteja a sara ensina ao jogador um ataque ranged
					caracol = true;
					vez2 = true;
					if((SaraTalk == 3 && Rodrigo2Talk == 4) || (SaraTalk == 3 && Rodrigo2Talk == 6) || 
					   (SaraTalk == 5 && Rodrigo2Talk == 4) || (SaraTalk == 5 && Rodrigo2Talk == 6)){
						if(mudar){
							SaraTalk = 12;
						}
						else{
							SaraTalk = 8;
						}
					}
					else if(SaraTalk < 8){
						SaraTalk = 8;
					}
				}
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
				points += 30;
				player.setPoints(points);
			}
		}
		
		//verifica a necessidade de remover o item
		if(player.getHealth() > 0){
			if(!pause){
				for(int i = 0; i < coins.size(); i++){
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
		
		//verifica a necessidade de remover o item
		if(player.getHealth() > 0){
			if(!pause){
				for(int i = 0; i < mushrooms.size(); i++){
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
		
		if(trunk && gliding){
			trunk = false;
			trunkT = secondsPassed;
		}

		if(trunkT != 0 && secondsPassed < trunkT + 1){
			specItens.get(0).update();
		}
		else{
			if(trunkT != 0){
				specTrunk1 = true;
				trunkT = 0;
			}
		}
		
		if(vez5 && SaraTalk == 18){
			vez3 = true;
			vez5 = false;
			sec = secondsPassed;
			Rodrigo2Talk = 9;
		}
		
		if(SaraTalk == 18 && secondsPassed < sec + 1){
			specItens.get(1).update();
		}
		else{
			if(SaraTalk == 18 && secondsPassed > sec + 1){
				specTrunk2 = true;
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

		//a cada 2 segundos faz com que a aranha dispare um netshot
		if(!pause){
			if(netShots.size() < 1 && secondsPassed % 2 == 0 && !netshots){
				NetShot ns = new NetShot(tileMap, false);
				ns.setPosition(enemies.get(enemies.size() - 1).getx() , enemies.get(enemies.size() - 1).gety());
				NSx = enemies.get(enemies.size() - 1).getx();
				NSy = enemies.get(enemies.size() - 1).gety();
				netShots.add(ns);
			}
		}
		
		//Caso o netShot tenha colidido o mesmo é removido
		for(int i = 0; i < netShots.size(); i++) {
			if(!pause && player.getHealth() > 0){
				netShots.get(i).update();
				if(netShots.get(i).shouldRemove()) {
					netShots.remove(i);
					i--;
				}
			}
		}
		if(enemies.size() == 0){
			if(vez4){
				vez4 = false;
				SaraTalk = 16;
			}
		}
	}
	
	public void finalizaConversa(){
		interacting = false;
		player.setInteracting(false);
	}
	
	public void draw(Graphics2D g) {
		
		//desenha o background
		bg.draw(g);
		
		//desenha o tilemap
		tileMap.draw(g);
		
		//desenha os adversarios
		for(int i = 0; i < enemies.size(); i++) {
			//flinching
			if(i == adversarioFlinch && flinch){
				if(flinch2){
					flinch2 = false;
					flinchTimer = System.nanoTime();
				}
				
				if(System.nanoTime() < flinchTimer + 1000){
					long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 4 == 0) {
						enemies.get(adversarioFlinch).draw(g);
					}
				}
				else{
					flinch2 = true;
				}
			}
			
			else{
				enemies.get(i).draw(g);
			}
		}
		
		//desenha os itens
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
		
		//desenha os mushrooms
		for(int i = 0; i < mushrooms.size(); i++){
			mushrooms.get(i).draw(g);
		}
		
		//desenha o tronco
		if(gliding && !specTrunk1){
			specItens.get(0).draw(g);
			
		}
		
		
		if(specTrunk1){
			specItens.get(2).draw(g);
			if(player.intersects(specItens.get(0))){
				if(player.getx() > specItens.get(0).getx() - 17 && 
				   player.gety() > specItens.get(0).gety() - 9 && 
				   player.getx() < specItens.get(0).getx() - 13){
					player.setX(specItens.get(0).getx() - 17);
				}
				if(player.getx() > specItens.get(0).getx() - 17 && 
				   player.getx() < specItens.get(0).getx() + 17 &&
				   player.gety() > specItens.get(0).gety() - 19 && 
				   player.gety() < specItens.get(0).gety() - 14){
					player.setY(specItens.get(0).gety() - 19);
				}
				if(player.getx() < specItens.get(0).getx() + 17 && 
				   player.gety() > specItens.get(0).gety() - 9 && 
				   player.getx() > specItens.get(0).getx() + 11){
					player.setX(specItens.get(0).getx() + 17);
				}
			}
		}
		
		if(SaraTalk == 18 && !specTrunk2){
			specItens.get(1).draw(g);
		}
		if(specTrunk2){
			specItens.get(3).draw(g);
			if(player.intersects(specItens.get(1))){
				if(player.getx() > specItens.get(1).getx() - 17 && 
				   player.gety() > specItens.get(1).gety() - 9 && 
				   player.getx() < specItens.get(1).getx() - 13){
					player.setX(specItens.get(1).getx() - 17);
				}
				if(player.getx() > specItens.get(1).getx() - 17 && 
				   player.getx() < specItens.get(1).getx() + 17 &&
				   player.gety() > specItens.get(1).gety() - 19 && 
				   player.gety() < specItens.get(1).gety() - 14){
					player.setY(specItens.get(1).gety() - 19);
				}
				if(player.getx() < specItens.get(1).getx() + 17 && 
				   player.gety() > specItens.get(1).gety() - 9 && 
				   player.getx() > specItens.get(1).getx() + 11){
					player.setX(specItens.get(1).getx() + 17);
				}
			}
		}
		
		//desenha as explosoes
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		//desenha as personagens
		characters.get(0).draw(g);
		
		if(Rodrigo2Talk != 8){
			characters.get(1).draw(g);
		}
		
		if(SaraTalk != 18)
			characters.get(2).draw(g);

		//desenha os netshots
		for(int i = 0; i < netShots.size(); i++){
			netShots.get(i).draw(g);
		}
		
		/**********************************************************************************/
		/*falas*/
		//desenha a 1º conversa com o Rodrigo
		if(RodrigoTalk == 1){
			if(vez){
				rodrigoTalk.play();
				vez = false;
			}
		}
		if(RodrigoTalk == 2){
			if(!vez){
				rodrigoTalk2.play();
				vez = true;
			}
		}
		if(RodrigoTalk == 3){
			if(vez){
				rodrigoTalk3.play();
				vez = false;
			}
		}
		if(RodrigoTalk == 4){
			if(!vez){
				rodrigoTalk4.play();
				vez = true;
				sec = secondsPassed;
			}
			if(secondsPassed >= sec + 2){
				if(vez){
					gliding = true;
					player.setPosition(width * 14, height * 10);
					rodrigoTalk4.stop();
					rodrigoTalk5.play();
					vez = false;
					RodrigoTalk++;
					sec = secondsPassed;
				}
			}
		}
		if(RodrigoTalk == 5){
			if(secondsPassed >= sec + 5){
				if(!vez){
					RodrigoTalk++;
					vez = true;
				}
			}
		}
		
		if(SaraTalk == 1){
			if(vez2){
				saraTalk1.play();
				vez2 = false;
			}
		}
		if(SaraTalk == 2){
			if(!vez2){
				saraTalk2.play();
				vez2 = true;
			}
		}
		
		if(SaraTalk == 4){
			if(vez2){
				saraTalk3.play();
				vez2 = false;
			}
		}
		
		if(SaraTalk == 6){
			if(!vez2){
				saraTalk3.play();
				vez2 = true;
			}
		}
		
		if(SaraTalk == 9){
			if(vez2){
				vez2 = false;
				saraTalk4.play();
			}
		}
		
		if(SaraTalk == 11){
			if(!vez2){
				vez2 = true;
				saraTalk4.play();
			}
		}
		
		if(SaraTalk == 13){
			if(vez2){
				vez2 = false;
				saraTalk5.play();
				firing = true;
				Rodrigo2Talk = 8;
			}
		}
		
		if(SaraTalk == 15){
			if(!vez2){
				vez2 = true;
				saraTalk5.play();
			}
		}
		
		if(SaraTalk == 17){
			if(vez2){
				vez2 = false;
				saraTalk6.play();
			}
		}
		
		if(Rodrigo2Talk == 1){
			if(vez3){
				vez3 = false;
				rodrigo2Talk1.play();
			}
		}
		
		if(Rodrigo2Talk == 3){
			if(!vez3){
				vez3 = true;
				rodrigo2Talk1.play();
			}
		}
		
		if(Rodrigo2Talk == 5){
			if(vez3){
				mudar = true;
				vez3 = false;
				rodrigo2Talk2.play();
			}
		}
		
		if(Rodrigo2Talk == 7){
			if(!vez3){
				vez3 = true;
				rodrigo2Talk2.play();
			}
		}
		
		if(Rodrigo2Talk == 10){
			if(vez3){
				vez3 = false;
				rodrigo2Talk3.play();
			}
		}
		
		//conversation (hud)
		conversation.draw(g);
		
		//desenha o hud
		hud.draw(g);
		
		/*Fim de falas*/
		/************************************************************************************/

		//nao deixa o jogador avançar enquanto nao aprender um ataque ranged
		if(Rodrigo2Talk < 8){
			if(player.getx() > (int)(width * 34.3)){
				player.setX((int)(width * 34.3));
			}
		}
		
		if(Rodrigo2Talk == 11){
			level2 = true;
		}
		
		if(morreu2 || level2){
			if(levelStateChange){
				bgMusic.stop();
				sec = secondsPassed;
				levelStateChange = false;
				morreu = true;
			}
			
			int tempoLVL1 = (player.getTimerM() * 60) + (player.getTimerS1() * 10) + player.getTimerS();
			
			if(secondsPassed > sec + 2){
				bgMusic.stop();
				musica = false;
				gsm.setPoints(points);
				gsm.setTimerLVL1(tempoLVL1);
				if(level2 && secondsPassed >= sec + 1){
					gsm.setState(GameStateManager.LEVEL2STATE);
				}
				else if(morreu2){
					gsm.setState(GameStateManager.GAMEOVERSTATE);
				}
			}
			
			if(level2){
				bgMusic.stop();
				musica = false;
				gsm.setTimerLVL1(tempoLVL1);
				gsm.setPoints(points);
				gsm.setHP(player.getHealth());
			}
		}
		
		//desenha o jogador
		player.draw(g);

		//se estiver em pausa desenha o background de pausa
		if(pause){
			paused.draw(g);
		}

		if(morreu && morreu2){
			gameoverstates.draw(g);
		}
	}

	public void keyPressed(int k) {
		//se clicar t começa a interaçao com 1 personagem
		if(k == KeyEvent.VK_ENTER && !pause && !walking){
			//garantir que o jogador esta na posiçao pretendida quando interage com o rodrigo
			if(player.getx() >= characters.get(0).getx() - (width) && player.getFacingRight() && 
			   player.gety() >= characters.get(0).gety() && player.getx() <= characters.get(0).getx() - (width / 3) || 
			   RodrigoTalk == 6){
				player.setInteracting(true);
				interacting = true;
				
				if(RodrigoTalk == 0){
					RodrigoTalk++;
					player.setRodrigo(RodrigoTalk);
				}
				
				else if(RodrigoTalk == 1){
					RodrigoTalk++;
					player.setRodrigo(RodrigoTalk);
					rodrigoTalk.stop();
				}
				
				else if(RodrigoTalk == 2){
					RodrigoTalk++;
					player.setRodrigo(RodrigoTalk);
					rodrigoTalk2.stop();
				}
				
				else if(RodrigoTalk == 3){
					RodrigoTalk++;
					rodrigoTalk3.stop();
					player.setRodrigo(RodrigoTalk);
				}

				else if(RodrigoTalk == 6){
					player.setRodrigo(RodrigoTalk);
					finalizaConversa();
					RodrigoTalk++;
				}
				else if(RodrigoTalk == 7 && rodrigoC){
					player.setRodrigo(RodrigoTalk);
					finalizaConversa();
				}
			}
			
			if(player.getx() >= characters.get(2).getx() && !player.getFacingRight() && SaraTalk != 18 &&
			   player.gety() >= characters.get(2).gety() && player.getx() - width <= characters.get(2).getx()){
				interacting = true;
				player.setInteracting(true);
					
				if(SaraTalk == 0){
					if(Rodrigo2Talk < 4){
						Rodrigo2Talk = 4;
						vez3 = true;
					}
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 1){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk1.stop();
				}
				
				else if(SaraTalk == 2){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk2.stop();
					finalizaConversa();
					scratching = true;
				}
				
				else if(SaraTalk == 3 ){
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 4){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk3.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 5){
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 6){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk3.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 7){
					SaraTalk = 4;
					player.setSara(SaraTalk);
				}
				//
				else if(SaraTalk == 8){
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 9){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk4.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 10){
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 11){
					SaraTalk = 8;
					player.setSara(SaraTalk);
					saraTalk4.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 12){
					player.setKunai();
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 13){
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk5.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 14){
					SaraTalk++;
					player.setSara(SaraTalk);
				}
				
				else if(SaraTalk == 15){
					SaraTalk = 12;
					player.setSara(SaraTalk);
					saraTalk5.stop();
					finalizaConversa();
				}
				
				else if(SaraTalk == 16){
					SaraTalk++;
					player.setSara(SaraTalk);
					vez2 = true;
				}
				
				else if(SaraTalk == 17){
					Rodrigo2Talk = 9;
					SaraTalk++;
					player.setSara(SaraTalk);
					saraTalk6.stop();
					finalizaConversa();
				}
			}

			if(player.getx() >= characters.get(1).getx() - (width) && player.getFacingRight() && Rodrigo2Talk != 8 && 
			   player.gety() >= characters.get(1).gety() && player.getx() <= characters.get(1).getx()){
				interacting = true;
				player.setInteracting(true);
				if(Rodrigo2Talk == 0){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
				}
				
				else if(Rodrigo2Talk == 1){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
					rodrigo2Talk1.stop();
					finalizaConversa();
				}
				
				else if(Rodrigo2Talk == 2){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
				}
				
				else if(Rodrigo2Talk == 3){
					Rodrigo2Talk = 0;
					player.setRodrigo2(Rodrigo2Talk);
					rodrigo2Talk1.stop();
					finalizaConversa();
				}
				
				else if(Rodrigo2Talk == 4){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
				}
				
				else if(Rodrigo2Talk == 5){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
					if(enemies.size() == 1){
						SaraTalk = 12;
					}
					vez2 = true;
					rodrigo2Talk2.stop();
					finalizaConversa();
				}

				else if(Rodrigo2Talk == 6){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
				}
				
				else if(Rodrigo2Talk == 7){
					Rodrigo2Talk = 4;
					player.setRodrigo2(Rodrigo2Talk);
					rodrigo2Talk2.stop();
					finalizaConversa();
				}
				
				else if(Rodrigo2Talk == 9){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
				}
				
				else if(Rodrigo2Talk == 10){
					Rodrigo2Talk++;
					player.setRodrigo2(Rodrigo2Talk);
					rodrigo2Talk3.stop();
					finalizaConversa();
				}
			}
		}
		
		//o jogador nao se pode mover enquanto interage com uma personagem
		if(!interacting && player.getHealth() > 0 && !level2){
			if(!pause){
				//o que acontece quando o jogador clica em cada uma das seguintes letras
				if(k == KeyEvent.VK_LEFT){
					player.setLeft(true);
					walking = true;
				}
				
				if(k == KeyEvent.VK_RIGHT){
					player.setRight(true);
					walking = true;
				}
				
				if(k == KeyEvent.VK_DOWN){
					player.setDown(true);
				}
				
				if(k == KeyEvent.VK_UP){
					player.setJumping(true);
				}

				if(k == KeyEvent.VK_E && gliding){
					player.setGliding(true);
				}
				
				if(k == KeyEvent.VK_R && scratching){
					player.setScratching();
				}

				if(k == KeyEvent.VK_F && firing){
					player.setFiring();
				}
				if(k == KeyEvent.VK_Q){
					System.out.println(SaraTalk + " " + Rodrigo2Talk);
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