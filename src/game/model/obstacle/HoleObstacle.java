package game.model.obstacle;

import java.util.Random;

import game.model.Map;
import game.model.Model;
import game.model.Obstacle;
import game.model.character.Player;
import game.model.item.Jelly;
import game.property.Hitbox;
import game.property.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.SceneManager;

public class HoleObstacle extends Obstacle {
	private static final double HOLE_WIDTH = 300;
	private static final Image[] images = new Image[1];
	static {
		for (int i = 1; i < 2; ++i) {
			images[i - 1] = new Image("images/obstacle/hole" + i + ".png");
		}
	}

	public HoleObstacle() {
		super(SceneManager.SCREEN_WIDTH + 180, 0, HOLE_WIDTH, SceneManager.SCREEN_HEIGHT);

		int rnd = new Random().nextInt(1);
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		hb = new Hitbox(150, Map.FLOOR_HEIGHT - 1, 40, 20);

		Jelly jelly = new Jelly(1100, 390);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1175, 340);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1300, 290);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1425, 340);
		Model.getContainer().add(jelly);
		jelly = new Jelly(1500, 390);
		Model.getContainer().add(jelly);

		draw();
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
