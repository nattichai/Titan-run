package game.model.entity.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import window.SceneManager;

public class GUIImage extends GUI {
	Image image;

	public GUIImage(double x, double y, double w, double h, Image i) {
		super(x, y, w, h);

		image = i;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(image, (SceneManager.SCREEN_WIDTH - image.getWidth()) / 2,
				(SceneManager.SCREEN_HEIGHT - image.getHeight()) / 2);
	}

}
