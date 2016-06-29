package Entity.Enemies;

import java.util.ArrayList;
import java.util.HashMap;

import Audio.AudioPlayer;
import Entity.Enemy;
import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;

public class EnemyAI extends MapObject{

	private boolean trapX2 = true;
	
	//traps
	private boolean[] trap2 = new boolean[10];
	private boolean[] trap3 = new boolean[10];
	private boolean[] trapX = new boolean[10];
	private boolean[] desce = new boolean[2];
	
	private HashMap<String, AudioPlayer> sfx;
	
	public EnemyAI(TileMap tm){
		super(tm);
		width = 32;
		height = 32;
		
		//sfx's
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("TrapHitFloor", new AudioPlayer("/SFX/TrapHitFloor.mp3"));
		sfx.put("WoodCrack", new AudioPlayer("/SFX/WoodCrack.mp3"));
	}

	//Ai dos adversarios voadores (skull)
	public void enemyAI(Enemy enemy, int up2, int down2, int left2, int right2, int up3, int down3, int left3, 
		int right3, double enemyX, int enemyY, int playerX, int playerY){
		int EFup = up2 * height;
		int EFdown = down2 * height;
		int EFleft = left2 * width;
		int EFright = right2 * width;
		int EFAup = up3 * height;
		int EFAdown = down3 * height;
		int EFAleft = left3 * width;
		int EFAright = right3 * width;
		int enemyFX = (int)(enemyX * width);
		int enemyFY = enemyY * height;

		if(playerX >= enemyFX - EFAleft && playerX <= enemyFX + EFAright && playerY >= enemyFY - EFAup && 
		   playerY <= enemyFY + EFAdown){
			if(playerX > enemy.getx()){
				enemy.setDx(enemy.getMoveSpeed());
			}
			else{
				enemy.setDx(-enemy.getMoveSpeed());
			}

			if(playerY > enemy.gety()){
				enemy.setDy(+enemy.getMoveSpeed());
			}
			else{
				enemy.setDy(-enemy.getMoveSpeed());
			}
		}
		else{
			if(enemy.getDx() == 0){
				enemy.setDx(-enemy.getMoveSpeed());
			}

			if(enemy.getx() <= enemyFX - EFleft){
				enemy.setDx(+enemy.getMoveSpeed());
			}

			else if(enemy.getx() >= enemyFX + EFright){
				enemy.setDx(-enemy.getMoveSpeed());
			}

			//patrulha (cima baixo)
			if(enemy.getDy() == 0){
				enemy.setDy(enemy.getMoveSpeed());
			}

			if(enemy.gety() >= enemyFY + EFdown){
				enemy.setDy(-enemy.getMoveSpeed());
			}

			else if(enemy.gety() <= enemyFY - EFup){
				enemy.setDy(enemy.getMoveSpeed());
			}
		}
	}
	
	//AI dos zombieWhite
	public void enemyAI2(Enemy enemy, int m, int m2, int x, int R, int L, double player){
		int ERight = m * width;
		int ELeft = m2 * width;
		int Eposicao = x * width;
		int zombieR = R * width;
		int zombieL = L * width;

		if(!trapX2 && player <= Eposicao + zombieL && player >= Eposicao - (8.5 * width) && Eposicao == 54 * width){
			if(player > enemy.getx()){
				enemy.setDx(enemy.getMoveSpeed());
			}
			else{
				enemy.setDx(-enemy.getMoveSpeed());
			}
		}

		else if(player <= Eposicao + zombieL && player >= Eposicao - zombieR){
			if(player > enemy.getx()){
				enemy.setDx(enemy.getMoveSpeed());
			}
			else{
				enemy.setDx(-enemy.getMoveSpeed());
			}
		}
		else{
			if(!trapX2 && Eposicao == 54 * width){
				if(enemy.getx() >= Eposicao + ELeft){
					enemy.setDx(-enemy.getMoveSpeed());
				}

				else if(enemy.getx() <= Eposicao - (8.5 * width)){
					enemy.setDx(enemy.getMoveSpeed());
				}
			}
			else{
				if(enemy.getx() >= Eposicao + ELeft){
					enemy.setDx(-enemy.getMoveSpeed());
				}

				else if(enemy.getx() <= Eposicao - ERight){
					enemy.setDx(enemy.getMoveSpeed());
				}
			}
		}
	}
	
