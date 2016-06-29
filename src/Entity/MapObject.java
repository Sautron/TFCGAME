package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Rectangle;

public abstract class MapObject {
	protected int time;
	protected boolean morrer;
	protected boolean key;
	
	//adversario
	protected boolean e_up;
	protected boolean e_down;
	protected boolean e_left;
	protected boolean e_right;
	
	//conversas
	protected int level;
	protected boolean rodrigo1;
	protected int mushroom;
	protected int health;
	protected int points2;
	
	//TODO
	//protected boolean male;
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	protected boolean[] trapA = new boolean[10];
	protected boolean platform1;
	protected boolean platform2;
	protected boolean platform6;
	protected boolean platform7;
	protected boolean platform11;
	protected boolean platform12;
	
	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize(); 
	}
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}
	
	public void checkTileMapCollision() {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			
			else {
				ytemp += dy;
			}
		}
		
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			
			else {
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			
			else {
				xtemp += dx;
			}
		}
		
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
	}
	
	//TODO
	/*public boolean getMale(){
		return male;
	}*/
	
	//getters
	public boolean getRodrigo1(){
		return rodrigo1;
	}
	
	public int getx() {
		return (int)x;
	}
	
	public int gety() {
		return (int)y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCWidth() {
		return cwidth;
	}
	
	public int getCHeight() {
		return cheight;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMushroom(){
		return mushroom;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getPoints(){
		return points2;
	}
	
	public double getDx(){
		return dx;
	}
	
	public double getDy(){
		return dy;
	}
	
	public double getMaxSpeed(){
		return maxSpeed;
	}
	
	public boolean getTrapA(int i){
		return trapA[i];
	}
	
	public boolean getPlatform1(){
		return platform1;
	}
	
	public boolean getPlatform2(){
		return platform2;
	}
	
	public boolean getPlatform11(){
		return platform11;
	}
	
	public boolean getPlatform12(){
		return platform12;
	}
	
	public int getTime(){
		return time;
	}
	
	public double getMoveSpeed(){
		return moveSpeed;
	}
	
	public boolean getKey(){
		return key;
	}
	
	//setters
	
	public void setKey(boolean key){
		this.key = key;
	}
	
	public void setFalling(boolean falling){
		this.falling = falling;
	}
	
	public void setMorrer(boolean morrer){
		this.morrer = morrer;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
	public void setPlatform12(boolean platform12){
		this.platform12 = platform12;
	}
	
	public void setPlatform11(boolean platform11){
		this.platform11 = platform11;
	}
	
	public boolean getPlatform6(){
		return platform6;
	}
	
	public boolean getPlatform7(){
		return platform7;
	}
	
	public void setPlatform7(boolean platform7){
		this.platform7 = platform7;
	}
	
	public void setPlatform6(boolean platform6){
		this.platform6 = platform6;
	}
	
	public void setPlatform2(boolean platform2){
		this.platform2 = platform2;
	}
	
	public void setPlatform1(boolean platform1){
		this.platform1 = platform1;
	}
	
	public void setTrapA(boolean trapA, int i){
		this.trapA[i] = trapA;
	}
	
	public void setDx(double dx){
		this.dx = dx;
	}
	
	public void setDy(double dy){
		this.dy = dy;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setPoints(int points2){
		this.points2 = points2;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void setMushroom(int mushroom){
		this.mushroom = mushroom;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) {
		left = b;
	}
	
	public void setRight(boolean b) {
		right = b;
	}
	
	public void setUp(boolean b) {
		up = b;
	}
	
	public void setDown(boolean b) {
		down = b;
	}
	
	public void setJumping(boolean b) {
		jumping = b;
	}
	
	public void setRodrigo1(boolean b){
		rodrigo1 = b;
	}
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH ||
			   y + ymap + height < 0 || y + ymap - height > GamePanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(facingRight) {
			g.drawImage(animation.getImage(), 
					   (int)(x + xmap - width / 2), 
					   (int)(y + ymap - height / 2),
					   null);
		}
		
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null);
		}
	}
}