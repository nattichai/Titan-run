package entity.obstacle;

import java.util.Random;

import entity.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;
import utility.Pair;

public class AirObstacle extends Obstacle {
	public static final int AIR_HEIGHT = 500;

	public AirObstacle(Pair pos, Pair size) {
		super(pos, size);

		int rnd = new Random().nextInt(2) + 3;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/air" + rnd + ".png").toString());
		height = obstacle.getHeight();

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, AIR_HEIGHT - height);
	}

	public boolean isCollision(Pair pos, State state) {
		if (		position.first + 50 	<= pos.first + Player.PLAYER_WIDTH / 2
									&& pos.first + Player.PLAYER_WIDTH / 2 	<= position.first + canvas.getWidth() - 50 ) {

			if (state != State.SLIDING && pos.second + Player.PLAYER_HEIGHT > AIR_HEIGHT - height) {
				return true;
			}
		}
		return false;
	}

}
