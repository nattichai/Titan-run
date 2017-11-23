package entity.characters.monster;

import dataStorge.Container;
import dataStorge.Storage;
import entity.characters.Characters;
import entity.characters.player.Player;
import entity.map.Map;
import entity.skill.Darkspear;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Skill;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import main.Main;

public class Monster extends Characters {

	protected boolean canMove, canMoveOut;
	protected Skill skill;

	private Timeline timer;

	public Monster(double x, double y, double w, double h, int idx) {
		super(x, y, w, h);

		Storage monster = Storage.characters[4];
		nImage = monster.nImage;
		images = monster.images;
		width = monster.width;
		height = monster.height;
		hb = monster.hb;
		speedX = monster.speedX;
		speedY = monster.speedY;
		accelX = monster.accelX;
		accelY = monster.accelY;
		hp = monster.hp;
		maxHp = monster.maxHp;
		atk = monster.atk;
		skill = monster.skill;
		side = monster.side;
		powerState = monster.powerState;
		canMove = true;
		canMoveOut = false;

		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(100, 15);
		hpBar.setOpacity(0.8);
		hpBar.setStyle("-fx-accent:green;");
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= nImage;
		gc.drawImage(images[currentAnimation++], 0, height - hb.y - hb.h, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		if (canMove) {
			positionX += speedX;
			positionY += speedY;
		}

		// first time move out = stay in map a few secs
		if (canMove == true && canMoveOut == false && speedX > 0 && positionX >= Main.SCREEN_WIDTH - width - hb.w) {
			stayInMap();
		}

		// check floor
		if (positionY >= Map.FLOOR_HEIGHT - height) {
			positionY = Map.FLOOR_HEIGHT - height;
			speedY = 0;
		}

		hpBar.relocate(positionX + hb.x, positionY + hb.y - 100);
		updatePosition();
	}

	public void stayInMap() {
		canMove = false;
		positionX = Main.SCREEN_WIDTH - width - hb.w;
		timer = new Timeline(new KeyFrame(Duration.millis(1000), e -> useSkill()), new KeyFrame(Duration.millis(4000)));
		timer.setCycleCount(2);
		timer.play();
		timer.setOnFinished(e -> {
			canMove = true;
			canMoveOut = true;
		});
	}

	private void useSkill() {
		if (skill instanceof Fireball) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Fireball skill = new Fireball(positionX, positionY + height / 2, Fireball.SKILL_WIDTH,
						Fireball.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(8);
			timer.play();
		}

		else if (skill instanceof Lightning) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Lightning skill = new Lightning(nearestPlayerPosition(), 0, Lightning.SKILL_WIDTH,
						Lightning.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(6);
			timer.play();
		}

		else if (skill instanceof Thunderbolt) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Thunderbolt(Main.SCREEN_WIDTH - Thunderbolt.SKILL_WIDTH, 0, Thunderbolt.SKILL_WIDTH,
						Thunderbolt.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Meteor) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Meteor(Main.SCREEN_WIDTH - 500, -Meteor.SKILL_HEIGHT, Meteor.SKILL_WIDTH,
						Meteor.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setRotate(127);
				skill.setPositionX(skill.getCanvas().getTranslateX());
				skill.setPositionY(skill.getCanvas().getTranslateY());
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Slashy) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Slashy(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Darkspear) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Darkspear(Main.SCREEN_WIDTH - Darkspear.SKILL_WIDTH,
						Map.FLOOR_HEIGHT - height / 2 - Darkspear.SKILL_HEIGHT / 2, Darkspear.SKILL_WIDTH,
						Darkspear.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.setSpeedX(-skill.getSpeedX());
				Container.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}
	}

	private double nearestPlayerPosition() {
		double pos = 200;
		for (Player player : Container.getContainer().getPlayerList()) {
			pos = Math.min(pos, player.getPositionX() + 50);
		}
		return pos;
	}

	public void changeImage() {
		draw();
	}

	public void affectTo(Characters player) {
		// takes damage to player = atk
		player.decreaseHp(atk);
	}

	public void injured() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 10), e -> {
			if (canvas.getOpacity() == 1)
				canvas.setOpacity(0.5);
			else if (canvas.getOpacity() == 0.5)
				canvas.setOpacity(1);
		}));
		timeline.setCycleCount(8);
		timeline.play();
	}

	public void die() {
		// fade away
		FadeTransition ft = new FadeTransition(Duration.millis(1000), canvas);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
		// stop skill animation if it not stop yet
		if (timer != null)
			timer.stop();
	}

	public boolean isDead() {
		if (canvas.getOpacity() == 0 || positionX >= Main.SCREEN_WIDTH + 150) {
			Container.getContainer().getMonsterPane().getChildren().removeAll(canvas, hpBar);
			return true;
		}
		return false;
	}

}
