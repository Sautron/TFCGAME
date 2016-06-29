//Class that makes NPC's (Non-Player Characters) of the game interactable
//Classe que torna possivel a interacção com outras personagens do jogo

package Entity;

import TileMap.TileMap;

public class Character extends MapObject{
	
	protected boolean interact;
	
	public Character(TileMap tm) {
		super(tm);
	}
	public void update() {
		
	}
	
	public boolean interacting() {
		return interact;
	}
	
	public void interact() {
		interact = true;
	}
	
	public void notInteract(){
		interact = false;
	}
}