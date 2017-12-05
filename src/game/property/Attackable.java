package game.property;

public interface Attackable {
	public abstract void increaseHp(double d);

	public abstract void decreaseHp(double d);

	public abstract void injured();

	public abstract void die();

	public abstract boolean isDead();
}
