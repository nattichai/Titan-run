package entity.skill;

import controller.Handler;
import dataStorge.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import main.Main;

public class Shield extends Skill {
	public static final double SKILL_WIDTH = 400;
	public static final double SKILL_HEIGHT = 300;
	public static final double SKILL_DAMAGE = 10;
	public static final double SKILL_COOLDOWN = 0.10;
	protected static final Image[] images = new Image[63];
	static {
		for (int i = 0; i < 63; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/shield/shield" + i + ".png").toString());
		}
	}
	
	public Shield(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 0;
		speedY = 0;
	}

	public Shield() {
	}
	
	public void updatePosition() {
		positionY = owner.getPositionY() - 50;
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 63;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isDead() {
		if (owner.getHp() == 0.00001 || !Handler.getKeys().contains(KeyCode.SPACE) || positionX >= Main.SCREEN_WIDTH) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
