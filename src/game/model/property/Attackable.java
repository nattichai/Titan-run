package game.model.property;

public interface Attackable {
	public abstract void decreaseHp(double d);

	public abstract void die();

	public abstract boolean isDead();
}
