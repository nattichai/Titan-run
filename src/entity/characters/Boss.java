package entity.characters;

public class Boss extends Characters {

	public Boss(double x, double y, int idx) {
		super(x, y, idx, y);

		userInterface.getHpBar().setPrefSize(300, 15);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void injured() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void affectTo(Characters e) {
		// TODO Auto-generated method stub

	}

}
