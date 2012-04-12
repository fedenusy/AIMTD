package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class responsible for Monster movement coordination.
 * @author fedenusy
 *
 */
public class Coordinator {

	///// Instance variables ////
	private Map _map;
	private ArrayList<Monster> _monsters;
	private ArrayList<Tower> _towers;
	
	
	///// Constructors /////
	/**
	 * Builds a Coordinator for making Monster movement decisions over the given Map.
	 * @param map The Map over which the coordinator should base its decisions.
	 */
	public Coordinator(Map map) {
		_map = map;
		_monsters = new ArrayList<Monster>();
		_towers = new ArrayList<Tower>();
	}

	
	///// Public methods /////
	/**
	 * Adds a Monster that will be guided by the Coordinator's tick() decisions.
	 * @param monster The Monster whose path will be guided by the Coordinator.
	 */
	public void addMonster(Monster monster) {
		if (!_monsters.contains(monster)) _monsters.add(monster);
	}
	
	/**
	 * Adds a Tower to be considered in the Coordinator's tick() decisions.
	 * @param tower A tower to be considered in each Monster's path decisions.
	 */
	public void addTower(Tower tower) {
		if (!_towers.contains(tower)) _towers.add(tower);
	}
	
	/**
	 * Removes a monster from the Coordinator's command.
	 * @param monster The monster to be removed.
	 */
	public void removeMonster(Monster monster) {
		_monsters.remove(monster);
	}
	
	/**
	 * Removes a tower from the Coordinator's consideration.
	 * @param tower The tower to be removed.
	 */
	public void removeTower(Tower tower) {
		_towers.remove(tower);
	}
	
	/**
	 * The collection of Monsters this Coordinator is actively manipulating (Monsters that are both
	 * alive and have not yet reached an objective).
	 * @return All monsters this Coordinator is manipulating.
	 */
	public Collection<Monster> getActiveMonsters() {
		ArrayList<Monster> actives = new ArrayList<Monster>();
		for (Monster monster : _monsters) {
			if (monster.isAlive() && !monster.reachedObjective()) actives.add(monster);
		}
		return actives;
	}
	
	/**
	 * The collection of all monsters assigned to this Coordinator.
	 * @return The collection of all monsters this Coordinator ever manipulated.
	 */
	public Collection<Monster> getAllMonsters() {
		return _monsters;
	}
	
	/**
	 * The collection of towers this Coordinator is aware of.
	 * @return All towers this Coordinator is aware of.
	 */
	public Collection<Tower> getTowers() {
		return _towers;
	}
	
	/**
	 * This method should be called each time the Coordinator has to make Monsters move. See Monster 
	 * and Tower classes for definitions of moveSpeed and fireSpeed, respectively, and their relationship 
	 * to tick().
	 */
	public void tick() {
		updateTilesDamage();
		
		for (Monster monster : _monsters) {
			if (monster.reachedObjective()) continue;
			monster.startNewTurn();
			int intelligenceLevel = monster.getIntelligenceLevel();
			if (intelligenceLevel == 1) shortestPathMove(monster);
			else if (intelligenceLevel == 2) survivalAwareMove(monster);
			else if (intelligenceLevel == 3) groupTacticMove(monster);
		}
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
					damage += tower.getFireDamage() * tower.getFireRate() / 100;
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
