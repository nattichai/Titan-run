package entity.obstacle;

import java.util.Random;

import entity.item.Jelly;
import entity.map.Map;
import entity.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import property.State;
import utility.Pair;

public class GroundObstacle extends Obstacle {

	public GroundObstacle(Pair pos, Pair size) {
		super(pos, size);

		int rnd = new Random().nextInt(3) + 1;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/obstacle" + rnd + ".png").toString());
		height = obstacle.getHeight();
		
		if (height < 200) {
			Jelly jelly = new Jelly(new Pair(1000, -50), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
			jelly = new Jelly(new Pair(1150, -100), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
			jelly = new Jelly(new Pair(1300, -50), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
		} else {
			Jelly jelly = new Jelly(new Pair(1000, -80), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
			jelly = new Jelly(new Pair(1150, -160), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
			jelly = new Jelly(new Pair(1300, -80), new Pair(Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(jelly);
		}

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height, OBSTACLE_WIDTH, height);
	}

	public boolean isCollision(Pair pos, State state) {
		if (		(position.first + 50 	<= pos.first + Player.PLAYER_WIDTH
										&& pos.first + Player.PLAYER_WIDTH	<= position.first + canvas.getWidth() - 50)	) {

			if (pos.second + Player.PLAYER_HEIGHT > Map.FLOOR_HEIGHT - height + 50) {
				return true;
			}
		}
		return false;
	}

}
