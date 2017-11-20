package entity.skill;

import entity.Entity;
import entity.characters.player.Player;
import property.Animatable;
import property.Movable;
import utility.Pair;

public abstract class Skill extends Entity implements Movable, Animatable{
	private Player player;
	protected double speedX, speedY;
	protected int currentAnimation;

	public Skill(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
	
	public abstract void draw();

	public void move() {
		positionX += speedX;
		positionY += speedY;
		
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}
	
	public void changeImage() {
		draw();
	}
	
	public abstract boolean isCollision(Pair pos);

	public abstract boolean isDead();

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
