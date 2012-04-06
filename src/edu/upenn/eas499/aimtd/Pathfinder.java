package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.PriorityQueue;

import edu.upenn.eas499.aimtd.Monster.Waypoint;

class Pathfinder {

	///// Instance variables /////
	private Map _map;
	private Monster _monster;
	private ArrayList<Tile> _nodes;
	private Tile _currentNode;
	
	
	///// Constructors /////
	Pathfinder(Monster monster, Map map, boolean survivalAware) {
		_monster = monster;
		_map = map;
		_nodes = new ArrayList<Tile>();
		_nodes.addAll(_map.getNodes());
		
		initializeNodes();
		if (!survivalAware) calculateDijkstra();
	}
	
	
	///// Package-protected methods /////
	/**
	 * @return The next Tile that the Monster should move towards.
	 */
	Tile nextTile() {
		updateCurrentNode();
		Tile objective = getObjective();
		if (_currentNode.equals(objective)) return null;
		
		Tile previous = objective.getPrevious();
		while (!_currentNode.equals(previous)) {
			objective = previous;
			previous = objective.getPrevious();
		}
		return objective;
	}
	
	void clearTiles() {
		for (Tile node : _nodes) node.setPrevious(null);
	}
	
	
	///// Private methods /////
	private void initializeNodes() {
		updateCurrentNode();
		if (_currentNode == null) {
			String exception = "Monster is located on an invalid map coordinate: ";
			exception += "(" + _monster.getRoundedX() + ", " + _monster.getRoundedY() + ")";
			throw new IllegalArgumentException(exception);
		}
		for (Tile node : _nodes) {
			if (!node.equals(_currentNode)) node.setCost(Double.MAX_VALUE);
		}
	}
	
	private void updateCurrentNode() {
		int xPos = _monster.getRoundedX();
		int yPos = _monster.getRoundedY();
		for (Tile node : _nodes) {
			if (node.getX() == xPos && node.getY() == yPos)
				_currentNode = node;
		}
		_currentNode.setCost(0);
	}
	
	private void calculateDijkstra() {
		PriorityQueue<Tile> nodeQueue = new PriorityQueue<Tile>();
		nodeQueue.addAll(_nodes);
		
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
	
	// Returns the Monster's next objective, or null if no objective is reachable
	private Tile getObjective() {
		Tile objective = getWaypointTile();
		if (objective != null) return objective;
		for (Tile tile : _map.getObjectives()) {
			if (objective == null || objective.getCost() > tile.getCost()) objective = tile;
		}
		if (objective.getCost() != Double.MAX_VALUE) return objective;
		else throw new IllegalArgumentException("Monster has no reachable objective");
	}
	
	// Returns the Tile corresponding to the next Waypoint in the Monster's list, if the Waypoint is reachable
	private Tile getWaypointTile() {
		Tile waypointTile = null;
		Waypoint wp = _monster.getWaypoint();
		while (wp != null) {
			waypointTile = _map.getTile(wp.getX(), wp.getY());
			if (waypointTile != null && waypointTile.getCost() != Double.MAX_VALUE) break;
			wp = _monster.getWaypoint();
		}
		return waypointTile;
	}
	
}
