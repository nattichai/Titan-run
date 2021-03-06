package game.model;

import java.util.ArrayList;

import game.model.character.Monster;
import game.model.character.Player;
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
	private Pane effectPane;

	private static Map map;
	private Player player;
	private ArrayList<Obstacle> obstacleList;
	private ArrayList<Skill> skillList;
	private ArrayList<GUI> guiList;
	private ArrayList<Item> itemList;
	private ArrayList<Monster> monsterList;
	private ArrayList<Effect> effectList;

	public Model() {
		mapPane = new Pane();
		playerPane = new Pane();
		obstaclePane = new Pane();
		skillPane = new Pane();
		guiPane = new Pane();
		itemPane = new Pane();
		monsterPane = new Pane();
		effectPane = new Pane();

		obstacleList = new ArrayList<>();
		skillList = new ArrayList<>();
		guiList = new ArrayList<>();
		itemList = new ArrayList<>();
		monsterList = new ArrayList<>();
		effectList = new ArrayList<>();
	}

	public static void initialize() {
		container = new Model();
		container.add(map);
		container.add(new Player(90, Map.FLOOR_HEIGHT - 200, 1));
		GameHandler.setPlayer();
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
			playerPane.getChildren().add(canvas);
		}

		else if (object instanceof Skill) {
			skillList.add((Skill) object);
			skillPane.getChildren().add(canvas);
			((Skill) object).getAudioClip().play();
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
			monsterPane.getChildren().add(canvas);
		}

		else if (object instanceof Effect) {
			effectList.add((Effect) object);
			effectPane.getChildren().add(canvas);
		}
		
		else if (object instanceof Canvas) {
			guiPane.getChildren().add((Canvas) object);
		}
	}

	public void remove(Object object) {
		Canvas canvas = null;
		if (object instanceof Entity) {
			canvas = ((Entity) object).getCanvas();
		}
		
		if (object instanceof Obstacle) {
			obstacleList.remove((Obstacle) object);
			obstaclePane.getChildren().remove(canvas);
		}

		else if (object instanceof Map) {
			mapPane.getChildren().remove(canvas);
		}

		else if (object instanceof Player) {
			playerPane.getChildren().remove(canvas);
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
			monsterList.remove((Monster) object);
			monsterPane.getChildren().remove(canvas);
		}

		else if (object instanceof Effect) {
			effectList.remove((Effect) object);
			effectPane.getChildren().remove(canvas);
		}
		
		else if (object instanceof Canvas) {
			guiPane.getChildren().remove((Canvas) object);
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

	public Pane getEffectPane() {
		return effectPane;
	}

	public ArrayList<Effect> getEffectList() {
		return effectList;
	}

}
