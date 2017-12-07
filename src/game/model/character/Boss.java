package game.model.character;

import java.util.Random;

import game.model.Effect;
import game.model.Model;
import game.model.Skill;
import game.model.gui.GUIDamage;
import game.property.Direction;
import game.property.PowerState;
import game.updater.Updater;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import scene.SceneManager;

public class Boss extends Monster {
	private Timeline moveTo;
	private Timeline goAroundTimeline;
	private Timeline attackTimeline;
	private Timeline moveTimeline;
	private Timeline beamTimeline;

	private boolean isReady;
	private int step;
	private boolean isAttack;
	private double bossSpeed;

	public Boss(double x, double y, int idx) {
		super(x, y, idx);

		isReady = false;
		step = 0;
		isAttack = false;

		double stage = 1;
		if (Model.getContainer().getPlayer() != null) {
			stage = Model.getContainer().getPlayer().getStage();
		}
		bossSpeed = 700 * (1 - stage / (stage + 4));

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
		if (!isAttack && Updater.getTimerUpdate().getStatus() == Status.RUNNING) {
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
		goAroundTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> moveTo(10, 150, bossSpeed)),
				new KeyFrame(Duration.millis(bossSpeed), e -> moveTo(10, 450, bossSpeed)),
				new KeyFrame(Duration.millis(2 * bossSpeed), e -> moveTo(600, 450, bossSpeed)),
				new KeyFrame(Duration.millis(3 * bossSpeed), e -> moveTo(600, 150, bossSpeed)),
				new KeyFrame(Duration.millis(4 * bossSpeed)));
		goAroundTimeline.setOnFinished(e -> isAttack = false);
		goAroundTimeline.play();
	}

	private void attack() {
		double random = 1.1 + (new Random().nextDouble() * 0.2);
		attackTimeline = new Timeline(new KeyFrame(Duration.millis(random * bossSpeed), e -> {
			Skill skill = new Skill(positionX, positionY + height / 2, 7, this);
			skill.getCanvas().setScaleX(-1);
			skill.setSpeedX(-skill.getSpeedX() * 0.6);
			Model.getContainer().add(skill);
		}));
		attackTimeline.setCycleCount(5);
		attackTimeline.play();

		moveTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> moveTo(600, 450, bossSpeed)),
				new KeyFrame(Duration.millis(bossSpeed), e -> moveTo(600, 150, bossSpeed)),
				new KeyFrame(Duration.millis(2 * bossSpeed)));
		moveTimeline.setCycleCount(3);
		moveTimeline.setOnFinished(e -> isAttack = false);
		moveTimeline.play();
	}

	private void beam() {
		Effect charge = new Effect(0, 0, 0, 1);
		Skill beam = new Skill(0, 0, 8, this);

		beamTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			double pos = new Random().nextDouble() * 590 + 10;
			if (Model.getContainer().getPlayer() != null) {
				pos = Model.getContainer().getPlayer().getPositionX();
			}
			charge.getCanvas().relocate(pos - (charge.getWidth() - 120) / 2, positionY);
			beam.setPositionX(pos - (150 - 120) / 2);
			beam.setPositionY(positionY + 25);
			beam.updatePosition();
			moveTo(pos - (width - 120) / 2, 150, bossSpeed);
		}), new KeyFrame(Duration.millis(bossSpeed), e -> Model.getContainer().add(charge)),
				new KeyFrame(Duration.millis(1.25 * bossSpeed), e -> Model.getContainer().add(beam)),
				new KeyFrame(Duration.millis(1.5 * bossSpeed), e -> {
					Model.getContainer().remove(charge);
					Model.getContainer().remove(beam);
				}));
		beamTimeline.setCycleCount(5);
		beamTimeline.play();
		beamTimeline.setOnFinished(e -> isAttack = false);
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
			GUIDamage damageUI = new GUIDamage(positionX + width / 2, positionY, "" + (int) d, 0);
			Model.getContainer().add(damageUI);
			injured();
		}
	}

	public void die() {
		Model.getContainer().getPlayer().addScore(20000);

		stopAllTimeline(); // stop skill animation if it not stop yet
		isReady = false;

		SceneManager.setTrasitioning(true);

		Random rnd = new Random();
		Timeline shake = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			canvas.setTranslateX(positionX + rnd.nextInt(101) - 50);
			canvas.setTranslateY(positionY + rnd.nextInt(101) - 50);
			canvas.setOpacity(canvas.getOpacity() - 0.005);
		}));
		shake.setCycleCount((int) (Updater.FPS * 3));
		shake.play();
		shake.setOnFinished(e -> {
			canvas.setOpacity(0);
			Updater.victory();
		});
	}

	public void pauseAllTimeline() {
		if (goAroundTimeline != null && goAroundTimeline.getStatus() == Status.RUNNING) {
			goAroundTimeline.pause();
		}
		if (attackTimeline != null && attackTimeline.getStatus() == Status.RUNNING) {
			attackTimeline.pause();
		}
		if (moveTimeline != null && moveTimeline.getStatus() == Status.RUNNING) {
			moveTimeline.pause();
		}
		if (beamTimeline != null && beamTimeline.getStatus() == Status.RUNNING) {
			beamTimeline.pause();
		}
		if (moveTo != null && moveTo.getStatus() == Status.RUNNING) {
			moveTo.pause();
		}
	}

	public void continueAllTimeline() {
		if (goAroundTimeline != null && goAroundTimeline.getStatus() == Status.PAUSED) {
			goAroundTimeline.play();
		}
		if (attackTimeline != null && attackTimeline.getStatus() == Status.PAUSED) {
			attackTimeline.play();
		}
		if (moveTimeline != null && moveTimeline.getStatus() == Status.PAUSED) {
			moveTimeline.play();
		}
		if (beamTimeline != null && beamTimeline.getStatus() == Status.PAUSED) {
			beamTimeline.play();
		}
		if (moveTo != null && moveTo.getStatus() == Status.PAUSED) {
			moveTo.play();
		}
	}

	public void stopAllTimeline() {
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
