package edu.upenn.eas499.aimtd;

/**
 * A tower that shoots projectiles at passing monsters within range.
 * By default, towers lock onto Monsters until they leave the shooting range.
 * @author fedenusy
 *
 */
public abstract class Tower {
	
	// Instance variables
	private int x, y;
	private int fireDamage;
	private int fireSpeed;
	private int fireRange;
	
	// Public methods
	public Tower(int x, int y, int fireDamage, int firingRate, int fireRange) {
		this.x = x;
		this.y = y;
		this.fireDamage = fireDamage;
		this.fireSpeed = firingRate;
		this.fireRange = fireRange;
	}
	
	/**
	 * @param x The target's x position.
	 * @param y The target's y position.
	 * @return Whether this tower's projectiles reach the given coordinates.
	 */
	public boolean reaches(int x, int y) {
		return (fireRange >= Math.sqrt((this.x-x)^2-(this.y-y)^2));
	}
	
}
