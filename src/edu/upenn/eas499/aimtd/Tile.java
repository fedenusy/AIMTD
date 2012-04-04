package edu.upenn.eas499.aimtd;

/**
 * A tile in the TD map.
 * @author fedenusy
 *
 */
class Tile {
	
	///// Monsters travel over ROAD tiles in an attempt to reach
	///// an OBJECTIVE tile.
	///// Towers can be built on FIELD tiles.
	///// ROCK tiles represent dead space.
	public enum Type { ROAD, OBJECTIVE, FIELD, ROCK }
	
	
	///// Instance variables /////
	private Type _type;
	private int _x, _y;
	
	
	///// Constructors /////
	public Tile(int x, int y, Type type) {
		if (type==null) throw new IllegalArgumentException("Invalid Tile.Type for Tile instance");
		_type = type;
		_x = x;
		_y = y;
	}
	
	
	///// Getter methods /////
	public Type getType() { return _type; }
	public int getX() { return _x; }
	public int getY() { return _y; }
	
	
	///// Public methods /////
	/**
	 * @return Whether this is a Road or Objective type Tile.
	 */
	public boolean isWalkable() {
		return (_type.equals(Type.ROAD) || _type.equals(Type.OBJECTIVE));
	}
	
	/**
	 * @param t The destination tile.
	 * @return The Euclidean distance between this tile and t.
	 */
	public double distanceTo(Tile t) {
		return Math.sqrt((_x-t.getX())^2+(_y-t.getY())^2);
	}
	
}
