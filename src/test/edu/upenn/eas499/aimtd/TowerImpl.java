package test.edu.upenn.eas499.aimtd;

import java.util.Collection;

import edu.upenn.eas499.aimtd.Monster;
import edu.upenn.eas499.aimtd.Tower;

public class TowerImpl extends Tower {

	///// Instance variables /////
	private int _movesLeft;
	private Monster _target;
	
	
	///// Constructors /////
	public TowerImpl(int x, int y, int fireDamage, int fireRate, int fireRange) {
		super(x, y, fireDamage, fireRate, fireRange);
		_movesLeft = 0;
	}
	
	
	///// Public methods /////
	/**
	 * Signals the beginning of a new turn, allowing the Tower to fire once again.
	 */
	public void newTurn() {
		_movesLeft += getFireRate();
	}
	
	/**
	 * Locks onto a Monster from a given collection and fires at it.
	 * @param monsters The collection of live monsters on the map.
	 */
	public void fire(Collection<Monster> monsters) {
		while (_movesLeft >= 100) {
			findTarget(monsters);
			if (_target == null) break;
			_target.damage(getFireDamage());
			if (!_target.isAlive()) _target = null;
			_movesLeft -= 100;
		}
	}
	
	@Override
	public TowerImpl clone() {
		return new TowerImpl(getX(), getY(), getFireDamage(), getFireRate(), getFireRange());
	}
	
	
	///// Private methods /////
	/**
	 * Finds a target Monster to lock onto, if one is found within range and no previously
	 * locked target is reachable.
	 * @param monsters The collection of monsters this tower could lock onto.
	 */
	private void findTarget(Collection<Monster> monsters) {
		if (_target!=null && this.reaches(_target.getX(), _target.getY())) return;
		
		for (Monster monster : monsters) {
			if (this.reaches(monster.getX(), monster.getY())) {
				_target = monster;
				return;
			}
		}
		
		_target = null;
		_movesLeft = 0;
	}
}
