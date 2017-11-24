package entity.characters;

import entity.gui.GUI;
import entity.gui.GUIImage;
import entity.map.Map;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Shield;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import game.animations.Animations;
import game.model.Model;
import game.property.Hitbox;
import game.property.PowerState;
import game.property.Side;
import game.property.Slidable;
import game.property.State;
import game.storage.PlayerData;
import game.storage.Storage;
import game.updater.Updater;
import input.GameHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import window.SceneManager;

public class Player extends Characters implements Slidable {
	public static final double LIMIT_NORMAL_ATTACK = 10;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;

	protected static final GUI youAreDead = new GUIImage(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
			new Image(ClassLoader.getSystemResource("images/cutscene/you died.png").toString()));

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
		atk = player.atk;
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

		if (state == State.SLIDING) {
			positionY = Map.FLOOR_HEIGHT - width;
			speedY = 0;
		} else if (positionY >= Map.FLOOR_HEIGHT - height) {
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

	public void affectTo(Characters e) {
		// takes damage to e = atk
		e.decreaseHp(atk);
	}

	public void jump() {
		// first jump
		if (state == State.RUNNING) {
			state = State.JUMPING;
			jump = 1;
			currentAnimation = 8;
			speedY = -18;
		}
		// other jumps
		else if (state == State.JUMPING && jump < Player.MAX_JUMP) {
			jump++;
			speedY = -15;
		}
	}

	public void goDown() {
		if (state != State.JUMPING) {
			slide();
		} else {
			speedY += MOVEDOWN_SPEED;
		}
	}

	public void slide() {
		state = State.SLIDING;
		canvas.setRotate(-90);
		speedY = 0;
		currentAnimation = 8;
		draw();
	}

	public void makeShield() {
		if (mana >= 0.5) {
			Shield shield = new Shield(positionX - 150, positionY - 50, Shield.SKILL_WIDTH, Shield.SKILL_HEIGHT);
			shield.setOwner(this);
			Model.getContainer().add(shield);
		}
	}

	public void useShield() {
		mana -= 0.5;
		powerState = PowerState.IMMORTAL;
		if (mana <= 0) {
			// force release spacebar
			GameHandler.keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "SPACE", KeyCode.SPACE, false,
					false, false, false));
		}
	}

	public void useFireball() {
		if (state != State.SLIDING && playerData.getCooldown(0) <= 0) {
			playerData.resetCooldown(0);
			Fireball fireball = new Fireball(positionX, positionY + height / 2, Fireball.SKILL_WIDTH,
					Fireball.SKILL_HEIGHT);
			fireball.setOwner(this);
			Model.getContainer().add(fireball);
		}
	}

	public void useLightning() {
		if (state != State.SLIDING && playerData.getCooldown(1) <= 0) {
			playerData.resetCooldown(1);
			Timeline timerLightning = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Lightning lightning = new Lightning(nearestMonsterPosition(), 0, Lightning.SKILL_WIDTH,
						Lightning.SKILL_HEIGHT);
				lightning.setOwner(this);
				Model.getContainer().add(lightning);
			}));
			timerLightning.setCycleCount(10);
			timerLightning.play();
		}
	}

	public static double nearestMonsterPosition() {
		double pos = 800;
		for (Monster monster : Model.getContainer().getMonsterList()) {
			pos = Math.min(pos, monster.getPositionX() + 50);
		}
		return pos;
	}

	public void useThunderbolt() {
		if (state != State.SLIDING && playerData.getCooldown(2) <= 0) {
			playerData.resetCooldown(2);
			Thunderbolt thunderbolt = new Thunderbolt(0, 0, Thunderbolt.SKILL_WIDTH, Thunderbolt.SKILL_HEIGHT);
			thunderbolt.setOwner(this);
			Model.getContainer().add(thunderbolt);
		}
	}

	public void useSlashy() {
		if (state != State.SLIDING && playerData.getCooldown(3) <= 0) {
			playerData.resetCooldown(3);
			Slashy slashy = new Slashy(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
			slashy.setOwner(this);
			Model.getContainer().add(slashy);
		}
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
		Updater.getTimerUpdate().stop();
		Animations.getTimerAnimation().stop();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			positionY += 10;
			canvas.setTranslateY(positionY);
			canvas.setOpacity(
					1 - ((positionY + height - Map.FLOOR_HEIGHT) / (SceneManager.SCREEN_HEIGHT - Map.FLOOR_HEIGHT)));
			canvas.setRotate(canvas.getRotate() + 15);
		}));
		timeline.setCycleCount((int) Updater.FPS / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			hp = 0.00001;
			Updater.getTimerUpdate().play();
			Animations.getTimerAnimation().play();
		});
		Model.getContainer().add(youAreDead);
	}

	public boolean isDead() {
		if (hp == 0.00001) {
			Model.getContainer().getPlayerPane().getChildren().removeAll(canvas, hpBar, manaBar);
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

	public void setJump(int j) {
		jump = j;
	}
}