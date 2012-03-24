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
	public Monster(int x, int y, int hp, int moveSpeed) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.moveSpeed = moveSpeed;
	}
	
}
