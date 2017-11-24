package entity.gui;

import entity.Entity;
import entity.characters.Characters;

public abstract class GUI extends Entity {

	public GUI(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public abstract void draw();

	public void affectTo(Characters e) {

	}

	public boolean isDead() {
		return false;
	}
}
