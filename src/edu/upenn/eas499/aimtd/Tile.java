package edu.upenn.eas499.aimtd;

import java.util.Comparator;

/**
 * A tile in the TD map.
 * @author fedenusy
 *
 */
class Tile implements Comparator<Tile> {
	
	///// Monsters travel over ROAD tiles in an attempt to reach
	///// an OBJECTIVE tile.
	///// Towers can be built on FIELD tiles.
	///// ROCK tiles represent dead space.
	enum Type { ROAD, OBJECTIVE, FIELD, ROCK }
	
	
	///// Instance variables /////
	private Type _type;
	private int _x, _y;
	private int _cost;
	private Tile _previous;
	
	
	///// Constructors /////
	Tile(int x, int y, Type type) {
		if (type==null) throw new IllegalArgumentException("Invalid Tile.Type for Tile instance");
		_type = type;
		_x = x;
		_y = y;
		_cost = Integer.MAX_VALUE;
	}
	
	
	///// Getter methods /////
	Type getType() { return _type; }
	int getX() { return _x; }
	int getY() { return _y; }
	int getCost() { return _cost; }
	Tile getPrevious() { return _previous; }
	
	
	///// Setter methods /////
	void setCost(int cost) { _cost = cost; }
	void setPrevious(Tile previous) { _previous = previous; }
	
	
	///// Package-protected methods /////
	/**
	 * @return Whether this is a Road or Objective type Tile.
	 */
	boolean isWalkable() {
		return (_type.equals(Type.ROAD) || _type.equals(Type.OBJECTIVE));
	}
	
	/**
	 * @param t The destination tile.
	 * @return The Euclidean distance between this tile and t.
	 */
	double distanceTo(Tile t) {
		return Math.sqrt((_x-t.getX())^2+(_y-t.getY())^2);
	}

	
	///// Public methods /////
	public int compare(Tile t1, Tile t2) {
		if (t1.getCost() < t2.getCost()) return 1;
		else if (t1.getCost() > t2.getCost()) return -1;
		else return 0;
	}
	
}
