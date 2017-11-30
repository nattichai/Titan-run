package entity.characters;

import java.util.Random;

import entity.effect.Charge;
import entity.gui.GUIDamage;
import entity.skill.Beam;
import entity.skill.Drill;
import game.model.Model;
import game.property.Direction;
import game.property.PowerState;
import game.updater.Updater;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import window.SceneManager;

public class Boss extends Monster {
	private Timeline moveTo;
	private Timeline goAroundTimeline;
	private Timeline attackTimeline;
	private Timeline moveTimeline;
	private Timeline beamTimeline;

	private boolean isReady;
	private int step;
	private boolean isAttack;

	public Boss(double x, double y, int idx) {
		super(x, y, idx);

		isReady = false;
		step = 0;
		isAttack = false;

		userInterface.getHpBar().setPrefSize(400, 30);
		userInterface.getHpBar().relocate(300, 100);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		positionX += speedX;
		positionY += speedY;
		updatePosition();
	}

	public void moveTo(double posX, double posY, double duration) {
		if (hp <= 0.00001) {
			return;
		}
		speedX = (posX - positionX) / duration * Updater.LOOP_TIME;
		speedY = (posY - positionY) / duration * Updater.LOOP_TIME;
		if ((int) speedX == 0) {
			speedX = 0;
		}
		if (speedX < 0) {
			direction = Direction.LEFT;
		} else if (speedX > 0) {
			direction = Direction.RIGHT;
		} else if (positionX <= SceneManager.SCREEN_WIDTH / 2) {
			direction = Direction.RIGHT;
		} else {
			direction = Direction.LEFT;
		}
		moveTo = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			move();
		}));
		moveTo.setCycleCount((int) (Updater.FPS * duration / 1000));
		moveTo.play();
	}

	public void update() {
		if (!isAttack) {
			isAttack = true;
			if (step == 0 || step == 1 || step == 3) {
				goAround();
			} else if (step == 2) {
				attack();
			} else if (step == 4) {
				beam();
			}
			step++;
			step %= 5;
		}
	}

	private void goAround() {
		goAroundTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> moveTo(10, 150, 1000)),
				new KeyFrame(Duration.millis(1000), e -> moveTo(10, 450, 1000)),
				new KeyFrame(Duration.millis(2000), e -> moveTo(600, 450, 1000)),
				new KeyFrame(Duration.millis(3000), e -> moveTo(600, 150, 1000)), new KeyFrame(Duration.millis(4000)));
		goAroundTimeline.setOnFinished(e -> isAttack = false);
		goAroundTimeline.play();
	}

	private void attack() {
		attackTimeline = new Timeline(new KeyFrame(Duration.millis(1200), e -> {
			Drill skill = new Drill(positionX, positionY + height / 2, Drill.SKILL_WIDTH, Drill.SKILL_HEIGHT);
			skill.setOwner(this);
			skill.getCanvas().setScaleX(-1);
			skill.setSpeedX(-skill.getSpeedX() * 0.6);
			Model.getContainer().add(skill);
		}));
		attackTimeline.setCycleCount(5);
		attackTimeline.play();

		moveTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> moveTo(600, 450, 1000)),
				new KeyFrame(Duration.millis(1000), e -> moveTo(600, 150, 1000)), new KeyFrame(Duration.millis(2000)));
		moveTimeline.setCycleCount(3);
		moveTimeline.setOnFinished(e -> isAttack = false);
		moveTimeline.play();
	}

	private void beam() {
		Charge charge = new Charge(675, 450, 1);
		Beam beam = new Beam(600 + width / 2 - Beam.SKILL_WIDTH, 450 + (height - Beam.SKILL_HEIGHT) / 2,
				Beam.SKILL_WIDTH, Beam.SKILL_HEIGHT);
		beam.setOwner(this);
		beamTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> moveTo(600, 450, 1000)),
				new KeyFrame(Duration.millis(1000), e -> Model.getContainer().add(charge)),
				new KeyFrame(Duration.millis(3000), e -> Model.getContainer().add(beam)),
				new KeyFrame(Duration.millis(6000), e -> Model.getContainer().remove(beam)));
		beamTimeline.setOnFinished(e -> isAttack = false);
		beamTimeline.play();
	}

	public void decreaseHp(double d) {
		if (hp <= 0.00001 || powerState == PowerState.IMMORTAL) {
			return;
		}
		hp -= d;
		if (hp <= 0) {
			hp = 0;
			die();
		}
		userInterface.updateHp(hp / maxHp);
		if (d > 1) {
			GUIDamage damageUI = new GUIDamage(positionX + width / 2, positionY, "" + (int) d);
			Model.getContainer().add(damageUI);
			injured();
		}
	}

	public void die() {
		Model.getContainer().getPlayer().addScore(20000);

		stopAllTimeline(); // stop skill animation if it not stop yet

		Random rnd = new Random();
		Timeline shake = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			canvas.setTranslateX(positionX + rnd.nextInt(101) - 50);
			canvas.setTranslateY(positionY + rnd.nextInt(101) - 50);
			canvas.setOpacity(canvas.getOpacity() - 0.005);
		}));
		shake.setCycleCount((int) (Updater.FPS * 3));
		shake.play();
		shake.setOnFinished(e -> {
			hp = 0.00001;
			Updater.victory();
		});
	}

	private void stopAllTimeline() {
		if (goAroundTimeline != null)
			goAroundTimeline.stop();
		if (attackTimeline != null)
			attackTimeline.stop();
		if (moveTimeline != null)
			moveTimeline.stop();
		if (beamTimeline != null)
			beamTimeline.stop();
		if (moveTo != null)
			moveTo.stop();
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
}
