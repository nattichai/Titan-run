package entity.characters.player;

import entity.characters.Characters;
import entity.map.Map;
import entity.textmodel.TextModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Container;
import main.Main;
import property.PowerState;
import property.Side;
import property.Slidable;
import property.State;
import utility.Hitbox;

public class Player extends Characters implements Slidable {
	public static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 10; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/character/character1_" + i + ".png").toString());
		}
	}
	public static final Image imageSlide = images[8];
	public static final double PLAYER_POSITON_X = 90;
	public static final double PLAYER_WIDTH = 120;
	public static final double PLAYER_HEIGHT = 200;
	public static final double LIMIT_NORMAL_ATTACK = 4;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;

	protected int jump;
	protected double score;
	protected Text scoreText;

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h);

		hb = new Hitbox(20, 20, PLAYER_WIDTH - 20, PLAYER_HEIGHT - 20);
		jump = 0;
		score = 0;
		scoreText = new Text("Score: " + score);
		scoreText.setFill(Color.WHITE);
		scoreText.setFont(new Font("Monospace", 40));
		scoreText.relocate(50, 50);
		hp = 100;
		maxHp = 100;
		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(200, 20);
		hpBar.setOpacity(0.3);
		atk = 10;
		side = Side.PLAYER;
		state = State.RUNNING;
		powerState = PowerState.NORMAL;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 10;
		gc.drawImage(images[currentAnimation++], 0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
	}

	public void move() {
		positionX += speedX;
		positionY += speedY;
		hpBar.relocate(positionX - 25, positionY - 50);

		if (positionY >= Map.FLOOR_HEIGHT - PLAYER_HEIGHT && state != State.SLIDING) {
			positionY = Map.FLOOR_HEIGHT - PLAYER_HEIGHT;
			speedY = 0;
			state = State.RUNNING;
		}
		updatePosition();
	}

	public void changeImage() {
		if (state == State.RUNNING) {
			draw();
		}
	}

	public void slide() {
		state = State.SLIDING;
		canvas.setRotate(-90);
		currentAnimation = 8;
		positionY = Map.FLOOR_HEIGHT - PLAYER_WIDTH;
		draw();
	}
	
	public void addScore(double s) {
		score += s;
		score *= 1.01;
		scoreText.setText("Score: " + changeUnit(score));
	}
	
	public String changeUnit(double d) {
		int count = 0;
		String number;
		String unit = "";
		while (d >= 1000) {
			d /= 1000;
			count ++;
		}
		number = String.format("%.2f ", d);
		if (count == 0) unit = "";
		else if (count == 1) unit = "K";
		else if (count == 2) unit = "M";
		else if (count == 3) unit = "B";
		else if (count == 4) unit = "T";
		else unit = ("" + (char)(97 + (count-5) / 26)) + ("" + (char)(97 + (count-5) % 26));
		return number + unit;
	}
	
	public void injured() {
		powerState = PowerState.IMMORTAL;
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 10), e -> {
			if (canvas.getOpacity() == 1)
				canvas.setOpacity(0.5);
			else
				canvas.setOpacity(1);
		}));
		timeline.setCycleCount(16);
		timeline.play();
		timeline.setOnFinished(e -> {
			powerState = PowerState.NORMAL;
		});
	}

	public void die() {
		Main.getTimerUpdate().stop();
		Main.getTimerAnimate().stop();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Main.FRAME_RATE), e -> {
			positionY += 10;
			canvas.setTranslateY(positionY);
			canvas.setOpacity(1 - ((positionY + PLAYER_HEIGHT - Map.FLOOR_HEIGHT) / (Main.SCREEN_HEIGHT - Map.FLOOR_HEIGHT)));
			canvas.setRotate(canvas.getRotate() + 15);
		}));
		timeline.setCycleCount(Main.FRAME_RATE / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			hp = 0.00001;
			Main.getTimerUpdate().play();
			Main.getTimerAnimate().play();
		});
		TextModel youDied = new TextModel(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT,
				new Image(ClassLoader.getSystemResource("images/text/you died.png").toString()), null);
		Container.getContainer().add(youDied);
	}

	public boolean isDead() {
		if (hp == 0.00001) {
			Container.getContainer().getPlayerPane().getChildren().removeAll(canvas, hpBar);
			return true;
		}
		return false;
	}

	public double getScore() {
		return score;
	}

	public Text getScoreText() {
		return scoreText;
	}

	public void setScoreText(Text scoreText) {
		this.scoreText = scoreText;
	}

	public int getJump() {
		return jump;
	}

	public void addJump(int jump) {
		this.jump += jump;
	}

}