package entity.skill;

import entity.characters.monster.Monster;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import utility.Pair;

public class Meteor extends Skill {
	public static final double SKILL_WIDTH = 200;
	public static final double SKILL_HEIGHT = 200;
	public static final double SKILL_DAMAGE = 30;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 8; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/meteor/meteor" + i + ".png").toString());
		}
	}

	public Meteor(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 8;
		speedY = -30;
		currentAnimation = 0;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation -= 4;
		currentAnimation %= 4;
		currentAnimation += 4;
		gc.drawImage(images[currentAnimation ++], 0, 0);
		canvas.setRotate(Math.atan(speedY / speedX) / Math.PI * 180);
	}
	
	public void changeSpeed(double accelX, double accelY) {
		speedX += accelX;
		speedY += accelY;
	}
	
	public boolean isCollision(Pair pos) {
		if (		(positionX	<= pos.first + Monster.MONSTER_WIDTH
								&& pos.first + Monster.MONSTER_WIDTH		<= positionX + canvas.getWidth()) 
			|| 	(positionX <= pos.first
								&& pos.first 							<= positionX + canvas.getWidth())	) {
			
			if (	pos.second <= positionY && positionY <= pos.second + Monster.MONSTER_HEIGHT) {
				return true;
			}
		}
		return false;
	}

	public boolean isDead() {
		if (positionX >= Main.SCREEN_WIDTH || positionY >= Main.SCREEN_HEIGHT) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
