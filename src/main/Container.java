package main;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import entity.Entity;
import entity.map.*;
import entity.player.Player;
import utility.*;

public class Container {
	private static final Container container = new Container();
	
	private Pane mapPane;
	private Pane playerPane;
	private Pane obstaclePane;
	
	private ArrayList<Map> mapList;
	private ArrayList<Player> playerList;
	private ArrayList<Obstacle> obstacleList;

	public Container() {
		mapPane = new Pane();
		playerPane = new Pane();
		obstaclePane = new Pane();
		
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
		obstacleList = new ArrayList<>();
	}
	
	public void initialize() {
		Map map = new Map(new Pair(0, 0), new Pair(1333 * 2, 750));
		container.add(map);
		
		Obstacle obstacle = new Obstacle(new Pair(0, 0), new Pair(Main.SCREEN_WIDTH * 2, Main.SCREEN_HEIGHT));
		container.add(obstacle);
		
//		Rectangle r = new Rectangle(0, Map.FLOOR_HEIGHT, Main.SCREEN_WIDTH, Map.FLOOR_HEIGHT);
//		r.setFill(Color.DIMGRAY);
//		container.add(r);
		
		Player player = new Player(new Pair(Player.PLAYER_XPOS - Player.PLAYER_WIDTH / 2, Map.FLOOR_HEIGHT - Player.PLAYER_HEIGHT),
				new Pair(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
		container.add(player);
	}
	
	public void add(Object object) {
		if (object instanceof Map) {
			mapList.add((Map) object);
			mapPane.getChildren().add(((Entity) object).getCanvas());
		} else if (object instanceof Obstacle) {
			obstacleList.add((Obstacle) object);
			obstaclePane.getChildren().add(((Entity) object).getCanvas());
		} else if (object instanceof Rectangle) {
			mapPane.getChildren().add((Rectangle) object);
		} else if (object instanceof Player) {
			playerList.add((Player) object);
			playerPane.getChildren().addAll(((Entity) object).getCanvas(), ((Player) object).getHpBar());
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
	
}
