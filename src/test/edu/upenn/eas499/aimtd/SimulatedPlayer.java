package test.edu.upenn.eas499.aimtd;

import java.util.ArrayList;

import edu.upenn.eas499.aimtd.Coordinator;
import edu.upenn.eas499.aimtd.Map;
import edu.upenn.eas499.aimtd.Monster;
import edu.upenn.eas499.aimtd.Tile;

public class SimulatedPlayer {

	///// Instance variables /////
	private Coordinator _coordinator;
	private Map _map;
	private ArrayList<TowerImpl> _towers;
	private int _lives;
	private int _resources;
	private int _towerCost;
	private int _towerDamage;
	private int _towerSpeed;
	private int _towerRange;
	
	
	///// Constructors /////
	public SimulatedPlayer(Coordinator coordinator, Map map) {
		_coordinator = coordinator;
		_map = map;
		_towers = new ArrayList<TowerImpl>();
		
		_lives = 100;
		_resources = 1000;
		
		_towerCost = 250;
		_towerDamage = 15;
		_towerSpeed = 100;
		_towerRange = 2;
		
		buildTower(4, 3);
		buildTower(17, 1);
		buildTower(8, 10);
		buildTower(17, 14);
	}
	
	
	///// Public methods /////
	/**
	 * Randomly places towers until lives or resources run short.
	 */
	public void tick() {
		for (TowerImpl tower : _towers) {
			tower.newTurn();
			tower.fire(_coordinator.getActiveMonsters());
		}
		
		ArrayList<Monster> reachedObjective = new ArrayList<Monster>();
		ArrayList<Monster> dead = new ArrayList<Monster>();
		for (Monster monster : _coordinator.getAllMonsters()) {
			if (monster.reachedObjective() && monster.isAlive()) {
				_lives -= 1;
				reachedObjective.add(monster);
			} else if (!monster.isAlive()) {
				dead.add(monster);
				_resources += 125;
			}
		}
		for (Monster monster : reachedObjective) _coordinator.removeMonster(monster);
		for (Monster monster : dead) _coordinator.removeMonster(monster);
		
		while (hasLives() && _resources >= _towerCost) {
			Tile candidate = _map.getRandomFieldTile();
			boolean validCandidate = true;
			for (TowerImpl tower : _towers) {
				if (tower.getX()==candidate.getX() && tower.getY()==candidate.getY())
					validCandidate = false;
			}
			if (validCandidate) buildTower(candidate.getX(), candidate.getY());
		}
		
		
	}
	
	/**
	 * Whether this player has any lives left.
	 * @return Whether this player has any lives left.
	 */
	public boolean hasLives() {
		return _lives > 0;
	}
	
	/**
	 * How many lives this player has left.
	 * @return The number of lives this player has left.
	 */
	public int getLives() {
		return _lives;
	}
	
	///// Private methods /////
	/**
	 * Adds a tower at the specified coordinates and reduces player resources by the appropriate amount.
	 * @param x The tower's x coordinate.
	 * @param y The tower's y coordinate.
	 */
	private void buildTower(int x, int y) {
		if (_resources < _towerCost) return;
		_resources -= _towerCost;
		TowerImpl tower = new TowerImpl(x, y, _towerDamage, _towerSpeed, _towerRange);
		_towers.add(tower);
		_coordinator.addTower(tower);
	}
	
}
