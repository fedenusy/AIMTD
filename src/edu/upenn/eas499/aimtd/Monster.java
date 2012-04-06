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
	private int _movesLeft;
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
	public int getSpeed() { return _moveSpeed; }
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
	void startNewTurn() {
		_movesLeft = _moveSpeed;
	}
	
	/**
	 * @return The next Waypoint, after removing it from the monster's list of Waypoints.
	 */
	Waypoint getWaypoint() {
		if (_waypoints.isEmpty()) return null;
		Waypoint wp = _waypoints.get(0);
		_waypoints.remove(0);
		return wp;
	}
	
	boolean canMove() {
		return _movesLeft > 0;
	}

	void moveTowards(int x, int y) {
		double moveY = y - _y;
		double moveX = x - _x;
		while (canMove() && !reachedTile(x, y)) {
			if (!reachedY(y)) {
				if (moveY > 0) _y += .005;
				else _y -= .005;
				_movesLeft -= .005;
			}
			if (!reachedX(x)) {
				if (moveX > 0) _x += .005;
				else _x -= .005;
				_movesLeft -= .005;
			}
		}
	}
	
	
	///// Private methods /////
	private boolean reachedX(int x) {
		return (x - _x) >= -.02 && (x - _x) <= .02;
	}
	
	private boolean reachedY(int y) {
		return (y - _y) >= -.02 && (y - _y) <= .02;
	}
	
	private boolean reachedTile(int x, int y) {
		return reachedX(x) && reachedY(y);
	}
	
}
