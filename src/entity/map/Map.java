package entity.map;

import entity.Entity;
import entity.characters.Characters;
import game.property.Movable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.SceneManager;

public class Map extends Entity implements Movable {
	public static final double FLOOR_HEIGHT = 600;
	public static final double GRAVITY = 0.8;
	public static final double PASSIVE_DAMAGE = 0.01; // 0.045
	public static final double PASSIVE_SCORE = 0; // 1
	public static final double PASSIVE_MANA_REGEN = 0.1;
	private static final Image[] images = new Image[2];
	static {
		for (int i = 1; i < 3; ++i) {
			images[i - 1] = new Image("images/map/map" + i + ".jpg");
		}
	}

	protected double speedX, speedY;
	protected double width;

	public Map(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = -2;
		speedY = 0;

		draw();
	}

	public Map() {
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image image = images[1];
		width = SceneManager.SCREEN_WIDTH;

		gc.drawImage(image, 0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		gc.drawImage(image, SceneManager.SCREEN_WIDTH, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
	}

	public void changeSpeed(double accelX, double accelY) {
	}

	public void move() {
		positionX += speedX;
		positionY += speedY;

		if (positionX + width <= 0) {
			positionX = 0;
		}

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public void affectTo(Characters e) {

	}

	public boolean isDead() {
		return false;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
