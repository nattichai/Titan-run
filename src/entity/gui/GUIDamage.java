package entity.gui;

import com.sun.javafx.tk.Toolkit;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GUIDamage extends GUIText {
	private static final Font FONT = Font
			.loadFont(ClassLoader.getSystemResourceAsStream("fonts/friz quadrata bold.otf"), 60);
	private static final Stop[][] stops = new Stop[3][3];
	static {
		// ORANGE
		stops[0] = new Stop[] { new Stop(0, Color.rgb(0xf4, 0xd8, 0x00)), new Stop(1, Color.rgb(0xe5, 0x34, 0x29)) };
		// PURPLE
		stops[1] = new Stop[] { new Stop(0, Color.rgb(0x69, 0x35, 0xc6)), new Stop(1, Color.rgb(0xe4, 0x24, 0xd2)) };
		// GREEN
		stops[2] = new Stop[] { new Stop(0, Color.rgb(0x41, 0x8a, 0x48)), new Stop(1, Color.rgb(0x50, 0xd2, 0x31)) };
	}

	private static final LinearGradient[] FONT_COLOR = new LinearGradient[3];
	static {
		// MONSTER GET DAMAGED
		FONT_COLOR[0] = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops[0]);
		// PLAYER GET DAMAGED
		FONT_COLOR[1] = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops[1]);
		// HEAL
		FONT_COLOR[2] = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops[2]);
	}

	public GUIDamage(double x, double y, String s, int i) {
		super(x, y, Toolkit.getToolkit().getFontLoader().computeStringWidth(s, FONT),
				Toolkit.getToolkit().getFontLoader().getFontMetrics(FONT).getLineHeight(), s, FONT_COLOR[i], FONT);

		FadeTransition ft = new FadeTransition(Duration.millis(1000), canvas);
		ft.setFromValue(1);
		ft.setToValue(0);
		TranslateTransition tt = new TranslateTransition(Duration.millis(1000), canvas);
		tt.setFromX(x);
		tt.setToX(x + 50);
		tt.setFromY(y);
		tt.setToY(y - 120);
		ft.play();
		tt.play();
	}

}
