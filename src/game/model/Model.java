package game.model;

import java.util.ArrayList;

import entity.Entity;
import entity.characters.Monster;
import entity.characters.Player;
import entity.gui.GUI;
import entity.item.HealthPotion;
import entity.item.Item;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.Obstacle;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Shield;
import entity.skill.Skill;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import game.storage.Storage;
import input.GameHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class Model {
	private static Model container;

	private Pane mapPane;
	private Pane playerPane;
	private Pane obstaclePane;
	private Pane skillPane;
	private Pane guiPane;
	private Pane itemPane;
	private Pane monsterPane;

	private static Map map;
	private Player player;
	private ArrayList<Obstacle> obstacleList;
	private ArrayList<Skill> skillList;
	private ArrayList<GUI> guiList;
	private ArrayList<Item> itemList;
	private ArrayList<Monster> monsterList;

	public Model() {
		mapPane = new Pane();
		playerPane = new Pane();
		obstaclePane = new Pane();
		skillPane = new Pane();
		guiPane = new Pane();
		itemPane = new Pane();
		monsterPane = new Pane();

		obstacleList = new ArrayList<>();
		skillList = new ArrayList<>();
		guiList = new ArrayList<>();
		itemList = new ArrayList<>();
		monsterList = new ArrayList<>();
	}

	public static void initialize() {
		container = new Model();
		container.add(map);
		container.add(new Player(90, Map.FLOOR_HEIGHT - 200, 120, 200));
		GameHandler.setPlayer();
		new Storage();
		new Map();
		new Shield();
		new Fireball();
		new Lightning();
		new Thunderbolt();
		new Meteor();
		new Slashy();
		new Jelly();
		new HealthPotion();
	}

	public void add(Object object) {
		Canvas canvas = null;
		if (object instanceof Entity) {
			canvas = ((Entity) object).getCanvas();
		}
		if (object instanceof Obstacle) {
			obstacleList.add((Obstacle) object);
			obstaclePane.getChildren().add(canvas);
		}

		else if (object instanceof Map) {
			map = (Map) object;
			mapPane.getChildren().add(canvas);
		}

		else if (object instanceof Player) {
			player = (Player) object;
			playerPane.getChildren().addAll(canvas, ((Player) object).getUserInterface().getHpBar(),
					((Player) object).getUserInterface().getManaBar());
		}

		else if (object instanceof Skill) {
			skillList.add((Skill) object);
			skillPane.getChildren().add(canvas);
		}

		else if (object instanceof GUI) {
			guiList.add((GUI) object);
			guiPane.getChildren().add(canvas);
		}

		else if (object instanceof Item) {
			itemList.add((Item) object);
			itemPane.getChildren().add(canvas);
		}

		else if (object instanceof Monster) {
			monsterList.add((Monster) object);
			monsterPane.getChildren().addAll(canvas, ((Monster) object).getUserInterface().getHpBar());
		}
	}

	public void remove(Object object) {
		Canvas canvas = ((Entity) object).getCanvas();
		if (object instanceof Obstacle) {
			obstacleList.remove((Obstacle) object);
			obstaclePane.getChildren().remove(canvas);
		}

		else if (object instanceof Map) {
			mapPane.getChildren().remove(canvas);
		}

		else if (object instanceof Player) {
			playerPane.getChildren().removeAll(canvas, ((Player) object).getUserInterface().getHpBar(),
					((Player) object).getUserInterface().getManaBar());
			player = null;
		}

		else if (object instanceof Skill) {
			skillList.remove((Skill) object);
			skillPane.getChildren().remove(canvas);
		}

		else if (object instanceof GUI) {
			guiList.remove((GUI) object);
			guiPane.getChildren().remove(canvas);
		}

		else if (object instanceof Item) {
			itemList.remove((Item) object);
			itemPane.getChildren().remove(canvas);
		}

		else if (object instanceof Monster) {
			monsterList.add((Monster) object);
			monsterPane.getChildren().addAll(canvas, ((Monster) object).getUserInterface().getHpBar());
		}
	}

	public void clearAllData() {
		monsterList.forEach(m -> m.getCanvas().setOpacity(0));
		skillList.removeIf(s -> s.isDead());
	}

	public static Model getContainer() {
		return container;
	}

	public Pane getPlayerPane() {
		return playerPane;
	}

	public Pane getMapPane() {
		return mapPane;
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

	public Pane getGuiPane() {
		return guiPane;
	}

	public ArrayList<GUI> getGuiList() {
		return guiList;
	}

	public Pane getItemPane() {
		return itemPane;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public Pane getMonsterPane() {
		return monsterPane;
	}

	public ArrayList<Monster> getMonsterList() {
		return monsterList;
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

}
