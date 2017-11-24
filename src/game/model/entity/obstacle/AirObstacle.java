package game.model.entity.obstacle;

import java.util.Random;

import game.model.entity.characters.player.Player;
import game.model.property.Hitbox;
import game.model.property.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AirObstacle extends Obstacle {
	public static final int AIR_HEIGHT = 500;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 1; i < 5; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/obstacle/air" + i + ".png").toString());
		}
	}

	public AirObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(4) + 1;
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		hb = new Hitbox(30, AIR_HEIGHT - height + 50, width - 60, height - 50);

		draw();
	}

	public AirObstacle() {
		// TODO Auto-generated constructor stub
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
