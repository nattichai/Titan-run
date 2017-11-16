package entity.player;

import javafx.animation.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import main.Main;
import property.*;
import utility.*;
import entity.*;
import entity.map.Map;

public class Player extends Entity implements Animatable, Jumpable, Slidable {
	public static final double PLAYER_XPOS = 150;
	public static final double PLAYER_WIDTH = 120;
	public static final double PLAYER_HEIGHT = 200;
	public static final Image[] images = new Image[20];
	public static final Image imageSlide = new Image(ClassLoader.getSystemResource("player1_walk9.png").toString());
	static {
		for (int i = 1; i <= 10; ++i) {
			images[i - 1] = new Image(ClassLoader.getSystemResource("player1_walk" + i + ".png").toString());
		}
	}
	
	private GraphicsContext gc;
	private ProgressBar hpBar;
	
	private double ySpeed;
	private int currentAnimation;
	private double hp, maxHp;

	public Player(Pair pos, Pair size) {
		super(pos, size);
		
		currentAnimation = 0;
		state = State.RUNNING;
		hp = 100;
		maxHp = 100;
		
		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(300, 30);
		hpBar.setTranslateX(20);
		hpBar.setTranslateY(20);
		hpBar.setOpacity(0.8);
	}
	
	public ProgressBar getHpBar() {
		return hpBar;
	}
	
	public void decreaseHp(double d) {
		hp -= d;
		hpBar.setProgress(hp / maxHp);
		
		if (hp <= 0) {
			die();
		}
	}
	
	public void die() {
		
		Timeline timeline = new Timeline (new KeyFrame(Duration.millis(1), e -> {
			canvas.setRotate(90);
			currentAnimation = 8;
			position.second = Map.FLOOR_HEIGHT - PLAYER_WIDTH;
			updatePosition();
			draw();
		}));
		timeline.setCycleCount(1);
		timeline.play();
		timeline.setOnFinished(e -> {
			Main.getTimerUpdate().stop();
			Main.getTimerAnimate().stop();
			new Alert(AlertType.NONE, "YOU DIED!!", ButtonType.OK).showAndWait();
		});
	}

	public void draw() {
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 10;
		gc.drawImage(images[currentAnimation ++], 0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
	}
	
	public void changeSpeed(double accel) {
		ySpeed += accel;
	}

	public void moveY() {
		position.second += ySpeed;
		
		if (position.second >= Map.FLOOR_HEIGHT - PLAYER_HEIGHT && state != State.SLIDING) {
			position.second = Map.FLOOR_HEIGHT - PLAYER_HEIGHT;
			ySpeed = 0;
			state = State.RUNNING;
		}
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateY(position.second);
	}

	public double getYSpeed() {
		return ySpeed;
	}
	
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
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

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
	
}