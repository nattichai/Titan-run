package entity;

import javafx.scene.canvas.Canvas;
import property.PowerState;
import property.State;
import utility.Pair;

public abstract class Entity {
	protected Canvas canvas;
	protected Pair position;
	protected State state;
	protected PowerState powerState;
	
	public Entity(Pair pos, Pair size) {
		canvas = new Canvas(size.first, size.second);
		canvas.setTranslateX(pos.first);
		canvas.setTranslateY(pos.second);
		
		position = pos;
	}
	
	public abstract void draw();
	
	public abstract boolean isDead();

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Pair getPosition() {
		return position;
	}

	public void setPosition(Pair position) {
		this.position = position;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public PowerState getPowerState() {
		return powerState;
	}

	public void setPowerState(PowerState powerState) {
		this.powerState = powerState;
	}
	
	
	
}
