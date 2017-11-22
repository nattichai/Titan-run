package entity.characters.player;

import dataStorge.Container;
import dataStorge.PlayerData;
import dataStorge.Storage;
import entity.characters.Characters;
import entity.gui.GUI;
import entity.gui.GUIImage;
import entity.map.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.Main;
import property.Hitbox;
import property.PowerState;
import property.Side;
import property.Slidable;
import property.State;

public class Player extends Characters implements Slidable {
	public static final double LIMIT_NORMAL_ATTACK = 10;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;
	
	protected Image imageSlide;
	protected int jump;
	protected double mana, maxMana;
	protected ProgressBar manaBar;
	protected State state;
	
	private PlayerData playerData;

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h);

		Storage player = Storage.characters[1];
		if (player.side == Side.MONSTER) {
			player.side = Side.PLAYER;
			canvas.setScaleX(-1);
		}
		nImage = player.nImage;
		images = player.images;
		width = 120;
		height = 200;
		hb = new Hitbox(20, 20, 100, 180);
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = Map.GRAVITY;
		hp = player.hp;
		maxHp = player.maxHp;
		atk	= player.atk;
		side = player.side;
		powerState = player.powerState;
		
		jump = 0;
		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(200, 15);
		hpBar.setOpacity(0.8);
		mana = 100;
		maxMana = 100;
		manaBar = new ProgressBar(1);
		manaBar.setPrefSize(200, 7);
		manaBar.setOpacity(0.8);
		manaBar.setPadding(new Insets(0));
		state = State.RUNNING;
		
		playerData = new PlayerData();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= nImage;
		gc.drawImage(images[currentAnimation++], 0, 0, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		positionX += speedX;
		positionY += speedY;
		hpBar.relocate(positionX - 25, positionY - 50);
		manaBar.relocate(positionX - 25, positionY - 32);

		if (positionY >= Map.FLOOR_HEIGHT - height && state != State.SLIDING) {
			positionY = Map.FLOOR_HEIGHT - height;
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
		speedY = 0;
		currentAnimation = 8;
		positionY = Map.FLOOR_HEIGHT - width;
		draw();
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
			canvas.setOpacity(1 - ((positionY + height - Map.FLOOR_HEIGHT) / (Main.SCREEN_HEIGHT - Map.FLOOR_HEIGHT)));
			canvas.setRotate(canvas.getRotate() + 15);
		}));
		timeline.setCycleCount((int) Main.FRAME_RATE / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			hp = 0.00001;
			Main.getTimerUpdate().play();
			Main.getTimerAnimate().play();
		});
		GUI youAreDead = new GUIImage(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT,
				new Image(ClassLoader.getSystemResource("images/text/you died.png").toString()));
		Container.getContainer().add(youAreDead);
	}

	public boolean isDead() {
		if (hp == 0.00001) {
			Container.getContainer().getPlayerPane().getChildren().removeAll(canvas, hpBar, manaBar);
			return true;
		}
		return false;
	}

	public int getJump() {
		return jump;
	}

	public void addJump(int jump) {
		this.jump += jump;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}

	public ProgressBar getManaBar() {
		return manaBar;
	}

	public void setManaBar(ProgressBar manaBar) {
		this.manaBar = manaBar;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}
	
	public void addMana(double mana) {
		this.mana += mana;
		if (this.mana < 0) {
			this.mana = 0;
		} else if (this.mana > maxMana) {
			this.mana = maxMana;
		}
		manaBar.setProgress(this.mana / maxMana);
	}
}