package entity.characters.monster;

import dataStorge.Container;
import dataStorge.Storage;
import entity.characters.Characters;
import entity.map.Map;
import entity.skill.Skill;
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

	public Monster(double x, double y, double w, double h, int idx) {
		super(x, y, w, h);

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
		atk	= monster.atk;
		side = monster.side;
		powerState = monster.powerState;
		canMove = true;
		canMoveOut = false;
		
		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(100, 15);
		hpBar.setOpacity(0.8);
		hpBar.setStyle("-fx-accent:green;");

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 8;
		gc.drawImage(images[currentAnimation++], 0, height - hb.y - hb.h, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		if (canMove) {
			positionX += speedX;
			positionY += speedY;
		}
		
		if (canMove == true && canMoveOut == false && speedX > 0 && positionX >= Main.SCREEN_WIDTH - width - hb.w) {
			canMove = false;
			positionX = Main.SCREEN_WIDTH - width - hb.w;
			Timeline timer = new Timeline(new KeyFrame(Duration.millis(2000), e -> {}));
			timer.play();
			timer.setOnFinished(e -> {
				canMove = true;
				canMoveOut = true;
			});
		}
		hpBar.relocate(positionX + hb.x, positionY + hb.y - 100);

		if (positionY >= Map.FLOOR_HEIGHT - height) {
			positionY = Map.FLOOR_HEIGHT - height;
			speedY = 0;
		}
		updatePosition();
	}

	public void changeImage() {
		draw();
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
		FadeTransition ft = new FadeTransition(Duration.millis(1000), canvas);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
	}

	public boolean isDead() {
		if (canvas.getOpacity() == 0 || positionX >= Main.SCREEN_WIDTH + 150) {
			Container.getContainer().getMonsterPane().getChildren().removeAll(canvas, hpBar);
			return true;
		}
		return false;
	}

}
