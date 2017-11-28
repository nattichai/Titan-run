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
	public static final Stop[] stops = new Stop[] { new Stop(0, Color.rgb(0xf4, 0xd8, 0)),
			new Stop(1, Color.rgb(0xe5, 0x34, 0x29)) };
	public static final LinearGradient FONT_COLOR = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
	public static final Font FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("fonts/friz quadrata bold.otf"),
			60);

	public GUIDamage(double x, double y, String s) {
		super(x, y, Toolkit.getToolkit().getFontLoader().computeStringWidth(s, FONT),
				Toolkit.getToolkit().getFontLoader().getFontMetrics(FONT).getLineHeight(), s, FONT_COLOR, FONT);

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
