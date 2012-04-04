package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

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
		for (Monster monster : _monsters) {
			if (_intelligenceLevel == 1) shortestPathMove(monster);
			else if (_intelligenceLevel == 2) survivalAwareMove(monster);
			else if (_intelligenceLevel == 3) groupTacticMove(monster);
		}
	}
	
	
	///// Private methods /////
	/**
	 * Moves the Monster along the shortest path towards its objective, using an implementation of
	 * Dijkstra's algorithm. 
	 */
	private void shortestPathMove(Monster monster) {
		Collection<Tile> nodes = initializeNodes(monster);
		PriorityQueue<Tile> nodeQueue = new PriorityQueue<Tile>();
		nodeQueue.addAll(nodes);
		
		while (!nodeQueue.isEmpty()) {
			Tile node = nodeQueue.poll();
			if (node.getCost() == Double.MAX_VALUE) break;
			for (Tile neighbor : _map.getNeighbors(node)) {
				double altCost = node.getCost() + node.distanceTo(neighbor);
				if (altCost < neighbor.getCost()) {
					nodeQueue.remove(neighbor);
					neighbor.setCost(altCost);
					neighbor.setPrevious(node);
					nodeQueue.add(neighbor);
				}
			}
		}
	}
	
	private Collection<Tile> initializeNodes(Monster monster) {
		Collection<Tile> nodes = _map.getNodes();
		for (Tile node : nodes) node.setCost(Double.MAX_VALUE);
		Tile source = getSource(monster);
		nodes.remove(source);
		source.setCost(0);
		nodes.add(source);
		return nodes;
	}
	
	private Tile getSource(Monster monster) {
		int xPos = monster.getRoundedX();
		int yPos = monster.getRoundedY();
		return _map.getTile(xPos, yPos);
	}
	
	/**
	 * Considers each path towards the Monster's objective, weighing the total amount of Tower damage that
	 * would be sustained along each trail. Paths that are likely to get the Monster killed are discarded
	 * from consideration. The Monster is then moved along the shortest such remaining path.
	 * If all paths are likely to get the Monster killed, this method becomes analogous to shortestPathMove().
	 */
	private void survivalAwareMove(Monster monster) {
		
	}
	
	/**
	 * Similar to survivalAwareMove(), except Monsters now take their team mates into consideration, along with
	 * their ability to draw Tower damage as they traverse a given path.
	 */
	private void groupTacticMove(Monster monster) {
		
	}
}
