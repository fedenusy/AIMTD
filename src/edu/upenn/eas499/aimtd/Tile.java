package edu.upenn.eas499.aimtd;

/**
 * A tile in the TD map.
 * @author fedenusy
 *
 */
class Tile {
	
	// Monsters travel over ROAD tiles in an attempt to reach
	// an OBJECTIVE tile.
	// Towers can be built on FIELD tiles.
	// ROCK tiles represent dead space.
	public enum Type { ROAD, OBJECTIVE, FIELD, ROCK }
	
	
	// Instance variables
	private Type type;
	private int x, y;
	
	// Getter methods
	public Type getType() { return type; }
	public int getX() { return x; }
	public int getY() { return y; }
	
	// Public methods
	public Tile(int x, int y, Type type) {
		if (type==null) throw new IllegalArgumentException("Invalid Tile.Type for Tile instance");
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return Whether this is a Road or Objective type Tile.
	 */
	public boolean isWalkable() {
		return (type.equals(Type.ROAD) || type.equals(Type.OBJECTIVE));
	}
	
	/**
	 * @param t The destination tile.
	 * @return The Euclidean distance between this tile and t.
	 */
	public double distanceTo(Tile t) {
		return Math.sqrt((x-t.getX())^2+(y-t.getY())^2);
	}
	
}
