package main;

import java.util.ArrayList;

import entity.Entity;
import entity.item.HealthPotion;
import entity.item.Item;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.Obstacle;
import entity.player.Player;
import entity.skill.Fireball;
import entity.skill.Meteor;
import entity.skill.Skill;
import entity.textmodel.TextModel;
import javafx.scene.layout.Pane;
import utility.Pair;

public class Container {
	private static Container container;
	
	private Pane mapPane;
	private Pane playerPane;
	private Pane obstaclePane;
	private Pane skillPane;
	private Pane textPane;
	private Pane itemPane;
	
	private ArrayList<Map> mapList;
	private ArrayList<Player> playerList;
	private ArrayList<Obstacle> obstacleList;
	private ArrayList<Skill> skillList;
	private ArrayList<TextModel> textList;
	private ArrayList<Item> itemList;

	public Container() {
		mapPane = new Pane();
		playerPane = new Pane();
		obstaclePane = new Pane();
		skillPane = new Pane();
		textPane = new Pane();
		itemPane = new Pane();
		
		Main.getRoot().getChildren().clear();
		Main.getRoot().getChildren().addAll(mapPane, obstaclePane, itemPane, playerPane, skillPane, textPane);
		
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
		obstacleList = new ArrayList<>();
		skillList = new ArrayList<>();
		textList = new ArrayList<>();
		itemList = new ArrayList<>();
	}
	
	public static void initialize() {
		container = new Container();
		
		Map map = new Map(new Pair(0, 0), new Pair(Main.SCREEN_WIDTH * 3, Main.SCREEN_HEIGHT));
		container.add(map);
		
		Player player = new Player(new Pair(Player.PLAYER_POSITON_X, Map.FLOOR_HEIGHT - Player.PLAYER_HEIGHT),
				new Pair(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT));
		container.add(player);
		
		new Fireball(new Pair(0, 0), new Pair(0, 0));
		new Meteor(new Pair(0, 0), new Pair(0, 0));
		new Jelly(new Pair(0, 0), new Pair(0, 0));
		new HealthPotion(new Pair(0, 0), new Pair(0, 0));
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
			playerPane.getChildren().addAll(((Entity) object).getCanvas(), ((Player) object).getHpBar(), ((Player) object).getScoreText());
		}
		
		else if (object instanceof Skill) {
			skillList.add((Skill) object);
			skillPane.getChildren().add(((Entity) object).getCanvas());
		}
		
		else if (object instanceof TextModel) {
			textList.add((TextModel) object);
			textPane.getChildren().add(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Item) {
			itemList.add((Item) object);
			itemPane.getChildren().add(((Entity) object).getCanvas());
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
		
		else if (object instanceof Skill) {
			skillList.remove((Skill) object);
			skillPane.getChildren().remove(((Entity) object).getCanvas());
		}
		
		else if (object instanceof TextModel) {
			textList.remove((TextModel) object);
			textPane.getChildren().remove(((Entity) object).getCanvas());
		}
		
		else if (object instanceof Item) {
			itemList.remove((Item) object);
			itemPane.getChildren().remove(((Entity) object).getCanvas());
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

	public Pane getSkillPane() {
		return skillPane;
	}

	public ArrayList<Skill> getSkillList() {
		return skillList;
	}

	public Pane getTextPane() {
		return textPane;
	}

	public ArrayList<TextModel> getTextList() {
		return textList;
	}

	public Pane getItemPane() {
		return itemPane;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}
	
}
