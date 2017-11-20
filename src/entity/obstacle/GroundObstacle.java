package entity.obstacle;

import java.util.Random;

import entity.characters.player.Player;
import entity.item.Jelly;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import property.State;
import utility.Pair;

public class GroundObstacle extends Obstacle {

	public GroundObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(3) + 1;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/obstacle" + rnd + ".png").toString());
		height = obstacle.getHeight();
		
		if (height < 200) {
			Jelly jelly = new Jelly(950, -50, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
			jelly = new Jelly(1150, -100, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
			jelly = new Jelly(1350, -50,Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
		} else {
			Jelly jelly = new Jelly(950, -80,Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
			jelly = new Jelly(1150, -160,Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
			jelly = new Jelly(1350, -80,Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
			Container.getContainer().add(jelly);
		}

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height, OBSTACLE_WIDTH, height);
	}

	public boolean isCollision(Pair pos, State state) {
		if (		(positionX + 50 	<= pos.first + Player.PLAYER_WIDTH
										&& pos.first + Player.PLAYER_WIDTH	<= positionX + canvas.getWidth() - 50)	) {

			if (pos.second + Player.PLAYER_HEIGHT > Map.FLOOR_HEIGHT - height + 50) {
				return true;
			}
		}
		return false;
	}

}
