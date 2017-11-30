package entity.effect;

import javafx.scene.image.Image;

public class Charge extends Effect {
	private static final double EFFECT_WIDTH = 240;
	private static final double EFFECT_HEIGHT = 180;
	private static final Image[] charges = new Image[35];
	static {
		for (int i = 0; i < 35; ++i) {
			charges[i] = new Image("images/effects/charge" + i + ".png");
		}
	}

	public Charge(double x, double y, int r) {
		super(x, y, EFFECT_WIDTH, EFFECT_HEIGHT);

		round = r;
		nImage = 35;
		images = charges;
		width = EFFECT_WIDTH;
		height = EFFECT_HEIGHT;
	}

	public Charge() {
	}

}
