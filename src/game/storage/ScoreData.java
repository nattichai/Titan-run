package game.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.model.Score;
import utility.ResourceManager;

public class ScoreData implements Serializable {

	private static final long serialVersionUID = -7497087449864406759L;

	public List<Score> data;

	public ScoreData() {
		data = new ArrayList<>();
	}

	public static void addData(String name, double score, double mode) {
		ScoreData scoreData;
		try {
			scoreData = (ScoreData) ResourceManager.load("ScoreData.save");
		} catch (Exception e) {
			scoreData = new ScoreData();
		}
		scoreData.data.add(new Score(name, score, mode));
		scoreData.data.sort((s1, s2) -> (int) (s2.score - s1.score));

		try {
			ResourceManager.save(scoreData, "ScoreData.save");
		} catch (Exception e) {
		}
	}

	public static List<Score> getData() {
		try {
			return ((ScoreData) ResourceManager.load("ScoreData.save")).data;
		} catch (Exception e) {
		}
		return null;
	}
}
