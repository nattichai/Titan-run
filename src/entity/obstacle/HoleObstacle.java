package entity.obstacle;

import java.util.Random;

import entity.characters.Player;
import entity.item.Jelly;
import entity.map.Map;
import game.model.Model;
import game.property.Hitbox;
import game.property.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import window.SceneManager;

public class HoleObstacle extends Obstacle {
	public static final double HOLE_WIDTH = 300;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 1; i < 2; ++i) {
			images[i] = new Image("images/obstacle/hole" + i + ".png");
		}
	}

	public HoleObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(1) + 1;
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		hb = new Hitbox(150, Map.FLOOR_HEIGHT - 1, 40, 20);

		Jelly jelly = new Jelly(1100, -50, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1200, -100, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1300, -50, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
		Model.getContainer().add(jelly);

		draw();
	}

	public HoleObstacle() {
		super();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height / 2);
	}

	public boolean isCollision(Player e) {
		if (e.getState() != State.JUMPING)
			return true;
		return this.isCollision(e);
	}

}
