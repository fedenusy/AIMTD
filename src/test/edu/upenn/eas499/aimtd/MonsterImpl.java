package test.edu.upenn.eas499.aimtd;

import edu.upenn.eas499.aimtd.Monster;

public class MonsterImpl extends Monster {

	public MonsterImpl(int x, int y, int hp, int moveSpeed) {
		super(x, y, hp, moveSpeed);
	}

	@Override
	public MonsterImpl clone() {
		return new MonsterImpl(getRoundedX(), getRoundedY(), getHp(), getSpeed());
	}
}
