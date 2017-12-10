package game.model.character;

import game.model.Characters;
import game.model.Effect;
import game.model.Map;
import game.model.Model;
import game.model.Skill;
import game.model.gui.GUIGradientText;
import game.property.Direction;
import game.property.Hitbox;
import game.property.PowerState;
import game.property.Side;
import game.property.State;
import game.storage.SkillsData;
import game.updater.Updater;
import input.GameHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import scene.GameMain;
import scene.MainMenu;
import scene.SceneManager;
import scene.ScoreView;

public class Player extends Characters {
	public static final double LIMIT_NORMAL_ATTACK = 10;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;

	protected static final double[] fullCooldown = new double[5];
	static {
		fullCooldown[0] = SkillsData.data[0].getCooldown();
		fullCooldown[1] = SkillsData.data[1].getCooldown();
		fullCooldown[2] = SkillsData.data[2].getCooldown();
		fullCooldown[3] = SkillsData.data[3].getCooldown();
	}
	protected State state;
	protected Image imageSlide;
	protected int jump;
	protected boolean isInjuring;
	
	protected double mode;
	protected int stage;
	protected double distance;
	protected double score;
	
	protected double exp, maxExp;
	protected double mana, maxMana;
	protected double[] cooldown;

	public Player(double x, double y, int idx) {
		super(x, y, idx);

		width = 120;
		height = 200;
		hb = new Hitbox(20, 20, 100, 180);
		
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = Map.GRAVITY;
		
		side = Side.PLAYER;
		direction = Direction.RIGHT;

		state = State.RUNNING;
		jump = 0;
		isInjuring = false;
		
		mode = GameMain.getDifficulty();
		stage = 1;
		distance = 0;
		score = 0;
		
		exp = 0;
		maxExp = 50;
		mana = 100;
		maxMana = 100;
		cooldown = new double[5];

		setName(MainMenu.getRegisterName().getText());
		userInterface.updateLevel(level);
	}

	public void draw() {
		if (direction != imageDirection) {
			if (state == State.SLIDING) {
				canvas.setScaleX(1);
				canvas.setScaleY(-1);
			} else {
				canvas.setScaleX(-1);
				canvas.setScaleY(1);
			}
		} else {
			canvas.setScaleX(1);
			canvas.setScaleY(1);
		}
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= nImage;
		gc.drawImage(images[currentAnimation], 0, 0, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		if (speedX < 0) {
			direction = Direction.LEFT;
		} else if (speedX > 0) {
			direction = Direction.RIGHT;
		}

		positionX += speedX;
		positionY += speedY;
		if (positionX < 0) {
			positionX = 0;
		} else if (positionX > SceneManager.SCREEN_WIDTH - width) {
			positionX = SceneManager.SCREEN_WIDTH - width;
		}

		if (state == State.SLIDING) {
			positionY = Map.FLOOR_HEIGHT - width;
			speedY = 0;
		} else if (positionY >= Map.FLOOR_HEIGHT - height) {
			positionY = Map.FLOOR_HEIGHT - height;
			speedY = 0;
			if (GameMain.getSpeed() != 0 || speedX != 0) {
				state = State.RUNNING;
			} else {
				state = State.STILL;
			}
		}

		userInterface.updateNamePos(positionX - 25, positionY - 80);
		userInterface.updateLevelPos(positionX + 166, positionY - 80);
		userInterface.updateHpPos(positionX - 25, positionY - 50);
		userInterface.updateManaPos(positionX - 25, positionY - 32);
		updatePosition();
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= nImage;
		draw();
	}

	public void affectTo(Characters e) {
		// takes damage to e = atk
		e.decreaseHp(atk);
	}

	public void jump() {
		// first jump
		if (state == State.RUNNING || state == State.STILL) {
			state = State.JUMPING;
			jump = 1;
			speedY = -18.5;
			new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Jump.wav").toString()).play();
		}
		// other jumps
		else if (jump < Player.MAX_JUMP) {
			jump++;
			speedY = -15;
			new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Jump.wav").toString()).play();
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
		if (state != State.SLIDING) {
			new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Slide.wav").toString()).play();
		}
		state = State.SLIDING;
		canvas.setRotate(-90);
		speedY = 0;
		draw();

	}

	public void makeShield() {
		if (mana >= 0.5) {
			Skill shield = new Skill(positionX - 150, positionY - 50, 4, this);
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
		if (state != State.SLIDING && cooldown[0] <= 0) {
			resetCooldown(0);
			Skill fireball = new Skill(positionX, positionY + height / 2, 0, this);
			if (direction != imageDirection) {
				fireball.getCanvas().setScaleX(-1);
				fireball.setSpeedX(-fireball.getSpeedX());
			}
			Model.getContainer().add(fireball);
		}
	}

	public void useLightning() {
		if (state != State.SLIDING && cooldown[1] <= 0) {
			resetCooldown(1);
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill lightning = new Skill(nearestMonsterPosition(), 0, 1, this);
				if (direction != imageDirection) {
					lightning.getCanvas().setScaleX(-1);
					lightning.setSpeedX(-lightning.getSpeedX());
				}
				Model.getContainer().add(lightning);
			}));
			timer.setCycleCount(6);
			timer.play();
		}
	}

