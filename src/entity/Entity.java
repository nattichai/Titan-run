package entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import property.State;
import utility.*;

public abstract class Entity {
	protected Canvas canvas;
	protected Pair position;
	protected State state;
	
	public Entity(Pair pos, Pair size) {
		canvas = new Canvas(size.first, size.second);
		canvas.setTranslateX(pos.first);
		canvas.setTranslateY(pos.second);
		
		position = pos;
		
		draw();
	}
	
	public abstract void draw();
	
	public abstract void updatePosition();

	public Canvas getCanvas() {
		return canvas;
	}

	public Pair getPosition() {
		return position;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State s) {
		state = s;
	}
	
}
