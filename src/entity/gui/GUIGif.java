package entity.gui;

import game.property.Animatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GUIGif extends GUI implements Animatable {
	private Image[] images;
	private int currentAnimation;

	public GUIGif(double x, double y, double w, double h, Image[] i) {
		super(x, y, w, h);

		currentAnimation = 0;
		images = i;
		width = i[0].getWidth();
		height = i[0].getHeight();

		draw();
	}

	public GUIGif(double x, double y, double w, double h, Image[] i, double width, double height) {
		super(x, y, w, h);

		currentAnimation = 0;
		images = i;
		this.width = width;
		this.height = height;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(images[currentAnimation], (canvas.getWidth() - width) / 2, (canvas.getHeight() - height) / 2,
				width, height);
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= images.length;
		draw();
	}

}
