package entity.obstacle;

import java.util.Random;

import entity.map.Map;
import entity.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;
import utility.Pair;

public class HoleObstacle extends Obstacle {

	public static final double HOLE_WIDTH = 300;

	public HoleObstacle(Pair pos, Pair size) {
		super(pos, size);

		int rnd = new Random().nextInt(1) + 1;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/hole" + rnd + ".png").toString());
		height = obstacle.getHeight();

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height / 2);
	}
	
	public boolean isCollision(Pair pos, State state) {
		if (		(position.first + 120 	<= pos.first + Player.PLAYER_WIDTH / 2
										&& pos.first + Player.PLAYER_WIDTH / 2 	<= position.first + canvas.getWidth() - 120) 
			|| 	(position.first + 120 	<= pos.first
										&& pos.first 							<= position.first + canvas.getWidth() - 120)	) {
			
			if (state != State.JUMPING) {
				return true;
			}
		}
		return false;
	}

}
