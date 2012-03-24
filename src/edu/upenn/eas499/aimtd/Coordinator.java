package edu.upenn.eas499.aimtd;

import java.util.ArrayList;

/**
 * Class responsible for monster movement coordination.
 * @author fedenusy
 *
 */
public class Coordinator {

	// Instance variables
	private Map map;
	private ArrayList<Monster> monsters;
	private ArrayList<Tower> towers;
	private int intelligenceLevel;
	
	// Public methods
	/**
	 * @param map The map over which the coordinator should base its decisions.
	 */
	public Coordinator(Map map, int intelligenceLevel) {
		this.map = map;
		this.monsters = new ArrayList<Monster>();
		this.towers = new ArrayList<Tower>();
		this.intelligenceLevel = intelligenceLevel;
		if (intelligenceLevel != 1) this.intelligenceLevel = 1;
	}
	
	/**
	 * Adds a Monster that will be guided by the Coordinator's tick() decisions.
	 * @param monster
	 */
	public void addMonster(Monster monster) {
		monsters.add(monster);
	}
	
	/**
	 * Adds a Tower to be considered in the Coordinator's tick() decisions.
	 * @param tower
	 */
	public void addTower(Tower tower) {
		towers.add(tower);
	}
	
	/**
	 * This method should be called each time the Coordinator has to make Monsters move(). The
	 * Coordinator assumes Towers will fire their projectiles immediately after tick() is called.
	 * See Monster and Tower classes for definitions of moveSpeed and fireSpeed, respectively, and 
	 * their relationship to tick().
	 */
	public void tick() {
		if (intelligenceLevel == 1) {
			for (Monster monster : monsters) aStarMove(monster);
		}
	}
	
	// Private methods
	private void aStarMove(Monster monster) {
		double xPos = monster.getX();
		double yPos = monster.getY();
		Tile start = map.getTile((int) Math.round(xPos), (int) Math.round(yPos));
	}
	
}
