package entity;

import entity.characters.Characters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import property.Hitbox;
import property.Side;

public abstract class Entity {
	protected Canvas canvas;
	protected double positionX, positionY;
	protected double width, height;
	protected Hitbox hb;
	protected boolean isCollided;
	protected Side side;
	
	public Entity(double x, double y, double w, double h) {
		canvas = new Canvas(w, h);
		canvas.setTranslateX(x);
		canvas.setTranslateY(y);
		positionX = x;
		positionY = y;
		hb = new Hitbox(0, 0, w, h);
		isCollided = false;
	}
	
	public Entity() {}
	
	public abstract void draw();
	
	public void drawHb() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(hb.x, hb.y, hb.w, hb.h);
	}
	
	public boolean isCollision(Entity e) {
		if (isCollided == true || side == e.side || (side == Side.NEUTRAL && e.side == Side.MONSTER))
			return false;
		if (positionX + hb.x < e.positionX + e.hb.x + e.hb.w && positionX + hb.x + hb.w > e.positionX + e.hb.x
				&& positionY + hb.y < e.positionY + e.hb.y + e.hb.h && positionY + hb.y + hb.h > e.positionY + e.hb.y == true) {
			isCollided = true;
			return true;
		}
		return false;
	}
	
	public abstract void affectTo(Characters e);
	
	public abstract boolean isDead();

	public Canvas getCanvas() {
		return canvas;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Hitbox getHb() {
		return hb;
	}

	public void setHb(Hitbox hb) {
		this.hb = hb;
	}

	public boolean isCollided() {
		return isCollided;
	}

	public void setCollided(boolean isCollided) {
		this.isCollided = isCollided;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
