package entity.skill;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Container;
import main.Main;
import property.Animatable;
import property.Movable;
import utility.Pair;

public class Fireball extends Entity implements Movable, Animatable{
	
	public static double SKILL_WIDTH = 50;
	public static double SKILL_HEIGHT = 30;
	
	public static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/fireball/Fireball" + i + ".png").toString());
		}
	}
	
	private GraphicsContext gc;
	private double xSpeed;
	private int currentAnimation;
	
	public Fireball(Pair pos, Pair size) {
		super(pos, size);
		
		xSpeed = 20;
		currentAnimation = 5;
	}

	public void changeImage() {
		draw();
	}

	public void draw() {
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 15;
		currentAnimation += 5;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public void moveX() {
		position.first += xSpeed;
		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(position.first);
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

}
