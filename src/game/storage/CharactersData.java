package game.storage;

import game.model.Map;
import game.property.Direction;
import game.property.Hitbox;
import game.property.PowerState;
import javafx.scene.image.Image;

public class CharactersData {
	private int nImage;
	private Image[] images;
	private double width, height;
	private Hitbox hb;
	private double speedX, speedY;
	private double accelX, accelY;
	private double hp, maxHp;
	private double atk;
	private int skillIndex;
	private Direction imageDirection;
	private PowerState powerState;

	public static final CharactersData[] data = new CharactersData[20];
	static {
		CharactersData player = new CharactersData();
		player.nImage = 10;
		player.images = new Image[player.nImage];
		for (int i = 0; i < player.nImage; ++i) {
			player.images[i] = new Image("images/character/character1_" + i + ".png");
		}
		player.width = 120;
		player.height = 200;
		player.hb = new Hitbox(20, 20, 100, 180);
		player.speedX = -12;
		player.speedY = 0;
		player.accelX = 0.15;
		player.accelY = Map.GRAVITY;
		player.hp = 100;
		player.maxHp = 100;
		player.atk = 10;
		player.skillIndex = 4;
		player.imageDirection = Direction.RIGHT;
		player.powerState = PowerState.NORMAL;

		CharactersData pikachu = new CharactersData();
		pikachu.nImage = 8;
		pikachu.images = new Image[pikachu.nImage];
		for (int i = 0; i < pikachu.nImage; ++i) {
			pikachu.images[i] = new Image("images/character/character2_" + i + ".png");
		}
		pikachu.width = 200;
		pikachu.height = 130;
		pikachu.hb = new Hitbox(0, 40, 120, 90);
		pikachu.speedX = -15;
		pikachu.speedY = -10;
		pikachu.accelX = 0.19;
		pikachu.accelY = Map.GRAVITY;
		pikachu.hp = 96;
		pikachu.maxHp = 96;
		pikachu.atk = 12;
		pikachu.skillIndex = 2;
		pikachu.imageDirection = Direction.LEFT;
		pikachu.powerState = PowerState.NORMAL;

		CharactersData spearman = new CharactersData();
		spearman.nImage = 12;
		spearman.images = new Image[spearman.nImage];
		for (int i = 0; i < spearman.nImage; ++i) {
			spearman.images[i] = new Image("images/character/character3_" + i + ".png");
		}
		spearman.width = 300;
		spearman.height = 300;
		spearman.hb = new Hitbox(90, 60, 120, 180);
		spearman.speedX = -16;
		spearman.speedY = -15;
		spearman.accelX = 0.2;
		spearman.accelY = Map.GRAVITY;
		spearman.hp = 122;
		spearman.maxHp = 122;
		spearman.atk = 8;
		spearman.skillIndex = 6;
		spearman.imageDirection = Direction.LEFT;
		spearman.powerState = PowerState.NORMAL;

		CharactersData sorcerer = new CharactersData();
		sorcerer.nImage = 23;
		sorcerer.images = new Image[sorcerer.nImage];
		for (int i = 0; i < sorcerer.nImage; ++i) {
			sorcerer.images[i] = new Image("images/character/character4_" + i + ".png");
		}
		sorcerer.width = 300;
		sorcerer.height = 300;
		sorcerer.hb = new Hitbox(120, 120, 60, 130);
		sorcerer.speedX = -15;
		sorcerer.speedY = -25;
		sorcerer.accelX = 0.15;
		sorcerer.accelY = Map.GRAVITY;
		sorcerer.hp = 76;
		sorcerer.maxHp = 76;
		sorcerer.atk = 17;
		sorcerer.skillIndex = 5;
		sorcerer.imageDirection = Direction.LEFT;
		sorcerer.powerState = PowerState.NORMAL;

		CharactersData shaman = new CharactersData();
		shaman.nImage = 47;
		shaman.images = new Image[shaman.nImage];
		for (int i = 0; i < shaman.nImage; ++i) {
			shaman.images[i] = new Image("images/character/character5_" + i + ".png");
		}
		shaman.width = 300;
		shaman.height = 300;
		shaman.hb = new Hitbox(120, 90, 60, 160);
		shaman.speedX = -16;
		shaman.speedY = 0;
		shaman.accelX = 0.19;
		shaman.accelY = Map.GRAVITY;
		shaman.hp = 104;
		shaman.maxHp = 104;
		shaman.atk = 68;
		shaman.skillIndex = 0;
		shaman.imageDirection = Direction.LEFT;
		shaman.powerState = PowerState.NORMAL;

		CharactersData slime = new CharactersData();
		slime.nImage = 6;
		slime.images = new Image[slime.nImage];
		for (int i = 0; i < slime.nImage; ++i) {
			slime.images[i] = new Image("images/character/character6_" + i + ".png");
		}
		slime.width = 195;
		slime.height = 225;
		slime.hb = new Hitbox(10, 75, 100, 150);
		slime.speedX = -17;
		slime.speedY = 0;
		slime.accelX = 0.19;
		slime.accelY = Map.GRAVITY;
		slime.hp = 199;
		slime.maxHp = 199;
		slime.atk = 8;
		slime.skillIndex = 0;
		slime.imageDirection = Direction.LEFT;
		slime.powerState = PowerState.NORMAL;

		CharactersData robotek = new CharactersData();
		robotek.nImage = 96;
		robotek.images = new Image[robotek.nImage];
		for (int i = 0; i < robotek.nImage; ++i) {
			robotek.images[i] = new Image("images/boss/boss1_" + i + ".png");
		}
		robotek.width = 400;
		robotek.height = 164;
		robotek.hb = new Hitbox(75, 32, 250, 100);
		robotek.speedX = 0;
		robotek.speedY = 0;
		robotek.accelX = 0;
		robotek.accelY = 0;
		robotek.hp = 1000;
		robotek.maxHp = 1000;
		robotek.atk = 24;
		robotek.skillIndex = 0;
		robotek.imageDirection = Direction.LEFT;
		robotek.powerState = PowerState.NORMAL;

		data[1] = player;
		data[2] = pikachu;
		data[3] = spearman;
		data[4] = sorcerer;
		data[5] = shaman;
		data[6] = slime;
		data[7] = robotek;
	}

	public int getnImage() {
		return nImage;
	}

	public Image[] getImages() {
		return images;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Hitbox getHb() {
		return hb;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public double getAccelX() {
		return accelX;
	}

	public double getAccelY() {
		return accelY;
	}

	public double getHp() {
		return hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public double getAtk() {
		return atk;
	}

	public int getSkillIndex() {
		return skillIndex;
	}

	public Direction getImageDirection() {
		return imageDirection;
	}

	public PowerState getPowerState() {
		return powerState;
	}
}
