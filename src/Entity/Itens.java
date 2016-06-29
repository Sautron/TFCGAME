package Entity;

import TileMap.TileMap;

public class Itens extends MapObject{
	
	protected boolean picked;
	
	public Itens(TileMap tm) {
		super(tm);
	}
	
	public boolean isPicked() {
		return picked;
	}
	
	public void picked() {
		picked = true;
	}
	
	public void update() {
		
	}
}