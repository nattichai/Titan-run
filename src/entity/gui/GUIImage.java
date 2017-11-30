package entity.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GUIImage extends GUI {
	private Image image;

	public GUIImage(double x, double y, double w, double h, Image i) {
		super(x, y, w, h);

		image = i;
		width = image.getWidth();
		height = image.getHeight();

		draw();
	}

	public GUIImage(double x, double y, double w, double h, Image i, double width, double height) {
		super(x, y, w, h);

		image = i;
		this.width = width;
		this.height = height;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(image, (canvas.getWidth() - width) / 2, (canvas.getHeight() - height) / 2, width, height);
	}

}
