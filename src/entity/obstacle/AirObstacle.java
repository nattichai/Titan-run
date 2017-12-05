package entity.obstacle;

import java.util.Random;

import entity.characters.Player;
import game.property.Hitbox;
import game.property.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.SceneManager;

public class AirObstacle extends Obstacle {
	private static final int AIR_HEIGHT = 500;
	private static final Image[] images = new Image[4];
	static {
		for (int i = 1; i < 5; ++i) {
			images[i - 1] = new Image("images/obstacle/air" + i + ".png");
		}
	}

	public AirObstacle() {
		super(SceneManager.SCREEN_WIDTH + 210, 0, OBSTACLE_WIDTH, SceneManager.SCREEN_HEIGHT);

		int rnd = new Random().nextInt(4);
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		hb = new Hitbox(30, AIR_HEIGHT - height + 50, width - 60, height - 50);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, AIR_HEIGHT - height);
	}

	public boolean isCollision(Player e) {
		if (e.getState() != State.SLIDING)
			return true;
		return this.isCollision(e);
	}
}
