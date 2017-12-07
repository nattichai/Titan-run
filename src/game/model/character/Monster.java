package game.model.character;

import game.model.Characters;
import game.model.Entity;
import game.model.Map;
import game.model.Model;
import game.model.Skill;
import game.property.Hitbox;
import game.property.Side;
import game.storage.SkillsData;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import scene.SceneManager;

public class Monster extends Characters {

	protected boolean canMove, canMoveOut;

	private Timeline timer;

	public Monster(double x, double y, int idx) {
		super(x, y, idx);

		canMove = true;
		canMoveOut = false;

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
		if (skillIndex == 0) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill fireball = new Skill(positionX, positionY + height / 2, 0, this);
				fireball.setHb(new Hitbox(0, 0, 250, 150));
				fireball.getCanvas().setScaleX(-5);
				fireball.getCanvas().setScaleY(5);
				fireball.setSpeedX(-fireball.getSpeedX() * 0.6);
				Model.getContainer().add(fireball);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skillIndex == 1) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill lightning = new Skill(140, 0, 1, this);
				lightning.getCanvas().setScaleX(-1);
				lightning.setSpeedX(-lightning.getSpeedX());
				Model.getContainer().add(lightning);
			}));
			timer.setCycleCount(6);
			timer.play();
		}

		else if (skillIndex == 2) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill thunderbolt = new Skill(SceneManager.SCREEN_WIDTH - SkillsData.data[2].getWidth(), 0, 2, this);
				thunderbolt.setHb(new Hitbox(200, 0, 120, SceneManager.SCREEN_HEIGHT));
				thunderbolt.getCanvas().setScaleX(-1);
				thunderbolt.setSpeedX(-thunderbolt.getSpeedX());
				Model.getContainer().add(thunderbolt);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skillIndex == 3) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill slashy = new Skill(0, 0, 3, this);
				slashy.getCanvas().setScaleX(-1);
				slashy.setSpeedX(-slashy.getSpeedX());
				Model.getContainer().add(slashy);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skillIndex == 5) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill meteor = new Skill(SceneManager.SCREEN_WIDTH - 100, -500, 5, this);
				meteor.getCanvas().setRotate(127);
				meteor.setPositionX(meteor.getCanvas().getTranslateX());
				meteor.setPositionY(meteor.getCanvas().getTranslateY());
				meteor.setSpeedX(-meteor.getSpeedX());
				Model.getContainer().add(meteor);
			}));
			timer.setCycleCount(1);
			timer.play();
		}

		else if (skillIndex == 6) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill skillIndex = new Skill(SceneManager.SCREEN_WIDTH - SkillsData.data[6].getWidth(),
						Map.FLOOR_HEIGHT - (height + SkillsData.data[6].getHeight()) / 2, 6, this);
				skillIndex.setOwner(this);
				skillIndex.setSpeedX(-skillIndex.getSpeedX());
				Model.getContainer().add(skillIndex);
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
		if (hp <= 0.00001 || side == e.getSide() || (side == Side.NEUTRAL && e.getSide() == Side.MONSTER))
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
		if (canvas.getOpacity() == 0 || positionX <= -150 || positionX >= SceneManager.SCREEN_WIDTH + 150) {
			Model.getContainer().getMonsterPane().getChildren().removeAll(canvas, userInterface.getHpBar());
			return true;
		}
		return false;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public void setCanMoveOut(boolean canMoveOut) {
		this.canMoveOut = canMoveOut;
	}

}