	public double nearestMonsterPosition() {
		double pos = 800;
		double minDistance = 1000;
		if (direction != imageDirection) {
			pos = 200;
		}
		for (Monster monster : Model.getContainer().getMonsterList()) {
			double distance = Math.abs(monster.getPositionX() - positionX);
			if (distance < minDistance) {
				minDistance = distance;
				pos = monster.getPositionX() + monster.getWidth() / 2;
			}
		}
		return pos;
	}

	public void useThunderbolt() {
		if (state != State.SLIDING && cooldown[2] <= 0) {
			resetCooldown(2);
			Skill thunderbolt = new Skill(positionX, 0, 2, this);
			if (direction != imageDirection) {
				thunderbolt.getCanvas().setScaleX(-1);
				thunderbolt.setSpeedX(-thunderbolt.getSpeedX());
			}
			Model.getContainer().add(thunderbolt);
		}
	}

	public void useSlashy() {
		if (state != State.SLIDING && cooldown[3] <= 0) {
			resetCooldown(3);
			Skill slashy = new Skill(0, 0, 3, this);
			Model.getContainer().add(slashy);
		}
	}

	public void backToRunningPosition() {
		setDistance(0);
		speedX = 0;
		positionX = 90;
		direction = Direction.RIGHT;
		if (state == State.SLIDING) {
			canvas.setRotate(0);
			state = State.RUNNING;
		}
	}

	public void injured() {
		isInjuring = true;
		powerState = PowerState.IMMORTAL;
		blink = new Timeline(new KeyFrame(Duration.millis(1000 / 10), e -> {
			if (canvas.getOpacity() == 1)
				canvas.setOpacity(0.5);
			else
				canvas.setOpacity(1);
		}));
		blink.setCycleCount(16);
		blink.play();
		blink.setOnFinished(e -> {
			isInjuring = false;
			powerState = PowerState.NORMAL;
			isCollided = false;
		});
		new AudioClip(ClassLoader.getSystemResource("sounds/otherfx/Gethit.wav").toString()).play();
	}

	public void die() {
		hp = 0.00001;
		ScoreView.setPlayer(name, score, mode);
		Updater.playerDead();
		GameMain.pauseGame();
		GameMain.setSpeed(2);
		timer = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			positionY += 10;
			canvas.setTranslateY(positionY);
			canvas.setRotate(canvas.getRotate() + 15);
		}));
		timer.setCycleCount((int) Updater.FPS / 2);
		timer.play();
		timer.setOnFinished(e -> {
			GameMain.continueGame();
			userInterface.dead(this);
			Model.getContainer().remove(this);
		});
	}

	public boolean isDead() {
		return hp == 0.00001 || canvas.getOpacity() == 0;
	}
	
	public void setName(String s) {
		name = s;
		userInterface.updateName(s);
	}
	
	public void addExp(double e) {
		exp += e;
		while(exp > maxExp) {
			exp -= maxExp;
			addLevel(1);
		}
		userInterface.updateExp(exp / maxExp);
	}
	
	public void addLevel(int lvl) {
		Effect levelUp = new Effect(positionX - 230, positionY - 350, 1, 1);
		levelUp.getCanvas().setOpacity(0.8);
		Model.getContainer().add(levelUp);
		
		GUIGradientText levelUpText = new GUIGradientText(positionX - 50, positionY - 100, "LEVEL UP", 2);
		Model.getContainer().add(levelUpText);
		
		new AudioClip(ClassLoader.getSystemResource("sounds/otherfx/level up.mp3").toString()).play();
		
		level += lvl;
		double multi = 1 + 0.2 * Math.pow(level, 1.5);
		hp = (hp / maxHp) * 200 * multi;
		maxHp = 200 * multi;
		atk = 20 * multi;
		maxExp = 50 * Math.pow(level, 2);
		userInterface.updateLevel(level);
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
		userInterface.updateMana(this.mana / maxMana);
	}
	
	public void setJump(int j) {
		jump = j;
	}
	
	public void addJump(int jump) {
		this.jump += jump;
	}

	public double getScore() {
		return score;
	}

	public void addScore(double s) {
		score += s * Math.pow(GameMain.getDifficulty(), 1.5);
		userInterface.updateScore(score);
	}

	public double getCooldown(int index) {
		return cooldown[index];
	}

	public void resetCooldown(int index) {
		cooldown[index] = fullCooldown[index];
		userInterface.updateCooldown(index, 1);
	}

	public void decreaseCooldown(double cd) {
		for (int i = 0; i < 4; ++i) {
			cooldown[i] -= cd;
			if (cooldown[i] < 0)
				cooldown[i] = 0;
			userInterface.updateCooldown(i, cooldown[i] / fullCooldown[i]);
		}
	}

	public int getStage() {
		return stage;
	}

	public void addStage(int s) {
		stage += s;
		userInterface.updateStage(stage);
		
		GameMain.setSpeed(10 + 5 * stage / (stage + 5));
		GameMain.setDifficulty(GameMain.getDifficulty() + 0.5);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double d) {
		distance = d;
		userInterface.updateDistance(d / GameMain.getStageDistance());
	}

	public void addDistance(double d) {
		distance += d;
		if (distance > GameMain.getStageDistance()) {
			distance = GameMain.getStageDistance();
		}
		userInterface.updateDistance(distance / GameMain.getStageDistance());
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isInjuring() {
		return isInjuring;
	}
}