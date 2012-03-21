package edu.upenn.eas499.aimtd;

/**
 * Monster that travels the TD map in order to reach an objective.
 * @author fedenusy
 *
 */
public class Monster {

	// Instance variables
	private int x, y;
	private int hp;
	private int speed;
	
	public Monster(int x, int y, int hp, int speed) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.speed = speed;
	}
	
}
