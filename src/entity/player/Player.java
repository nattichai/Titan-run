package entity.player;

import entity.Entity;
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
import property.Animatable;
import property.Movable;
import property.PowerState;
import property.Slidable;
import property.State;
import utility.Pair;

public class Player extends Entity implements Animatable, Movable, Slidable {
	public static final Image[] images = new Image[20];
	public static final Image imageSlide = new Image(
			ClassLoader.getSystemResource("images/character/player1_walk9.png").toString());
	public static final double PLAYER_POSITON_X = 90;
	public static final double PLAYER_WIDTH = 120;
	public static final double PLAYER_HEIGHT = 200;
	public static final double LIMIT_NORMAL_ATTACK = 10;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;
	static {
		for (int i = 1; i <= 10; ++i) {
			images[i - 1] = new Image(
					ClassLoader.getSystemResource("images/character/player1_walk" + i + ".png").toString());
		}
	}

	protected double speedX, speedY;
	protected int currentAnimation;
	protected int jump;
	protected double hp, maxHp;
	protected ProgressBar hpBar;
	protected double score;
	protected Text scoreText;

	public Player(Pair pos, Pair size) {
		super(pos, size);

		speedX = 0;
		speedY = 0;
		currentAnimation = 0;
		jump = 0;
		hp = 100;
		maxHp = 100;
		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(300, 30);
		hpBar.setTranslateX(20);
		hpBar.setTranslateY(20);
		hpBar.setOpacity(0.8);
		score = 0;
		scoreText = new Text("Score: " + score);
		scoreText.setFill(Color.WHITE);
		scoreText.setFont(new Font("Monospace", 40));
		scoreText.relocate(580, 10);

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

	public void changeSpeed(double accelX, double accelY) {
		speedX += accelX;
		speedY += accelY;
	}

	public void move() {
		position.first += speedX;
		position.second += speedY;

		if (position.second >= Map.FLOOR_HEIGHT - PLAYER_HEIGHT && state != State.SLIDING) {
			position.second = Map.FLOOR_HEIGHT - PLAYER_HEIGHT;
			speedY = 0;
			state = State.RUNNING;
		}
		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(position.first);
		canvas.setTranslateY(position.second);
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
		position.second = Map.FLOOR_HEIGHT - PLAYER_WIDTH;
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
		else unit = ("" + (char)(97 + (count-5) / 26)) + ("" + (char)(97 + (count-5)));
		return number + unit;
	}
	

	public void decreaseHp(double d) {
		if (hp == 0.00001) {
			return;
		}
		hp -= d;
		if (hp <= 0) {
			die();
			return;
		}
		hpBar.setProgress(hp / maxHp);

		if (d >= 10) {
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
	}

	public void die() {
		Main.getTimerUpdate().stop();
		Main.getTimerAnimate().stop();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Main.FRAME_RATE), e -> {
			position.second += 5;
			canvas.setTranslateY(position.second);
			canvas.setOpacity(1 - ((position.second + PLAYER_HEIGHT - Map.FLOOR_HEIGHT) / (Main.SCREEN_HEIGHT - Map.FLOOR_HEIGHT)));
		}));
		timeline.setCycleCount(Main.FRAME_RATE / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			hp = 0.00001;
			Main.getTimerUpdate().play();
			Main.getTimerAnimate().play();
		});
		TextModel youDied = new TextModel(new Pair(0, 0), new Pair(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT),
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

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public ProgressBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(ProgressBar hpBar) {
		this.hpBar = hpBar;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
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