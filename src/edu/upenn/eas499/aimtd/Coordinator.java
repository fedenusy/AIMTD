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
	
	// Public methods
	/**
	 * @param map The map over which the coordinator should base its decisions.
	 */
	public Coordinator(Map map) {
		this.map = map;
		this.monsters = new ArrayList<Monster>();
		this.towers = new ArrayList<Tower>();
	}
	
	/**
	 * Adds a Monster that will be guided by the Coordinator.
	 * @param monster
	 */
	public void addMonster(Monster monster) {
		monsters.add(monster);
	}
	
	/**
	 * Adds a Tower whose projectiles will be considered in the Coordinator's decisions.
	 * @param tower
	 */
	public void addTower(Tower tower) {
		towers.add(tower);
	}
	
	/**
	 * This method should be called each time the Coordinator has to make Monsters move(). The
	 * Coordinator assumes Towers will fire their projectiles immediately after tick() is called.
	 * See Monster and Tower for field definitions of speed and firingSpeed, respectively, and their
	 * relationship to tick().
	 */
	public void tick() {
		
	}
	
}
