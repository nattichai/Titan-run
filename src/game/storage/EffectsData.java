package game.storage;

import javafx.scene.image.Image;

public class EffectsData {
	private int nImage;
	private Image[] images;
	private double width, height;

	public static final EffectsData[] data = new EffectsData[10];
	static {
		EffectsData charge = new EffectsData();
		charge.nImage = 35;
		charge.images = new Image[charge.nImage];
		for (int i = 0; i < charge.nImage; ++i) {
			charge.images[i] = new Image("images/effects/charge" + i + ".png");
		}
		charge.width = 240;
		charge.height = 180;
		
		EffectsData levelUp = new EffectsData();
		levelUp.nImage = 19;
		levelUp.images = new Image[levelUp.nImage];
		for (int i = 0; i < levelUp.nImage; ++i) {
			levelUp.images[i] = new Image("images/effects/level up" + i + ".png");
		}
		levelUp.width = 577;
		levelUp.height = 495;

		data[0] = charge;
		data[1] = levelUp;
	}

	public int getnImage() {
		return nImage;
	}

	public Image[] getImages() {
		return images;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
