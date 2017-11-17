package main;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import entity.Entity;
import entity.map.*;
import entity.obstacle.Obstacle;
import entity.player.Player;
import entity.skill.Fireball;
import utility.*;

public class Container {
	private static final Container container = new Container();
	
	private Pane mapPane;
	private Pane playerPane;
	private Pane obstaclePane;
	private Pane fireBallPane;
	
	private ArrayList<Map> mapList;
	private ArrayList<Player> playerList;
	private ArrayList<Obstacle> obstacleList;
	private ArrayList<Fireball> fireBallList;

	public Container() {
		mapPane = new Pane();
		playerPane = new Pane();
		obstaclePane = new Pane();
		fireBallPane = new Pane();
		
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
		obstacleList = new ArrayList<>();
		fireBallList = new ArrayList<>();
	}
	
	public void initialize() {
		Map map = new Map(new Pair(0, 0), new Pair(Main.SCREEN_WIDTH * 3, Main.SCREEN_HEIGHT));
		container.add(map);
		
		Player player = new Player(new Pair(Player.PLAYER_XPOS - Player.PLAYER_WIDTH / 2, Map.FLOOR_HEIGHT - Player.PLAYER_HEIGHT),
				new Pair(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
		container.add(player);
	}
	
	public void add(Object object) {
		if (object instanceof Obstacle) {
			obstacleList.add((Obstacle) object);
			obstaclePane.getChildren().add(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Map) {
			mapList.add((Map) object);
			mapPane.getChildren().add(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Player) {
			playerList.add((Player) object);
			playerPane.getChildren().addAll(((Entity) object).getCanvas(), ((Player) object).getHpBar());
		}
		
		else if (object instanceof Fireball) {
			fireBallList.add((Fireball) object);
			fireBallPane.getChildren().add(((Entity) object).getCanvas());
		}
	}
	
	public void remove(Object object) {
		if (object instanceof Obstacle) {
			obstacleList.remove((Obstacle) object);
			obstaclePane.getChildren().remove(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Map) {
			mapList.remove((Map) object);
			mapPane.getChildren().remove(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Player) {
			playerList.remove((Player) object);
			playerPane.getChildren().removeAll(((Entity) object).getCanvas(), ((Player) object).getHpBar());
		}
		
		else if (object instanceof Fireball) {
			fireBallList.remove((Fireball) object);
			fireBallPane.getChildren().remove(((Entity) object).getCanvas());
		}
	}
	
	public static Container getContainer() {
		return container;
	}

	public Pane getPlayerPane() {
		return playerPane;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public Pane getMapPane() {
		return mapPane;
	}

	public ArrayList<Map> getMapList() {
		return mapList;
	}

	public Pane getObstaclePane() {
		return obstaclePane;
	}

	public ArrayList<Obstacle> getObstacleList() {
		return obstacleList;
	}

	public Pane getFireBallPane() {
		return fireBallPane;
	}

	public ArrayList<Fireball> getFireBallList() {
		return fireBallList;
	}
	
}
