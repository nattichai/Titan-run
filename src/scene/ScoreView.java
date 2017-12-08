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

public class ScoreView {
	private static final Font FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("fonts/SukhumvitSet.ttc"), 40);

	private static Canvas canvas;
	private static Pane scoreViewPane;

	private static List<Score> data;

	private static String playerName;
	private static double playerScore;

	public static void initialize() {
		int rank = getRank();

		canvas = new Canvas(SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setGlobalAlpha(0.5);
		gc.fillRect(165, 140, 670, 470);
		gc.fillRect(510, 260, 290, 275);

		gc.setFill(Color.WHITE);
		if (rank < 5) {
			gc.setGlobalAlpha(0.2);
			gc.fillRect(520, 284 + rank * 40, 269, 33);
		}

		gc.setGlobalAlpha(1);
		gc.setTextBaseline(VPos.TOP);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.setFont(new Font(FONT.getName(), 60));
		gc.fillText(playerName, 500, 145, 550);

		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.strokeText(playerName, 500, 145, 550);
		gc.strokeLine(200, 235, 798, 235);

		gc.setFont(new Font(FONT.getName(), 25));
		gc.fillText("Score", 337, 334);
		gc.fillText("Enter to continue", 500, 557);

		gc.setFont(new Font(FONT.getName(), 30));
		gc.fillText("Top runner", 655, 270);
		gc.setLineWidth(1);
		gc.strokeText("Top runner", 655, 270);
		if (rank > 5) {
			gc.fillText("unranked", 337, 445);
		} else {
			gc.fillText("rank #" + rank, 337, 445);
		}

		gc.setFont(new Font(FONT.getName(), 75));
		gc.fillText("" + (int) playerScore, 337, 345);

		gc.setFont(new Font(FONT.getName(), 20));
		for (int i = 0; i < 5 && i < data.size(); ++i) {
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText(data.get(i).name, 530, 325 + i * 40, 150);

			gc.setTextAlign(TextAlignment.RIGHT);
			gc.fillText("" + (int) data.get(i).score, 785, 325 + i * 40, 100);
		}

		scoreViewPane = new Pane();
		scoreViewPane.getChildren().addAll(canvas);
	}

	private static int getRank() {
		data = ScoreData.getData();
		if (data == null) {
			return 0;
		}

		int rank = 1;
		for (Score s : data) {
			if (playerName.equals(s.name) && playerScore == s.score) {
				return rank;
			}
			rank++;
		}
		return 0;
	}

	public static Pane getScoreViewPane() {
		return scoreViewPane;
	}

	public static void setPlayer(String name, double score) {
		playerName = name;
		playerScore = score;
		ScoreData.addData(name, score);
	}
}
