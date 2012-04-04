package edu.upenn.eas499.aimtd;

/**
 * Monster that travels the TD map in order to reach an objective.
 * @author fedenusy
 *
 */
public abstract class Monster {

	///// Instance variables /////
	private float _x, _y;
	private int _hp;
	private int _moveSpeed;
	private Tile objective;
	
	
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
	}
	
	
	///// Getter methods /////
	public float getX() { return _x; }
	public float getY() { return _y; }
	public int getHp() { return _hp; }
	public int getSpeed() { return _moveSpeed; }
	public int getRoundedX() { return Math.round(_x); }
	public int getRoundedY() { return Math.round(_y); }
	
	
	///// Public methods /////

	
	
	///// Final methods /////
	protected final void move(float x, float y) {
		_x = x;
		_y = y;
	}
	
}
