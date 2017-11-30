package entity.characters;

import entity.Entity;
import entity.map.Map;
import entity.skill.Darkspear;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Skill;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import game.model.Model;
import game.property.Direction;
import game.property.Hitbox;
import game.property.Side;
import game.property.UserInterface;
import game.storage.Storage;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import window.SceneManager;

public class Monster extends Characters {

	protected boolean canMove, canMoveOut;
	protected Skill skill;

	private Timeline timer;

	public Monster(double x, double y, int idx) {
		super(x, y, Storage.characters[idx].width, Storage.characters[idx].height);

		Storage monster = Storage.characters[idx];
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
		side = Side.MONSTER;
		direction = Direction.LEFT;
		imageDirection = monster.imageDirection;
		powerState = monster.powerState;
		canMove = true;
		canMoveOut = false;

		userInterface = new UserInterface(this);

		userInterface.getHpBar().setPrefSize(100, 15);
	}

	public void draw() {
		if (direction != imageDirection) {
			canvas.setScaleX(-1);
		} else {
			canvas.setScaleX(1);
		}
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= nImage;
		gc.drawImage(images[currentAnimation], 0, 0, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		if (canMove) {
			positionX += speedX;
			positionY += speedY;
		}

		// first time move out = stay in map a few secs
		if (canMove == true && canMoveOut == false && speedX > 0
				&& positionX >= SceneManager.SCREEN_WIDTH - width - hb.w) {
			stayInMap();
		}

		// check floor
		if (positionY >= Map.FLOOR_HEIGHT - height) {
			positionY = Map.FLOOR_HEIGHT - height;
			speedY = 0;
		}

		userInterface.updateHp(positionX + hb.x, positionY + hb.y - 50);

		updatePosition();
	}

	public void stayInMap() {
		canMove = false;
		positionX = SceneManager.SCREEN_WIDTH - width - hb.w;
		timer = new Timeline(new KeyFrame(Duration.millis(1000), e -> useSkill()), new KeyFrame(Duration.millis(4000)));
		timer.setCycleCount(2);
		timer.play();
		timer.setOnFinished(e -> {
			canMove = true;
			canMoveOut = true;
		});
	}

	private void useSkill() {
		if (isDead()) {
			return;
		}
		if (skill instanceof Fireball) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Fireball skill = new Fireball(positionX, positionY + height / 2, Fireball.SKILL_WIDTH * 5,
						Fireball.SKILL_HEIGHT * 5);
				skill.setOwner(this);
				skill.setHb(new Hitbox(40, 40, 170, 70));
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX() * 0.6);
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Lightning) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Lightning skill = new Lightning(140, 0, Lightning.SKILL_WIDTH, Lightning.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(6);
			timer.play();
		}

		else if (skill instanceof Thunderbolt) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Thunderbolt(SceneManager.SCREEN_WIDTH - Thunderbolt.SKILL_WIDTH, 0, Thunderbolt.SKILL_WIDTH,
						Thunderbolt.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Meteor) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Meteor(SceneManager.SCREEN_WIDTH - 100, -500, Meteor.SKILL_WIDTH, Meteor.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setRotate(127);
				skill.setPositionX(skill.getCanvas().getTranslateX());
				skill.setPositionY(skill.getCanvas().getTranslateY());
				skill.setSpeedX(-skill.getSpeedX());
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Slashy) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Slashy(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
				skill.setOwner(this);
				skill.getCanvas().setScaleX(-1);
				skill.setSpeedX(-skill.getSpeedX());
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skill instanceof Darkspear) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				skill = new Darkspear(SceneManager.SCREEN_WIDTH - Darkspear.SKILL_WIDTH,
						Map.FLOOR_HEIGHT - height / 2 - Darkspear.SKILL_HEIGHT / 2, Darkspear.SKILL_WIDTH,
						Darkspear.SKILL_HEIGHT);
				skill.setOwner(this);
				skill.setSpeedX(-skill.getSpeedX());
				Model.getContainer().add(skill);
			}));
			timer.setCycleCount(1);
			timer.play();
		}
	}

	public void changeImage() {
		currentAnimation++;
		draw();
	}

	public boolean isCollision(Entity e) {
		if (side == e.getSide() || (side == Side.NEUTRAL && e.getSide() == Side.MONSTER))
			return false;
		if (positionX + hb.x < e.getPositionX() + e.getHb().x + e.getHb().w
				&& positionX + hb.x + hb.w > e.getPositionX() + e.getHb().x
				&& positionY + hb.y < e.getPositionY() + e.getHb().y + e.getHb().h
				&& positionY + hb.y + hb.h > e.getPositionY() + e.getHb().y == true) {
			return true;
		}
		return false;
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
		hp = 0.00001;
		// kill monster = get 2000 scores;
		Model.getContainer().getPlayer().addScore(2000);
		// fade away
		FadeTransition ft = new FadeTransition(Duration.millis(1000), canvas);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
		// stop skill animation if it not stop yet
		if (timer != null) {
			timer.stop();
		}
	}

	public boolean isDead() {
		if (canvas.getOpacity() == 0 || hp == 0.00001 || positionX <= -150
				|| positionX >= SceneManager.SCREEN_WIDTH + 150) {
			Model.getContainer().getMonsterPane().getChildren().removeAll(canvas, userInterface.getHpBar());
			return true;
		}
		return false;
	}

}
