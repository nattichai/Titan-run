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
		gc.fillText("Rank", 295, 215);
		gc.fillText("Name", 430, 215);
		gc.fillText("Score", 700, 215);
		gc.setLineWidth(1);
		gc.strokeText("Rank", 295, 215);
		gc.strokeText("Name", 430, 215);
		gc.strokeText("Score", 700, 215);

		gc.setFont(new Font(FONT.getName(), 20));
		gc.fillText("enter to go back", 500, 620);
		if (data != null) {
			for (int i = 0; i < 10 && i < data.size(); ++i) {
				if (i % 2 == 0) {
					gc.setGlobalAlpha(0.1);
					gc.fillRect(230, 280 + i * 34, 540, 30);
				} else {
					gc.setFill(Color.BLACK);
					gc.setGlobalAlpha(0.5);
					gc.fillRect(230, 280 + i * 34, 540, 30);
				}
	
				gc.setFill(Color.WHITE);
				gc.setGlobalAlpha(1);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.fillText(i + 1 + "", 295, 280 + i * 34);
	
				gc.setTextAlign(TextAlignment.LEFT);
				gc.fillText(data.get(i).name, 400, 280 + i * 34);
	
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.fillText("" + (int) data.get(i).score, 730, 280 + i * 34);
			}
		}

		rankingsPane = new Pane(canvas);
	}

	public static Pane getRankingsPane() {
		return rankingsPane;
	}
}
