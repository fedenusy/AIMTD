package test.edu.upenn.eas499.aimtd;

import edu.upenn.eas499.aimtd.Tower;

public class TowerImpl extends Tower {

	public TowerImpl(int x, int y, int fireDamage, int fireRate, int fireRange) {
		super(x, y, fireDamage, fireRate, fireRange);
	}

	@Override
	public TowerImpl clone() {
		return new TowerImpl(getX(), getY(), getFireDamage(), getFireRate(), getFireRange());
	}
}
