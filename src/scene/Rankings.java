package scene;

import java.util.List;

import game.model.Score;
import game.storage.ScoreData;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Rankings {
	private static final Font FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("fonts/SukhumvitSet.ttc"), 40);

	private static Canvas canvas;
	private static Pane rankingsPane;

	private static List<Score> data;

	public static void initialize() {
		data = ScoreData.getData();

		canvas = new Canvas(SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setGlobalAlpha(0.5);
		gc.fillRect(210, 95, 580, 560);

		gc.setFill(Color.WHITE);
		gc.setGlobalAlpha(1);
		gc.setTextBaseline(VPos.TOP);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.setFont(new Font(FONT.getName(), 75));
		gc.fillText("Rankings", 500, 100);
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.strokeText("Rankings", 500, 100);
		gc.strokeLine(230, 260, 770, 260);

		gc.setFont(new Font(FONT.getName(), 25));
		gc.setLineWidth(1);
		gc.strokeText("Rank", 275, 215);
		gc.strokeText("Name", 370, 215);
		gc.strokeText("Score", 640, 215);
		gc.strokeText("Mode", 722.5, 215);

		gc.setFont(new Font(FONT.getName(), 20));
		gc.fillText("Enter to go back", 500, 620);
		if (data != null) {
			for (int i = 0; i < 10 && i < data.size(); ++i) {
				String mode = "";
				if (data.get(i).mode == 0.5) {
					mode = "E";
				} else if (data.get(i).mode == 1.5) {
					mode = "N";
				} else if (data.get(i).mode == 3) {
					mode = "H";
				} else if (data.get(i).mode == 4) {
					mode = "I";
				}
				if (i % 2 == 0) {
					gc.setGlobalAlpha(0.1);
					gc.fillRect(230, 280 + i * 34, 540, 30);
				} else {
					gc.setFill(Color.BLACK);
					gc.setGlobalAlpha(0.5);
					gc.fillRect(230, 280 + i * 34, 540, 30);
				}
				
				gc.setFill(Color.WHITE);
				gc.setGlobalAlpha(0.9);
				gc.fillRoundRect(710, 283 + i * 34, 25, 25, 5, 5);
				
				gc.setStroke(Color.BLACK);
				gc.setGlobalAlpha(1);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.strokeText(mode, 722.5, 280 + i * 34);

				gc.setFill(Color.WHITE);
				gc.setGlobalAlpha(1);
				gc.fillText(i + 1 + "", 275, 280 + i * 34);

				gc.setTextAlign(TextAlignment.LEFT);
				gc.fillText(data.get(i).name, 340, 280 + i * 34);

				gc.setTextAlign(TextAlignment.RIGHT);
				gc.fillText("" + (int) data.get(i).score, 670, 280 + i * 34);
			}
		}

		rankingsPane = new Pane(canvas);
	}

	public static Pane getRankingsPane() {
		return rankingsPane;
	}
}