	//ai das 2 traps metálicas
	public void trapAi2(ArrayList<Enemy> trap, double[] trapY, double[] trapI, Player player){
		//loop throught enemies
		for(int i = 0; i < trap.size(); i++){
			Enemy t = trap.get(i);
			//caso o jogador esteja exactamente por baixo da trap
			//a trap começa a cair

			if(player.getx() > (t.getx() - width) && player.getx() < (t.getx() + width) && 
			   player.gety() >= (trapI[i] + trapY[i]) * height){

				if(player.gety() > t.gety() + 20 && player.gety() < t.gety() + 32){
					player.setMorrer(true);
					player.setHealth(0);
					System.out.println("1");
				}

				if(!trapX[i + 3]){
					sfx.get("TrapHitFloor").play();
					trapX[i + 3] = true;
				}
				desce[i] = true;
			}
			//System.out.println(t.y);
			if(desce[i] == true && t.gety() < 70.5 * height){
				t.setY(t.gety() + 3);
			}

			//puder saltar em cima da trap
			if((intersects(t) || t.gety() > (trapI[i] + trapY[i]) * height) && (player.getx() > (t.getx() - width) && 
			    player.getx() < (t.getx() + width) && 
				!trap3[i] && y + 25 > t.gety())){
				trap2[i] = true;
			}

			//enquanto a trap nao colidir com o chao
			if(player.gety() > (trapI[i] + 2) * height && (player.getx() > (t.getx() - width) && 
			   player.getx() < (t.getx() + width) && player.gety() + 25 <= t.gety() && 
			   player.gety() + 30 >= t.gety() && player.gety() < t.gety())){
				//caso ele esteja sobre a trap (ele n morre)
				trap2[i] = false;
				player.setFalling(false);
				
				if(player.getDy() > 0){
					trapA[i] = true;
					player.setDy(0);
				}
			}
			
			//depois de colidir com o chao
			else{
				trapA[i] = false;
				if((player.getx() < (t.getx() - width) || player.getx() > (t.getx() + width))){
					trap3[i] = false;
				}
			}

			if(player.gety() > t.gety() + height && player.getx() > (t.getx() - width - 5) && 
			   player.getx() < (t.getx() + width + 5) && player.gety() < t.gety() + height + 3){
				player.setY(t.gety() + height);
			}
			if(i == 0){
				if(player.getx() < t.getx() - width + 5 || player.getx() > t.getx() + width - 5){
					if(t.gety() > 70.5 * height){
						if(player.gety() > t.gety()){
							if(player.getx() < t.getx() + width && player.getx() > t.getx() - width + 5){
								player.setX(t.getx() + width);
							}
						}
					}
				}
			}
			if(i == 1){
				if(player.getx() < t.getx() - width + 5 || player.getx() > t.getx() + width - 5){
					if(t.gety() > 70.5 * height){
						if(player.gety() > t.gety()){
							if(player.getx() > t.getx() - width && player.getx() < t.getx() + width - 5){
								player.setX(t.getx() - width);
							}
						}
					}
				}
			}
		}
	}
	
	//ai da trap dentro da caverna
	public void trapAi(ArrayList<Enemy> trap, double[] trapY, double[] trapI, Player player){
		//loop throught enemies
		for(int i = 0; i < trap.size(); i++){

			Enemy t = trap.get(i);
			//caso o jogador esteja exactamente por baixo da trap
			//a trap começa a cair

			if(player.getx() > (t.getx() - width) && player.getx() < (t.getx() + width) && 
			   player.gety() >= (trapI[i] + trapY[i]) * height && player.gety() < t.gety() + (6 * height)){
				if(player.gety() > t.gety() + 20 && player.gety() < t.gety() + height){
					player.setHealth(0);
				}
				t.setDy(trap.get(i).getMoveSpeed());
				if(!trapX[i]){
					sfx.get("TrapHitFloor").play();
					trapX[i] = true;
				}
			}

			//puder saltar em cima da trap
			if((intersects(t) || t.gety() > (trapI[i] + trapY[i]) * height) && (player.getx() > (t.getx() - width - 5) && 
				player.getx() < (t.getx() + width + 5) && !trap3[i] && player.gety() + 25 > t.gety())){
				trap2[i] = true;
			}

			//dizer ao zombieWhite que já pode percorrer a caverna na sua totalidade (patrulhar)
			if(t.gety() == 21.5 * height){
				if(trapX2){
					sfx.get("TrapHitFloor").play();
					trapX2 = false;
				}
			}

			//enquanto a trap nao colidir com o chao
			if(player.gety() > (trapI[i] + 2) * height && (player.getx() > (t.getx() - width) && 
			   player.getx() < (t.getx() + width) && player.gety() + 25 <= t.gety() && player.gety() + 30 >= t.gety() && 
			   player.gety() < t.gety())){
				//caso ele esteja sobre a trap (ele n morre)
				player.setFalling(false);
				trap2[i] = false;
				if(player.getDy() > 0){
					trapA[i] = true;
					player.setDy(0);
				}
			}
			//depois de colidir com o chao
			else{
				trapA[i] = false;
				if((player.getx() < (t.getx() - width) || player.getx() > (t.getx() + width))){
					trap3[i] = false;
				}
			}

			if(player.gety() > t.gety() - 3 + height && player.getx() > (t.getx() - width - 5) && 
			   player.getx() < (t.getx() + width + 5) && player.gety() < t.gety() + height + 3){
				player.setDx(0);
				if(player.gety() < 72 * height)
					player.setY(t.gety() + height);
				player.setHealth(0);
				player.setMorrer(true);
			}
		}
	}
}
