package edu.upenn.eas499.aimtd;

import java.util.ArrayList;

/**
 * Monster that travels the TD map in order to reach an objective.
 * @author fedenusy
 *
 */
public abstract class Monster {

	class Waypoint {
		private int _x, _y;
		Waypoint(int x, int y) { _x = x; _y = y; }
		public int getX() { return _x; }
		public int getY() { return _y; }
	}
	
	///// Instance variables /////
	private float _x, _y;
	private int _hp;
	private int _moveSpeed;
	private double _movesLeft;
	private boolean _reachedObjective;
	private ArrayList<Waypoint> _waypoints;
	
	
	///// Constructors /////
	/**
	 * @param x The monster's initial x-position.
	 * @param y The monster's initial y-position.
	 * @param hp The monster's remaining total health points.
	 * @param moveSpeed The monster's movement speed. A speed of 100 means that the monster
	 * can move a Euclidean distance of 1 within a single tick. Thus, if the monster's
	 * initial position is (1,0), its moveSpeed is 200, and the Coordinator decides that
	 * the monster should move East, after calling Coordinator.tick() the Monster will be
	 * located at (3,0).
	 */
	public Monster(int x, int y, int hp, int moveSpeed) {
		_x = x;
		_y = y;
		_hp = hp;
		_moveSpeed = moveSpeed;
		_reachedObjective = false;
		_waypoints = new ArrayList<Waypoint>();
	}
	
	
	///// Getter methods /////
	public float getX() { return _x; }
	public float getY() { return _y; }
	public int getHp() { return _hp; }
	public boolean reachedObjective() { return _reachedObjective; }
	public int getRoundedX() { return Math.round(_x); }
	public int getRoundedY() { return Math.round(_y); }
	
	
	///// Setter methods /////
	void setReachedObjective(boolean reachedObjective) { _reachedObjective = reachedObjective; }
	
	
	///// Public methods /////
	/**
	 * Adds a waypoint to the Monster's path. Monsters will try to reach waypoints in the order they were
	 * added before attempting to reach OBJECTIVE tiles.
	 * @param x The waypoint's x-coordinate.
	 * @param y The waypoint's y-coordinate.
	 */
	public void addWaypoint(int x, int y) {
		Waypoint wp = new Waypoint(x, y);
		_waypoints.add(wp);
	}
	
	
	///// Package-protected methods /////
	/**
	 * Measure of the Euclidean distance this monster can cover per tick.
	 * @return The monster's speed.
	 */
	double getSpeed() { 
		return _moveSpeed / 100.0;
	}
	
	/**
	 * Allows this Monster to move once again; method should be called before each new tick().
	 */
	void startNewTurn() {
		_movesLeft = _moveSpeed;
	}
	
	/**
	 * Gets the next Waypoint in the Monster's list, removing the Waypoint after it's been reached.
	 * @return The next Waypoint.
	 */
	Waypoint getWaypoint() {
		if (_waypoints.isEmpty()) return null;
		Waypoint wp = _waypoints.get(0);
		if (reachedCoords(wp.getX(), wp.getY())) {
			_waypoints.remove(0);
			wp = _waypoints.isEmpty() ? null : _waypoints.get(0);
		}
		return wp;
	}
	
	/**
	 * Whether this Monster can still move within this tick.
	 * @return Whether the monster can still move.
	 */
	boolean canMove() {
		return _movesLeft > 0;
	}

	/**
	 * Iteratively moves the Monster towards the specified coordinates.
	 * @param x The target x-coordinate.
	 * @param y The target y-coordinate.
	 */
	void moveTowards(int x, int y) {
		double yDirection = y - _y; //positive or negative
		double xDirection = x - _x; //positive or negative
		while (canMove() && !reachedCoords(x, y)) {
			if (!reachedY(y)) {
				if (yDirection > 0) _y += .005;
				else _y -= .005;
				_movesLeft -= .5;
			}
			if (!reachedX(x)) {
				if (xDirection > 0) _x += .005;
				else _x -= .005;
				_movesLeft -= .5;
			}
		}		
	}
	
	/**
	 * Removes a Waypoint from the monster's list of Waypoints.
	 * @param wp The Waypoint to be removed.
	 */
	void removeWaypoint(Waypoint wp) {
		_waypoints.remove(wp);
	}
	
	
	///// Private methods /////
	private boolean reachedX(int x) {
		return (x - _x) >= -.005 && (x - _x) <= .005;
	}
	
	private boolean reachedY(int y) {
		return (y - _y) >= -.005 && (y - _y) <= .005;
	}
	
	private boolean reachedCoords(int x, int y) {
		return reachedX(x) && reachedY(y);
	}
	
}
