package controller;

import java.util.Iterator;
import java.util.Random;

import entity.Entity;
import entity.map.Map;
import entity.obstacle.Obstacle;
import entity.player.Player;
import entity.skill.Fireball;
import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import main.Container;
import main.Main;
import property.PowerState;
import property.State;
import utility.Pair;

public class Controller {
	
	private static State saveState;
	private static int tick = 0;
	private static int rnd = 0;
	
	public static void update() {
		randomObstacle();
		gravity();
		moveAll();
		checkCollision();
	}
	
	private static void randomObstacle() {
		if (tick == 0) {
			rnd = new Random().nextInt(30) + 40;
		}
		tick ++;
		if (tick > rnd) {
			tick = 0;
			Obstacle obstacle = new Obstacle(new Pair(1100, 0), new Pair(Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT));
			Container.getContainer().add(obstacle);
		}
	}
	
	private static void gravity() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getState() == State.JUMPING1 || player.getState() == State.JUMPING2)
				player.changeSpeed(Map.GRAVITY);
		}
	}

	private static void moveAll() {
		for (Map map : Container.getContainer().getMapList()) {
			map.moveX();
		}
		
		try {
			for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
				obstacle.moveX();
				if (obstacle.getPosition().first < -Obstacle.OBSTACLE_WIDTH) {
					Container.getContainer().remove(obstacle);
				}
			}
		} catch (Exception e) {}
		
		for (Player player : Container.getContainer().getPlayerList()) {
			
			if (Listener.keys.contains(KeyCode.UP)) {		//JUMP
				
				if (player.getState() == State.RUNNING) {	//FIRST JUMP
					Listener.keys.remove(KeyCode.UP);
					player.setCurrentAnimation(8);
					player.draw();
					player.setYSpeed(-18);
					player.setState(State.JUMPING1);
					saveState = State.JUMPING1;
				} else if (player.getState() == State.JUMPING1) {	//SECOND JUMP
					player.setYSpeed(-15);
					player.setState(State.JUMPING2);
					saveState = State.JUMPING2;
				}
				
			}
			
			else if (Listener.keys.contains(KeyCode.DOWN) && player.getState() != State.JUMPING1 && player.getState() != State.JUMPING2) {
				player.slide();
			}
			
			else if (!Listener.keys.contains(KeyCode.DOWN)) {
				player.getCanvas().setRotate(0);
				player.getCanvas().setTranslateX(player.getPosition().first);
				player.getCanvas().setTranslateY(player.getPosition().second);
				player.setState(saveState);
			}
			
			if (Listener.keys.contains(KeyCode.Q) && Container.getContainer().getFireBallList().size() < 5) {
				Listener.keys.remove(KeyCode.Q);
				Fireball fireball = new Fireball(new Pair(Player.PLAYER_XPOS, player.getPosition().second + Player.PLAYER_HEIGHT / 2), 
					new Pair(Fireball.SKILL_WIDTH, Fireball.SKILL_HEIGHT));
				Container.getContainer().add(fireball);
			}
			
			player.moveY();
			player.decreaseHp(0.05);		//Lost hp from moving
		}
		
		try {
			for (Fireball fireball : Container.getContainer().getFireBallList()) {
				fireball.moveX();
				if (fireball.getPosition().first >= Main.SCREEN_WIDTH) {
					Container.getContainer().remove(fireball);
				}
			}
		} catch (Exception e) {}
		
	}
	
	public static void checkCollision() {
		for (Player player : Container.getContainer().getPlayerList()) {
			
			if (player.getHitState() == PowerState.NORMAL) {
				
				for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
					
					if (Math.abs(player.getPosition().first + Player.PLAYER_WIDTH - obstacle.getPosition().first - Obstacle.OBSTACLE_WIDTH / 2) < -obstacle.getXSpeed()) {
						
						if (obstacle.isAir()) {
							if (player.getState() != State.SLIDING)
							player.decreaseHp(10);
						}
						
						else if (player.getPosition().second + Player.PLAYER_HEIGHT > Map.FLOOR_HEIGHT - obstacle.getHeight()) {
							player.decreaseHp(10);
						}
					}
				}
			}
		}
	}
	
	public static void animate() {
		animateAll();
	}
	
	private static void animateAll() {
		for (Player player : Container.getContainer().getPlayerList()) {
			player.changeImage();
		}
		
		for (Fireball fireball : Container.getContainer().getFireBallList()) {
			fireball.changeImage();
		}
	}
	
}
