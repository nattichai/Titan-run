package entity.skill;

import entity.characters.monster.Monster;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import utility.Pair;

public class Fireball extends Skill {
	public static final double SKILL_WIDTH = 50;
	public static final double SKILL_HEIGHT = 30;
	public static final double SKILL_DAMAGE = 10;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/fireball/Fireball" + i + ".png").toString());
		}
	}
	
	public Fireball(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 20;
		speedY = 0;
		currentAnimation = 5;
		
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation -= 5;
		currentAnimation %= 10;
		currentAnimation += 5;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
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
		if (positionX >= Main.SCREEN_WIDTH) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
