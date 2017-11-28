package entity.obstacle;

import java.util.Random;

import entity.item.Jelly;
import entity.map.Map;
import game.model.Model;
import game.property.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import window.SceneManager;

public class GroundObstacle extends Obstacle {
	public static final Image[] images = new Image[89];
	static {
		for (int i = 0; i < 89; ++i) {
			images[i] = new Image("images/obstacle/ground" + i + ".png");
		}
	}

	public GroundObstacle() {
		super(SceneManager.SCREEN_WIDTH + 210, 0, OBSTACLE_WIDTH, SceneManager.SCREEN_HEIGHT);

		int rnd = new Random().nextInt(17) + 72;
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		if (height < 200) {
			hb = new Hitbox(40, 510, 120, 120);
			Jelly jelly = new Jelly(1025, 400);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1100, 350);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1250, 300);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1400, 350);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1475, 400);
			Model.getContainer().add(jelly);
		} else {
			hb = new Hitbox(40, 420, 60, 220);
			Jelly jelly = new Jelly(1000, 390);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1075, 290);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1250, 190);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1425, 290);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1500, 390);
			Model.getContainer().add(jelly);
		}

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height);
	}

}
