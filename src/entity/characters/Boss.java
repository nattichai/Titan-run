package entity.characters;

public class Boss extends Monster {

	public Boss(double x, double y, int idx) {
		super(x, y, idx);

		userInterface.getHpBar().setPrefSize(300, 15);
	}

}
