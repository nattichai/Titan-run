package game.model;

import java.io.Serializable;

public class Score implements Serializable {
	private static final long serialVersionUID = 7843597108580670257L;

	public String name;
	public double score;

	public Score(String name, double score) {
		this.name = name;
		this.score = score;
	}
}
