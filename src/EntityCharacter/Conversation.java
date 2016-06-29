//Class were the game's characters monologues are written for further display
//Classe onde se encontram as falas das personagens do jogo para 

package EntityCharacter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Entity.Player;

public class Conversation {
	private Player player;
	
	private BufferedImage image;
	private Font font;
		
	public Conversation(Player p) {
		player = p;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Conversations/Balao_fala.png"));
			font = new Font("ARIAL", Font.PLAIN, 12);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		if(player.getInteracting()){
			g.drawImage(image, 0, 176, null);
		}
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		if(player.getInteracting() && player.getRodrigo() == 1){
			g.drawString("Uouow, hey! Didn't see you there friend! Are you a ninja?", 10, 200);
			g.drawString("Well I'm Rodrigo, and, I can actually teach you the basics", 10, 215);
			g.drawString("you need to survive in this world.", 10, 230);
		}
		
		else if(player.getInteracting() && player.getRodrigo() == 2){
			g.drawString("Since you already... probably have noticed, well, you", 10, 200);
			g.drawString("cannot pass throught this wall only by jumping.", 10, 215);
		}
		
		else if(player.getInteracting() && player.getRodrigo() == 3){
			g.drawString("But, you know what? If you glide by pressing \"E\" in the", 10, 200);
			g.drawString("middle of the air, it will become possible!", 10, 215);
		}
		
		else if((player.getInteracting() && player.getRodrigo() == 4)){
			g.drawString("And after that we will... AH! Wai... OH... where did he go?", 10, 200);
		}
		
		else if(player.getInteracting() && player.getSara() == 1){
			g.drawString("Welcome stranger, I'm Sara. I'm a master in martial arts.", 10, 200);
			g.drawString("I come here sometimes to train, since there are alot of", 10, 215);
			g.drawString("monsters in the cave ahead.", 10, 230);
		}
		
		else if(player.getInteracting() && player.getSara() == 2){
			g.drawString("I will teach you how to protect yourself. Just try to kill that", 10, 200);
			g.drawString("snail ahead with a melee attack by pressing \"R\".", 10, 215);
			g.drawString("Good luck.", 10, 230);
		}
		
		else if(player.getInteracting() && (player.getSara() == 4 || player.getSara() == 6)){
			g.drawString("Are you afraid of a snail? Don't make me laught!", 10, 200);
			g.drawString("HA HA HA HA HA.", 10, 215);
		}
		
		else if(player.getInteracting() && (player.getSara() == 9 || player.getSara() == 11)){
			g.drawString("Good job!", 10, 200);
		}
		
		else if(player.getInteracting() && (player.getSara() == 13 || player.getSara() == 15)){
			g.drawString("Rodrigo? I have no ideia who he is. A ranged attack you", 10, 200);
			g.drawString("say? Ok, I will teach you a simple ranged attack, you just ", 10, 215);
			g.drawString("need to press \"F\".", 10, 230);
		}
		
		else if(player.getInteracting() && player.getSara() == 17){
			g.drawString("I've tried to teach you everything I know, but you just don't", 10, 200);
			g.drawString("have it in you. Better luck next time.", 10, 215);
		}
		
		else if(player.getInteracting() && (player.getRodrigo2() == 1 || player.getRodrigo2() == 3)){
			g.drawString("Come on men don't be that guy. I can't let you pass", 10, 200);
			g.drawString("while you still don't know how to protect yourself.", 10, 215);
			g.drawString("Ask Sara! She will teach you.", 10, 230);
		}
		
		else if(player.getInteracting() && (player.getRodrigo2() == 5 || player.getRodrigo2() == 7)){
			g.drawString("Ok ok, thats actually pretty good, but not enought, you", 10, 200);
			g.drawString("need to know ranged attacks as well.", 10, 215);
		}
		
		else if(player.getInteracting() && player.getRodrigo2() == 10){
			g.drawString("Well done, you are learning quickly, now let us go to.", 10, 200);
			g.drawString("a more interesting to test your skills.", 10, 215);
		}
	}
}