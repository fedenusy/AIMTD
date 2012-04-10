package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.PriorityQueue;

import edu.upenn.eas499.aimtd.Monster.Waypoint;

/**
 * Calculates the optimal path for a Monster using one of various methods.
 * @author fedenusy
 *
 */
class Pathfinder {

	///// Instance variables /////
	private Map _map;
	private Monster _monster;
	private ArrayList<Tile> _nodes;
	private Tile _currentNode;
	private boolean _survivalAware;
	
	///// Constructors /////
	Pathfinder(Monster monster, Map map, boolean survivalAware) {
		_monster = monster;
		_map = map;
		_nodes = new ArrayList<Tile>();
		_nodes.addAll(_map.getNodes());
		_survivalAware = survivalAware;
		
		calculateOptimalPath();
		checkOptimalPath();
	}
	
	///// Package-protected methods /////
	/**
	 * @return The next Tile that the Monster should move towards.
	 */
	Tile nextTile() {
		updateCurrentNode();
		Tile objective = getObjective();
		if (_currentNode.equals(objective)) return objective;
		
		Tile previous = objective.getPrevious();
		while (!_currentNode.equals(previous)) {
			objective = previous;
			previous = objective.getPrevious();
		}
		return objective;
	}
	
	
	///// Private methods /////
	private void calculateOptimalPath() {
		initializeNodes();
		
		PriorityQueue<Tile> nodeQueue = new PriorityQueue<Tile>();
		nodeQueue.addAll(_nodes);
		
		while (!nodeQueue.isEmpty()) {
			Tile node = nodeQueue.poll();
			if (node.getCost() == Double.MAX_VALUE) break;
			for (Tile neighbor : _map.getNeighbors(node)) {
				double altCost = getAltCost(node, neighbor);
				if (altCost < neighbor.getCost()) {
					nodeQueue.remove(neighbor);
					neighbor.setCost(altCost);
					neighbor.setPrevious(node);
					nodeQueue.add(neighbor);
				}
			}
		}
	}
	
	private void initializeNodes() {
		for (Tile node : _nodes) node.setPrevious(null);
		updateCurrentNode();
		_currentNode.setCost(0);
		for (Tile node : _nodes) {
			if (!node.equals(_currentNode)) node.setCost(Double.MAX_VALUE);
		}
	}
	
	private void updateCurrentNode() {
		int xPos = _monster.getRoundedX();
		int yPos = _monster.getRoundedY();
		
		_currentNode = _map.getTile(xPos, yPos);
		if (!validCurrentNode()) {
			if (xPos < _monster.getX()) xPos = (int) Math.ceil(_monster.getX());
			else xPos = (int) Math.floor(_monster.getX());
			_currentNode = _map.getTile(xPos, yPos);
		}
		if (!validCurrentNode()) {
			xPos = _monster.getRoundedX();
			if (yPos < _monster.getX()) yPos = (int) Math.ceil(_monster.getY());
			else yPos = (int) Math.floor(_monster.getY());
			_currentNode = _map.getTile(xPos, yPos);
		}
		
		if (!validCurrentNode()) 
			throw new IllegalArgumentException("Monster located on an invalid tile; coordinates (" + 
					_currentNode.getX() + "," + _currentNode.getY() + ")");
	}
	
	private boolean validCurrentNode() {
		return _currentNode != null && _currentNode.isWalkable();
	}
	
	private double getAltCost(Tile node, Tile neighbor) {
		if (!_survivalAware){
			double distanceCost = node.getCost();
			distanceCost += node.distanceTo(neighbor);
			return distanceCost;
		} else {
			double damageCost = node.getCost();
			damageCost += node.getDamageCost() * (node.distanceTo(neighbor) / _monster.getSpeed());
			return damageCost;
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
			else {
				waypointTile = null;
				_monster.removeWaypoint(wp);
			}
			wp = _monster.getWaypoint();
		}
		return waypointTile;
	}
	
	// Checks whether a survivalAware path was calculated when no towers are present; if so, the path is recalculated
	// using the shortest-path version
	private void checkOptimalPath() {
		if (!_survivalAware) return;
		
		boolean foundCostlyTile = false;
		for (Tile objective : _map.getObjectives()) {
			if (objective.getCost() > 0) foundCostlyTile = true;
		}

		if (!foundCostlyTile) {
			_survivalAware = false;
			calculateOptimalPath();
		}
	}
	
}
