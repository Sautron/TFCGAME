package Entity;

import java.util.HashMap;

import Audio.AudioPlayer;
import TileMap.TileMap;

public class Enemy extends MapObject {
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean fallingRL;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	private HashMap<String, AudioPlayer> sfx;
	
	public Enemy(TileMap tm) {
		super(tm);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("EnemyDeath", new AudioPlayer("/SFX/EnemyDeath.mp3"));
		sfx.put("hit", new AudioPlayer("/SFX/hit.mp3"));
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void hit(int damage) {
		if(dead || flinching){
			return;
		}
		
		health -= damage;
		sfx.get("hit").play();
		if(health < 0){
			health = 0;
		}
		
		if(health == 0){
			sfx.get("EnemyDeath").play();
			dead = true;
		}
		
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update() {
	}
}