package game.property;

public interface Movable {
	public abstract void changeSpeed(double accelX, double accelY);

	public abstract void move();

	public abstract void updatePosition();
}
