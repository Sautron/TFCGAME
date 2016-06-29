package Entity;

import TileMap.*;
import Audio.AudioPlayer;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends MapObject{

	//timer
	private int timerS = 0;
	private int timerM = 0;
	private int timerS1 = 0;
	private int secondsPassed;
	private int sec;
	private boolean first = true;

	// player stuff
	private boolean walking;
	private boolean male;
	private boolean vez = true;
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean pause;
	private boolean dead = false;
	private boolean dead2 = false;
	private boolean flinching;
	private long flinchTimer;
	private boolean kunai = false;
	
	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;

	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;

	// gliding
	private boolean gliding;

	//enemy
	private boolean platform3 = false, platform4 = false, platform5 = false;
	private boolean platform8 = false, platform9 = false, platform10 = false;
	private boolean platform13 = false, platform14 = false, platform15 = false;
	private double platformSpeed = 0.7;
	private boolean platformRigth, platformUp, platformUp2;
	private double platformY;
	private int nearDeathS=0;

	//conversaçao
	private boolean interacting;
	private int rodrigo;
	private int sara;
	private int rodrigo2;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			10, 10, 10, 2, 10, 10, 10, 10, 1
	};

	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	private static final int DEAD = 7;
	private static final int ISDEAD = 8;

	private HashMap<String, AudioPlayer> sfx;

	public Player(TileMap tm, boolean male) {
		super(tm);
		this.male = male;
		width = 32;
		height = 32;
		cwidth = 18;
		cheight = 18;

		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		facingRight = true;

		health = maxHealth = 5;
		fire = maxFire = 2000;

		fireCost = 400;
		fireBallDamage = 1;
		fireBalls = new ArrayList<FireBall>();

		scratchDamage = 2;
		scratchRange = 32;

		start();

		// load sprites
		try {
			BufferedImage spritesheet;

			if(male){
				spritesheet = ImageIO.read(
						getClass().getResourceAsStream("/Sprites/Player/NinjaBoy.png"));
			}

			else{
				spritesheet = ImageIO.read(
						getClass().getResourceAsStream("/Sprites/Player/NinjaGirl.png"));
			}

			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 8; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(bi);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		//se estiver vivo e nao tiver nenhuma acçao carregada muda para iddle
		if(!dead && !dead2){
			animation = new Animation();
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400);
		}

		//sfx's
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sfx.put("scratch", new AudioPlayer("/SFX/scratch.mp3"));
		sfx.put("GameOver", new AudioPlayer("/SFX/GameOver.mp3"));
		sfx.put("morte", new AudioPlayer("/SFX/morte.mp3"));
		sfx.put("points", new AudioPlayer("/SFX/points.mp3"));
		sfx.put("HurtBoy", new AudioPlayer("/SFX/HurtBoy.mp3"));
		sfx.put("HurtGirl", new AudioPlayer("/SFX/HurtGirl.mp3"));
		sfx.put("Powerup", new AudioPlayer("/SFX/Powerup.mp3"));
		sfx.put("NearDeath", new AudioPlayer("/SFX/Heart_beat.mp3"));
		sfx.put("Teleport", new AudioPlayer("/SFX/teleport.mp3"));
	}

	//getters
	public boolean getWalking(){
		return walking;
	}

	public double getDY(){
		return dy;
	}

	public int getTimerS(){
		return timerS;
	}

	public int getTimerS1(){
		return timerS1;
	}

	public int getTimerM(){
		return timerM;
	}

	public boolean getDead(){
		return dead;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getFire() {
		return fire;
	}

	public int getMaxFire() {
		return maxFire;
	}

	public boolean getFacingRight(){
		return facingRight;
	}

	public boolean getKunai(){
		return kunai;
	}

	public boolean getInteracting(){
		return interacting;
	}

	public int getRodrigo(){
		return rodrigo;
	}

	public int getSara(){
		return sara;
	}

	public int getRodrigo2(){
		return rodrigo2;
	}

	public void setRodrigo2(int rodrigo2) { 
		this.rodrigo2 = rodrigo2;
	}
	public void setSara(int sara) { 
		this.sara = sara;
	}

	public void setRodrigo(int rodrigo) { 
		this.rodrigo = rodrigo;
	}

	public void setInteracting(boolean interacting) { 
		this.interacting = interacting;
	}

	public void setFiring() { 
		firing = true;
	}

	public void setKunai(){
		kunai = true;
	}

	public void setPause(boolean b){
		pause = b;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setScratching() {
		scratching = true;
	}

	public void setDead(){
		dead = true;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setGliding(boolean b) { 
		gliding = b;
	}

	//verifica se o item foi colecionado
	public void checkPick(ArrayList<Itens> itens, int itemType){
		if(itemType == 0){
			for(int i = 0; i < itens.size(); i++){
				Itens in = itens.get(i);
				if(intersects(in)){
					sfx.get("points").play();
					in.picked();
				}
			}
		}
		if(itemType == 1){
			if(intersects(itens.get(0))){
				sfx.get("Teleport").play();
				setPosition(width * 42, height * 8);
			}
		}
		if(itemType == 2){
			for(int i = 0; i < itens.size(); i++){
				if(i < itens.size()){
					Itens in = itens.get(i);
					if(intersects(in)) {
						//caso nao esteja full hp
						if(health != maxHealth){
							sfx.get("Powerup").play();
							health++;
							in.picked();
						}
					}
				}
			}
		}
	}

	//corrimao Nota: (ainda nao implementado)
	public void platform2(ArrayList<Itens> platform){
		for(int i = 0; i < platform.size(); i++){
			Itens p = platform.get(i);
			if(intersects(p)){
				dy = 0;
			}
		}
	}

	//verifica se estamos em conversa com as personagens (sara e rodrigo)
	public void checkInteract(ArrayList<Character> characters){
		for(int i = 0; i < characters.size(); i++){
			Character c = characters.get(i);
			if(intersects(c)){
				c.interact();
			}
			else{
				c.notInteract();
			}
		}
	}

	//plataformas horizontais
	public void platformAI(ArrayList<Enemy> platforms){
		//loop through platforms
		for(int i = 0; i < 1; i++){
			//jogador ficar por cima da plataforma
			if(y >= platforms.get(i).y - height && y <= platforms.get(i).y - 27 
					&& x > platforms.get(i).x - 40 && x < platforms.get(i).x + 40){
				platform2 = true;
				platform5 = true;
			}
			else{
				platform2 = false;
				if(dy == 0){
					platform5 = false;
				}
			}

			//jogador nao conseguir passar atraves da plataforma a partir da parte inferior
			if(!platform5 && y > platforms.get(i).y && x > platforms.get(i).x - 40 
					&& x < platforms.get(i).x + 40 && !platform2){
				platform3 = true;
			}
			else{
				platform3 = false;
			}

			//o jogador nao ser capaz de atravesar a plataforma (dos lados)
			if(y >= platforms.get(i).y - 30 && y <= platforms.get(i).y + 30 
					&& x > platforms.get(i).x - 40 && x < platforms.get(i).x + 40 && !platform2){
				platform4 = true;
			}
			else{
				platform4 = false;
			}

			//dar movimento a plataforma
			if(platformRigth && platforms.get(i).x > width * 95 + (7 * width)){
				platforms.get(i).setDx(-platformSpeed);
				platformRigth = false;
			}
			if(!platformRigth && platforms.get(i).x <= width * 95){
				platforms.get(i).setDx(platformSpeed);
				platformRigth = true;
			}
		}
	}

	//plataforma vertical
	public void platformAI2(ArrayList<Enemy> platform, int n){
		//loop through traps
		for(int i = 0; i < platform.size(); i++){
			if(i > 1 || n == 3){
				//o jogador nao cair da plataforma (quando esta sobre a mesma)
				if(y >= platform.get(i).y - width && y <= platform.get(i).y - 27 
						&& x > platform.get(i).x - 40 && x < platform.get(i).x + 40 
						&& currentAction != 2){
					platform7 = true;
					platform10 = true;
					platformY = platform.get(i).y - 30;
				}
				else{
					platform7 = false;
					if(dy == 0){
						platform10 = false;
					}
				}

				//O jogador nao atravessar a plataforma (a partir da parte inferior)
				if(!platform10 && y < platform.get(i).y + 30 && x > platform.get(i).x - 40 
						&& x < platform.get(i).x + 40 && !platform7){
					platform8 = true;
				}
				else{
					platform8 = false;
				}

				//o jogador nao atravessar a plataforma(a partir da direita/esquerda)
				if(y >= platform.get(i).y - 30 && y <= platform.get(i).y + 30
						&& x > platform.get(i).x - 40 && x < platform.get(i).x + 40 && !platform7){
					platform9 = true;
				}
				else{
					platform9 = false;
				}

				//movimentar a plataforma
				if(n != 3){
					if(!platformUp && platform.get(1).y >= height * 25){
						platform.get(1).setDy(-platformSpeed);
						platformUp = true;
					}

					if(platformUp && platform.get(1).y <= height * 18.5){
						platform.get(1).setDy(platformSpeed);
						platformUp = false;
					}

					if(!platformUp2 && platform.get(2).y >= height * 16){
						platform.get(2).setDy(-platformSpeed);
						platformUp2 = true;
					}

					if(platformUp2 && platform.get(2).y <= height * 7){
						platform.get(2).setDy(platformSpeed);
						platformUp2 = false;
					}
				}
				if(n == 3){
					if(!platformUp && platform.get(i).y >= height * 45){
						platform.get(i).setDy(-platformSpeed);
						platformUp = true;
					}

					if(platformUp && platform.get(i).y <= height * 35){
						platform.get(i).setDy(platformSpeed);
						platformUp = false;
					}
				}
			}
			//porque és bue especial
			else if(n != 3){
				if(y >= platform.get(1).y - width && y <= platform.get(1).y - 27 && 
						x > platform.get(1).x - 40 && x < platform.get(1).x + 40 && currentAction != 2){
					platform12 = true;
					platform15 = true;
					platformY = platform.get(1).y - 30;
				}
				else{
					platform12 = false;
					if(dy == 0){
						platform15 = false;
					}
				}

				//O jogador nao atravessar a plataforma (a partir da parte inferior)
				if(!platform15 && y > platform.get(1).y + 30 && x > platform.get(1).x - 40 && 
						x < platform.get(1).x + 40 && !platform12){
					platform13 = true;
				}
				else{
					platform13 = false;
				}

				//o jogador nao atravessar a plataforma(a partir da direita/esquerda)
				if(y >= platform.get(1).y - 30 && y <= platform.get(1).y + 30 && 
						x > platform.get(1).x - 40 && x < platform.get(1).x + 40 && !platform12){
					platform14 = true;
				}
				else{
					platform14 = false;
				}
			}
		}
	}

	//verificar se atingimos o adversario
	public int checkAttack(ArrayList<Enemy> enemies) {
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);

			// scratch attack
			if(scratching) {
				if(facingRight) {
					if(e.getx() > x && e.getx() < x + scratchRange && 
							e.gety() > y - height / 2 && e.gety() < y + height / 2) {
						e.hit(scratchDamage);
						return i;
					}
				}

				else {
					if(e.getx() < x && e.getx() > x - scratchRange &&
							e.gety() > y - height / 2 && e.gety() < y + height / 2) {
						e.hit(scratchDamage);
						return i;
					}
				}
			}

			// fireballs
			for(int j = 0; j < fireBalls.size(); j++) {
				if(fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					return i;
				}
			}

			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
		}
		return 50;
	}

	//retirar hp ao jogador
	public void hit(int damage) {
		//se o jogador nao morrer
		if(!dead && !dead2){
			if(flinching){
				return;
			}

			//barulho de dano (rapaz)
			if(male){
				sfx.get("HurtBoy").play();
			}

			//barulho de dano(rapariga)
			if(!male){
				sfx.get("HurtGirl").play();
			}

			//retirar vida
			health -= damage;

			//caso a vida seja inferior a 0 o display continua a 0
			if(health < 0){
				health = 0;
			}

			//fazer flinch
			flinching = true;
			flinchTimer = System.nanoTime();
		}
	}

	//movimento do jogador
	private void getNextPosition() {
		if(!dead && !dead2){
			// movement
			if(left) {
				if(!platform2){
					dx -= moveSpeed;
					if(dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				}
				else{
					if(!platformRigth){
						dx += moveSpeed - 1.1;
					}
					else{
						dx -= moveSpeed + 1.1;
						if(dx < -maxSpeed + 1.1) {
							dx = -maxSpeed + 1.1;
						}
					}
				}
			}

			else if(right) {
				if(!platform2){
					dx += moveSpeed;
					if(dx > maxSpeed) {
						dx = maxSpeed;
					}
				}
				else{
					if(!platformRigth && walking){
						dx = dx * - 1;
					}
				}
			}

			else {
				if(dx > 0) {
					dx -= stopSpeed;
					if(dx < 0) {
						dx = 0;
					}
				}

				else if(dx < 0) {
					dx += stopSpeed;
					if(dx > 0) {
						dx = 0;
					}
				}
			}

			// cannot move while attacking, except in air
			if((currentAction == SCRATCHING || currentAction == FIREBALL) && 
					!(jumping || falling)){
				dx = 0;
			}

			// jumping
			if(jumping && !falling) {
				sfx.get("jump").play();
				dy = jumpStart;
				falling = true;
			}

			// falling
			if(falling) {
				if(dy > 0 && gliding){
					dy += fallSpeed * 0.1;
				}
				else{
					if(!platform2 && !platform7 && !platform12){
						if(level == 1 && x > 392 && x < 426 && y == 320){
							dy = 0;
							falling = false;
						}
						else{
							dy += fallSpeed;
						}
					}
				}

				if(dy > 0){
					jumping = false;
				}

				if(dy < 0 && !jumping){
					dy += stopJumpSpeed;
				}

				if(dy > maxFallSpeed){
					dy = maxFallSpeed;
				}
			}
		}

		else{
			dy = 0;
			dx = 0;
		}

		//impedir jogador de cair da plataforma
		if(platform2 || platform7 || platform12){
			if(dy > 0){
				dy = 0;
			}
		}

		//impedir o jogador de atravesar a plataforma por baixo
		if(platform3 || platform8 || platform13){
			if(dy < 0 && currentAction != JUMPING){
				dy = 0;
			}
			platform8 = false;
			platform3 = false;
			platform13 = false;
		}

		//impedir jogador de atravesar a plataforma pelos lados
		if(platform4 || platform9 || platform14){
			if(dx != 0){
				dx = 0;
			}
			platform4 = false;
			platform9 = false;
			platform14 = false;
		}
	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		//if(dead == false){
		if(health == 1){			
			if(nearDeathS == 0){
				nearDeathS = secondsPassed + 4;	
				sfx.get("NearDeath").play();
			}
			if(nearDeathS <= secondsPassed){
				nearDeathS = 0;
			}
		}
		else{
			sfx.get("NearDeath").stop();
		}

		// check attack has stopped
		if(currentAction == SCRATCHING) {
			if(animation.hasPlayedOnce()){
				scratching = false;
			}
		}

		if(currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()){
				firing = false;
			}
		}

		// fireball attack
		fire+=1;
		if(fire > maxFire){
			fire = maxFire;
		}

		if(firing && currentAction != FIREBALL) {
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}

		// update fireballs
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}

		// check done flinching
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;

			if(elapsed > 1000) {
				flinching = false;
			}
		}

		//se morrer
		if(dead){
			if(currentAction != DEAD) {
				currentAction = DEAD;
				animation.setFrames(sprites.get(DEAD));
				animation.setDelay(500);
				width = 32;

				if(first){
					first = false;
					sec = secondsPassed;
					dead2 = true;
				}
			}
			sfx.get("GameOver").play();
		}

		//muda para a ultima linha (1 frame)
		if(dead2 && secondsPassed > sec + 4){
			dead = false;
			dead2 = true;
			currentAction = ISDEAD;
			animation.setFrames(sprites.get(ISDEAD));
			animation.setDelay(Integer.MAX_VALUE);
			width = 32;
		}

		// set animation
		if(scratching) {
			if(currentAction != SCRATCHING) {
				sfx.get("scratch").play();
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(20);
				width = 32;
			}
		}

		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(50);
				width = 32;
			}
		}

		else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 32;
				}
			}

			else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 32;
			}
		}

		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 32;
			}
		}

		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 32;
			}
		}

		else {
			if(currentAction != IDLE && !dead && !dead2) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 32;
			}
		}

		animation.update();

		// set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right){
				facingRight = true;
			}

			if(left){
				facingRight = false;
			}
		}

		if(currentAction != IDLE){
			walking = true;
		}
		else{
			walking = false;
		}

		//caso o jogador esteja sobre a plataforma
		if(platform2){
			//dar ao jogador velocidade = a da plataforma
			if(platformRigth){
				dx = 1.1;
			}
			else{
				dx = -1.1;
			}
		}

		//System.out.println(dx);

		if((platform7 || platform12) && currentAction != 2){
			y = platformY;
		}
		if(currentAction == 2){
			platform7 = false;
		}
		//}

		/*else{
			flinching = false;
		}*/
		
		if(morrer){
			dx = 0;
			walking = false;
			jumping = false;
			gliding = false;
			falling = true;
			health = 0;
		}
	}

	public void draw(Graphics2D g) {
		setMapPosition();

		// draw fireballs
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}

		// draw player
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;

			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}

	static Thread thread = new Thread();
	//timer
	Timer timer1 = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			if(!dead && !dead2 && !pause){
				timerS++;
			}
			secondsPassed++;
			if(getHealth() == 0){
				if(vez){
					vez = false;
					sfx.get("morte").play();
				}
			}

			if(timerS == 10){
				timerS = 0;
				timerS1++;
			}

			if(timerS1 == 6){
				timerS1 = 0;
				timerM++;
			}
		}
	};

	public void start(){
		timer1.scheduleAtFixedRate(task, 1000, 1000);
	}
}