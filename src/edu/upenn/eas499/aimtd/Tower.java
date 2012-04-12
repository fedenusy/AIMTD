package edu.upenn.eas499.aimtd;

/**
 * A tower that shoots projectiles at passing monsters within range.
 * @author fedenusy
 *
 */
public abstract class Tower {
	
	///// Instance variables /////
	private int _x, _y;
	private int _fireDamage;
	private int _fireRate;
	private int _fireRange;
	
	
	///// Constructors /////
	/**
	 * @param x The tower's x-position.
	 * @param y The tower's y-position.
	 * @param fireDamage Damage from a single projectile, in terms of Monster hit points.
	 * @param firingRate Speed at which the tower fires. A speed of 100 means the tower can
	 * fire a single projectile within a call of Coordinator.tick(); a speed of 200 means the
	 * tower can fire off 2 projectiles within a single tick.
	 * @param fireRange The tower's firing radius.
	 */
	public Tower(int x, int y, int fireDamage, int fireRate, int fireRange) {
		_x = x;
		_y = y;
		_fireDamage = fireDamage;
		_fireRate = fireRate;
		_fireRange = fireRange;
	}
	
	
	///// Getter methods /////
	/**
	 * Gets this Tower's fire damage.
	 * @return This Tower's fire damage.
	 */
	public int getFireDamage() { return _fireDamage; }
	
	/**
	 * Gets this Tower's firing rate.
	 * @return This Tower's firing rate.
	 */
	public int getFireRate() { return _fireRate; }
	
	/**
	 * Gets this Tower's fire radius.
	 * @return This Tower's fire radius.
	 */
	public int getFireRange() { return _fireRange; }
	
	/**
	 * Gets this Tower's x-coordinate.
	 * @return This Tower's x-coordinate.
	 */
	public int getX() { return _x; }
	
	/**
	 * Gets this Tower's y-coordinate.
	 * @return This Tower's y-coordinate.
	 */
	public int getY() { return _y; }
	
	
	///// Public methods /////
	/**
	 * Determines whether this Tower reaches a given coordinate.
	 * @param x The target's x position.
	 * @param y The target's y position.
	 * @return Whether this tower's projectiles will reach the given coordinate.
	 */
	public boolean reaches(int x, int y) {
		double distance = Math.sqrt( Math.pow(_x-x,2) + Math.pow(_y-y, 2) );
		return _fireRange >= distance;
	}
	
	/**
	 * Determines whether this Tower reaches a given coordinate.
	 * @param x The target's x position.
	 * @param y The target's y position.
	 * @return Whether this tower's projectiles will reach the given coordinate.
	 */
	public boolean reaches(double x, double y) {
		double distance = Math.sqrt( Math.pow(_x-x,2) + Math.pow(_y-y, 2) );
		return _fireRange >= distance;
	}
}
