package edu.upenn.eas499.aimtd;

/**
 * A tile in the TD map.
 * @author fedenusy
 *
 */
class Tile implements Comparable<Tile> {
	
	///// Monsters travel over ROAD tiles in an attempt to reach
	///// an OBJECTIVE tile.
	///// Towers can be built on FIELD tiles.
	///// ROCK tiles represent dead space.
	enum Type { ROAD, OBJECTIVE, FIELD, ROCK }
	
	
	///// Instance variables /////
	private Type _type;
	private int _x, _y;
	private double _cost;
	private double _damageCost;
	private Tile _previous;
	
	
	///// Constructors /////
	Tile(int x, int y, Type type) {
		if (type==null) throw new IllegalArgumentException("Invalid Tile.Type for Tile instance");
		_type = type;
		_x = x;
		_y = y;
		_cost = Double.MAX_VALUE;
		_damageCost = 0;
	}
	
	
	///// Getter methods /////
	Type getType() { return _type; }
	int getX() { return _x; }
	int getY() { return _y; }
	double getCost() { return _cost; }
	/**
	 * Gets the maximum amount of damage a monster standing on this Tile could receive within a
	 * single tick().
	 * @return The Tile's damage cost.
	 */
	double getDamageCost() { return _damageCost; }
	Tile getPrevious() { return _previous; }
	
	
	///// Setter methods /////
	void setCost(double cost) { _cost = cost; }
	void setDamageCost(double damage) { _damageCost = damage; }
	void setPrevious(Tile previous) { _previous = previous; }
	
	
	///// Package-protected methods /////
	/**
	 * @return Whether this is a Road or Objective type Tile.
	 */
	boolean isWalkable() {
		return (_type.equals(Type.ROAD) || _type.equals(Type.OBJECTIVE));
	}
	
	/**
	 * @return Whether this is an Objective type Tile.
	 */
	boolean isObjective() {
		return _type.equals(Type.OBJECTIVE);
	}
	
	/**
	 * @param t The destination tile.
	 * @return The Euclidean distance between this tile and t.
	 */
	double distanceTo(Tile t) {
		return Math.sqrt( Math.pow((_x-t.getX()),2) + Math.pow((_y-t.getY()),2) );
	}

	
	///// Public methods /////
	public int compareTo(Tile t) {
		if (getCost() > t.getCost()) return 1;
		else if (getCost() < t.getCost()) return -1;
		else return 0;
	}
	
}
