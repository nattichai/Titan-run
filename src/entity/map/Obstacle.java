package entity.map;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Main;
import property.Movable;
import utility.Pair;

public class Obstacle extends Map implements Movable{
	
	private double height;

	public Obstacle(Pair pos, Pair size) {
		super(pos, size);
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Image obstacle = new Image(ClassLoader.getSystemResource("obstacle1.png").toString());
		gc.drawImage(obstacle, 1100, FLOOR_HEIGHT - height, 300, height);
	}
	
	public void recycle() {
		if (position.first + Main.SCREEN_WIDTH + 250 <= 0) {
			position.first = 0;
			int rnd = new Random().nextInt(5) + 3;
			height = rnd * 50;
			draw();
		}
	}

	public double getHeight() {
		return height;
	}

}
