package edu.upenn.eas499.aimtd;

import java.util.ArrayList;

/**
 * Class responsible for monster movement coordination.
 * @author fedenusy
 *
 */
public class Coordinator {

	///// Instance variables ////
	private Map _map;
	private ArrayList<Monster> _monsters;
	private ArrayList<Tower> _towers;
	private int _intelligenceLevel;
	
	
	///// Constructors /////
	/**
	 * @param map The map over which the coordinator should base its decisions.
	 */
	public Coordinator(Map map, int intelligenceLevel) {
		_map = map;
		_monsters = new ArrayList<Monster>();
		_towers = new ArrayList<Tower>();
		_intelligenceLevel = intelligenceLevel;
		if (intelligenceLevel != 1) _intelligenceLevel = 1;
	}

	
	///// Public methods /////
	/**
	 * Adds a Monster that will be guided by the Coordinator's tick() decisions.
	 * @param monster
	 */
	public void addMonster(Monster monster) {
		_monsters.add(monster);
	}
	
	/**
	 * Adds a Tower to be considered in the Coordinator's tick() decisions.
	 * @param tower
	 */
	public void addTower(Tower tower) {
		_towers.add(tower);
	}
	
	/**
	 * This method should be called each time the Coordinator has to make Monsters move(). The
	 * Coordinator assumes Towers will fire their projectiles immediately after tick() is called.
	 * See Monster and Tower classes for definitions of moveSpeed and fireSpeed, respectively, and 
	 * their relationship to tick().
	 */
	public void tick() {
		if (_intelligenceLevel == 1) {
			for (Monster monster : _monsters) aStarMove(monster);
		}
	}
	
	
	///// Private methods /////
	private void aStarMove(Monster monster) {
		float xPos = monster.getX();
		float yPos = monster.getY();
		Tile start = _map.getTile(Math.round(xPos), Math.round(yPos));
	}
	
}
