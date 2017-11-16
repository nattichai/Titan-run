package entity.map;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Main;
import property.Movable;
import utility.Pair;

public class Obstacle extends Map implements Movable{
	
	double height;

	public Obstacle(Pair pos, Pair size) {
		super(pos, size);
		
		height = 100;
		
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Image obstacle = new Image(ClassLoader.getSystemResource("obstacle1.png").toString());
		gc.drawImage(obstacle, 1100, FLOOR_HEIGHT - height, 300, height);
//		gc.setFill(Color.BLACK);
//		gc.fillRect(1100, FLOOR_HEIGHT - height, 100, FLOOR_HEIGHT + height);
	}
	
	public void recycle() {
		if (position.first + Main.SCREEN_WIDTH + 250 <= 0) {
			position.first = 0;
			int rnd = new Random().nextInt(5) + 1;
			height = rnd * 50;
			draw();
		}
	}

}
