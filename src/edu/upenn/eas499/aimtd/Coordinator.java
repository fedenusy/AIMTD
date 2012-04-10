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
	private ArrayList<Monster> _monsters, _monstersUpdated;
	private ArrayList<Tower> _towers;
	private int _intelligenceLevel;
	
	
	///// Constructors /////
	/**
	 * 
	 * @param map The map over which the coordinator should base its decisions.
	 * @param intelligenceLevel The Coordinator's intelligence level, which defines Monster movement:<br/>
	 * Intelligence level 1: Each monster will move towards the closest objective.<br/>
	 * Intelligence level 2: Each monster will follow the path towards the objective that's least likely to get the monster killed.<br/>
	 * Intelligence level 3: An improvement on intelligence level 2, where monsters make group decisions to increase their likelihood
	 * of reaching their objectives.
	 */
	public Coordinator(Map map, int intelligenceLevel) {
		_map = map;
		_monsters = new ArrayList<Monster>();
		_towers = new ArrayList<Tower>();
		_intelligenceLevel = intelligenceLevel;
		
		if (_intelligenceLevel!=1 && _intelligenceLevel!=2 && _intelligenceLevel!=3) {
			System.err.println("AIMTD Error: invalid Coordinator inelligence level " + _intelligenceLevel
					+ "; defaulting to intelligence level 1.");
			_intelligenceLevel = 1;
		}
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
	 * This method should be called each time the Coordinator has to make Monsters move. See Monster 
	 * and Tower classes for definitions of moveSpeed and fireSpeed, respectively, and their relationship 
	 * to tick().
	 */
	public void tick() {
		updateTilesDamage();
		
		_monstersUpdated = new ArrayList<Monster>();
		_monstersUpdated.addAll(_monsters);
		
		for (Monster monster : _monsters) {
			if (monster.reachedObjective()) continue;
			monster.startNewTurn();
			if (_intelligenceLevel == 1) shortestPathMove(monster);
			else if (_intelligenceLevel == 2) survivalAwareMove(monster);
			else if (_intelligenceLevel == 3) groupTacticMove(monster);
		}
		
		_monsters = _monstersUpdated;
	}
	
	
	///// Private methods /////
	/**
	 * Updates how much damage a Monster standing on a Tile can receive within a tick.
	 */
	private void updateTilesDamage() {
		_map.resetTilesDamage();
		
		for (Tile tile : _map.getNodes()) {
			if (!tile.isWalkable()) continue;
			for (Tower tower : _towers) {
				if (tower.reaches(tile.getX(), tile.getY())) {
					double damage = tile.getDamageCost();
					damage += tower.getFireDamage() * tower.getFireSpeed() / 100;
					tile.setDamageCost(damage);
				}
			}
		}
	}
	
	/**
	 * Moves the Monster along the shortest path towards its objective, using an implementation of
	 * Dijkstra's algorithm to calculate the shortest path.
	 */
	private void shortestPathMove(Monster monster) {
		Pathfinder pathfinder = new Pathfinder(monster, _map, false);
		moveMonster(monster, pathfinder);
	}
	
	private void moveMonster(Monster monster, Pathfinder pathfinder) {
		while (monster.canMove()) {
			Tile nextTile = pathfinder.nextTile();
			monster.moveTowards(nextTile.getX(), nextTile.getY());
			if (nextTile.isObjective() && monster.canMove()) {
				monster.setReachedObjective(true);
				_monstersUpdated.remove(monster);
				break;
			}
		}
	}
	
	/**
	 * Considers each path towards the Monster's objective, weighing the total amount of Tower damage that
	 * would be sustained along each trail. Paths that are likely to get the Monster killed are discarded
	 * from consideration. The Monster is then moved along the shortest such remaining path.
	 * If all paths are likely to get the Monster killed, this method becomes analogous to shortestPathMove().
	 */
	private void survivalAwareMove(Monster monster) {
		Pathfinder pathfinder = new Pathfinder(monster, _map, true);
		moveMonster(monster, pathfinder);
	}
	
	/**
	 * Similar to survivalAwareMove(), except Monsters now take their team mates into consideration, along with
	 * their ability to draw Tower damage as they traverse a given path.
	 */
	private void groupTacticMove(Monster monster) {
		
	}
}
