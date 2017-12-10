package game.model.obstacle;

import java.util.Random;

import game.model.Map;
import game.model.Model;
import game.model.Obstacle;
import game.model.item.Jelly;
import game.property.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.SceneManager;

public class GroundObstacle extends Obstacle {
	private static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image("images/obstacle/ground" + i + ".png");
		}
	}

	public GroundObstacle() {
		super(SceneManager.SCREEN_WIDTH + 410, 0, OBSTACLE_WIDTH, SceneManager.SCREEN_HEIGHT);

		int rnd = new Random().nextInt(20);
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		if (height < 200) {
			width = 150;
			height = 150;
			hb = new Hitbox(40, 510, 70, 120);
			Jelly jelly = new Jelly(1225, 380);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1300, 320);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1450, 260);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1600, 320);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1675, 380);
			Model.getContainer().add(jelly);
		} else {
			width = 150;
			height = 275;
			hb = new Hitbox(40, 400, 60, 200);
			Jelly jelly = new Jelly(1200, 340);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1275, 240);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1450, 140);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1625, 240);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1700, 340);
			Model.getContainer().add(jelly);
		}

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height, width, height);
	}

}
