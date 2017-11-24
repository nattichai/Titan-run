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
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.obstacle.Obstacle;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Shield;
import entity.skill.Skill;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import game.GameMain;
import game.storage.Storage;
import game.updater.Updater;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import window.SceneManager;

public class Model {
	private static Model container;

	private Pane mapPane;
	private Pane playerPane;
	private Pane obstaclePane;
	private Pane skillPane;
	private Pane guiPane;
	private Pane itemPane;
	private Pane monsterPane;

	private ArrayList<Map> mapList;
	private ArrayList<Player> playerList;
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

		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
		obstacleList = new ArrayList<>();
		skillList = new ArrayList<>();
		guiList = new ArrayList<>();
		itemList = new ArrayList<>();
		monsterList = new ArrayList<>();

		GameMain.getGamePane().getChildren().addAll(mapPane, obstaclePane, itemPane, monsterPane, playerPane, skillPane,
				guiPane);
	}

	public static void initialize() {
		container = new Model();
		new Updater();
		new Storage();
		new Shield();
		new Fireball();
		new Lightning();
		new Thunderbolt();
		new Meteor();
		new Slashy();
		new Jelly();
		new HealthPotion();
		new AirObstacle();
		new GroundObstacle();
		new HoleObstacle();

		Map map = new Map(0, 0, SceneManager.SCREEN_WIDTH * 2, SceneManager.SCREEN_HEIGHT);
		container.add(map);

		Player player = new Player(90, Map.FLOOR_HEIGHT - 200, 120, 200);
		Model.getContainer().add(player);

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
			mapList.add((Map) object);
			mapPane.getChildren().add(canvas);
		}

		else if (object instanceof Player) {
			playerList.add((Player) object);
			playerPane.getChildren().addAll(canvas, ((Player) object).getHpBar(), ((Player) object).getManaBar());
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
			monsterPane.getChildren().addAll(canvas, ((Monster) object).getHpBar());
		}
	}

	public void remove(Object object) {
		Canvas canvas = ((Entity) object).getCanvas();
		if (object instanceof Obstacle) {
			obstacleList.remove((Obstacle) object);
			obstaclePane.getChildren().remove(canvas);
		}

		else if (object instanceof Map) {
			mapList.remove((Map) object);
			mapPane.getChildren().remove(canvas);
		}

		else if (object instanceof Player) {
			playerList.remove((Player) object);
			playerPane.getChildren().removeAll(canvas, ((Player) object).getHpBar(), ((Player) object).getManaBar());
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
			monsterPane.getChildren().addAll(canvas, ((Monster) object).getHpBar());
		}
	}

	public void clearAllData() {
		monsterList.forEach(m -> m.die());
		skillList.forEach(s -> s.isDead());
		mapPane.getChildren().clear();
		playerPane.getChildren().clear();
		obstaclePane.getChildren().clear();
		skillPane.getChildren().clear();
		guiPane.getChildren().clear();
		itemPane.getChildren().clear();
		monsterPane.getChildren().clear();
		mapList.clear();
		playerList.clear();
		obstacleList.clear();
		skillList.clear();
		guiList.clear();
		itemList.clear();
		monsterList.clear();
		GameMain.getGamePane().getChildren().clear();
	}

	public static Model getContainer() {
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

}
