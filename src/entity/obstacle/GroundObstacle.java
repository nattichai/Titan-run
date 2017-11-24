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
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 1; i < 4; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/obstacle/ground" + i + ".png").toString());
		}
	}

	public GroundObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(3) + 1;
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();
		hb = new Hitbox(40, Map.FLOOR_HEIGHT - height + 70, width - 90, height - 30);

		if (height < 200) {
			Jelly jelly = new Jelly(1000, -50, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1050, -75, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1150, -100, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1250, -75, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1300, -50, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
		} else {
			Jelly jelly = new Jelly(900, -80, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1000, -120, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1150, -160, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1300, -120, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
			jelly = new Jelly(1400, -80, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
			Model.getContainer().add(jelly);
		}

		draw();
	}

	public GroundObstacle() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height);
	}

}
