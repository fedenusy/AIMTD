package edu.upenn.eas499.aimtd;

/**
 * Monster that travels the TD map in order to reach an objective.
 * @author fedenusy
 *
 */
public abstract class Monster {

	// Instance variables
	private double x, y;
	private int hp;
	private int moveSpeed;
	
	// Public methods
	/**
	 * @param x The monster's initial x-position.
	 * @param y The monster's initial y-position.
	 * @param hp The monster's remaining total health points.
	 * @param moveSpeed The monster's movement speed. A speed of 10 means that the monster
	 * can move a Euclidean distance of 1 within a single tick. Thus, if the monster's
	 * initial position is (1,0), its moveSpeed is 20, and the Coordinator decides that
	 * the monster should move East, after calling Coordinator.tick() the Monster will be
	 * located at (3,0).
	 */
	public Monster(int x, int y, int hp, int moveSpeed) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.moveSpeed = moveSpeed;
	}
	
}
