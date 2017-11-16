package entity.skill;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.Animatable;
import property.Movable;
import utility.Pair;

public class FireBall extends Entity implements Movable, Animatable{
	
	public static final double SKILL_WIDTH = 30;
	public static final double SKILL_HEIGHT = 25;
	public static final Image[] images = new Image[20];
	public static final Image imageSlide = new Image(ClassLoader.getSystemResource("Fireball0.png").toString());
	static {
		for (int i = 0; i < 10; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("Fireball" + i + ".png").toString());
		}
	}
	
	private GraphicsContext gc;
	private double xSpeed;
	private int currentAnimation;
	
	public FireBall(Pair pos, Pair size) {
		super(pos, size);
		// TODO Auto-generated constructor stub
		xSpeed = 5 ;
		currentAnimation = 0;
	}

	@Override
	public void changeImage() {
		draw();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 10;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		canvas.setTranslateX(position.second);
	}

	@Override
	public void moveX() {
		// TODO Auto-generated method stub
		position.second += xSpeed;
		updatePosition();
	}

	@Override
	public double getXSpeed() {
		// TODO Auto-generated method stub
		return xSpeed;
	}

	@Override
	public void setXSpeed(double xSpeed) {
		// TODO Auto-generated method stub
		this.xSpeed = xSpeed;
		
	}

}
