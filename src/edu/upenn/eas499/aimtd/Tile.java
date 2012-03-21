package edu.upenn.eas499.aimtd;

/**
 * A tile in the TD map.
 * @author fedenusy
 *
 */
class Tile {
	
	// Monsters travel over ROAD tiles.
	// Towers can be built on FIELD tiles.
	// ROCK tiles represent dead space.
	public enum Type { ROAD, FIELD, ROCK }
	
	
	// Instance variables
	private Type type;
	private int x, y;
	
	// Getter methods
	public Type getType() { return type; }
	public int getX() { return x; }
	public int getY() { return y; }
	
	
	public Tile(Type type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
}
