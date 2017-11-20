package entity.characters.monster;

import entity.characters.Characters;
import entity.map.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.Container;
import main.Main;
import property.Status;

public class Monster extends Characters {
	public static int nImage;
	public static Image[] images;
	
	public static double MONSTER_WIDTH;
	public static double MONSTER_HEIGHT;

	public Monster(double x, double y, double w, double h, int idx) {
		super(x, y, w, h);

		nImage = Status.allStatus[idx].nImage;
		images = Status.allStatus[idx].images;
		MONSTER_WIDTH = Status.allStatus[idx].width;
		MONSTER_HEIGHT = Status.allStatus[idx].height;
		hb = Status.allStatus[idx].hb;
		speedX = Status.allStatus[idx].speedX;
		speedY = Status.allStatus[idx].speedY;
		hp = Status.allStatus[idx].hp;
		maxHp = Status.allStatus[idx].maxHp;
		atk	= Status.allStatus[idx].atk;
		side = Status.allStatus[idx].side;
		powerState = Status.allStatus[idx].powerState;

		hpBar = new ProgressBar(1);
		hpBar.setPrefSize(100, 20);
		hpBar.setOpacity(0.3);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 8;
		gc.drawImage(images[currentAnimation++], 0, 0, MONSTER_WIDTH, MONSTER_HEIGHT);
	}

	public void move() {
		changeSpeed(0.19, Map.GRAVITY);
		positionX += speedX;
		positionY += speedY;
		hpBar.relocate(positionX + hb.x, positionY + hb.y - 100);

		if (positionY >= Map.FLOOR_HEIGHT - MONSTER_HEIGHT) {
			positionY = Map.FLOOR_HEIGHT - MONSTER_HEIGHT;
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
			else
				canvas.setOpacity(1);
		}));
		timeline.setCycleCount(8);
		timeline.play();
	}

	public void die() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / Main.FRAME_RATE), e -> {
			positionY += 5;
			canvas.setTranslateY(positionY);
			canvas.setOpacity(1 - ((positionY + MONSTER_HEIGHT - Map.FLOOR_HEIGHT) / (Main.SCREEN_HEIGHT - Map.FLOOR_HEIGHT)));
			canvas.setRotate(canvas.getRotate() + 20);
		}));
		timeline.setCycleCount(Main.FRAME_RATE / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			hp = 0.00001;
		});
	}

	public boolean isDead() {
		if (hp == 0.00001 || positionX >= Main.SCREEN_WIDTH + 150) {
			Container.getContainer().getMonsterPane().getChildren().removeAll(canvas, hpBar);
			return true;
		}
		return false;
	}

}
