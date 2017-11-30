package game.storage;

import entity.map.Map;
import entity.skill.Darkspear;
import entity.skill.Fireball;
import entity.skill.Meteor;
import entity.skill.Skill;
import entity.skill.Thunderbolt;
import game.property.Direction;
import game.property.Hitbox;
import game.property.PowerState;
import javafx.scene.image.Image;

public class Storage {
	public int nImage; // number of images
	public Image[] images; // image array
	public double width, height; // character size
	public Hitbox hb; // hitbox area
	public double speedX, speedY; // initial speed
	public double accelX, accelY; // initial accel
	public double stayPositionX; // character will stay at this position
	public double waitTime; // stay time then move out
	public double hp, maxHp; // hit point
	public double atk; // attack
	public Skill skill; // skill
	public Direction imageDirection; // IMAGE DIRECTION
	public PowerState powerState; // power state

	public static final Storage[] characters = new Storage[20];
	static {
		Storage player = new Storage();
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
		player.imageDirection = Direction.RIGHT;
		player.powerState = PowerState.NORMAL;

		Storage pikachu = new Storage();
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
		pikachu.skill = new Thunderbolt();
		pikachu.imageDirection = Direction.LEFT;
		pikachu.powerState = PowerState.NORMAL;

		Storage spearman = new Storage();
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
		spearman.skill = new Darkspear();
		spearman.imageDirection = Direction.LEFT;
		spearman.powerState = PowerState.NORMAL;

		Storage sorcerer = new Storage();
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
		sorcerer.skill = new Meteor();
		sorcerer.imageDirection = Direction.LEFT;
		sorcerer.powerState = PowerState.NORMAL;

		Storage shaman = new Storage();
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
		shaman.skill = new Fireball();
		shaman.imageDirection = Direction.LEFT;
		shaman.powerState = PowerState.NORMAL;

		Storage slime = new Storage();
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
		slime.skill = new Fireball();
		slime.imageDirection = Direction.LEFT;
		slime.powerState = PowerState.NORMAL;

		Storage robotek = new Storage();
		robotek.nImage = 96;
		robotek.images = new Image[robotek.nImage];
		for (int i = 0; i < robotek.nImage; ++i) {
			robotek.images[i] = new Image("images/boss/boss1_" + i + ".png");
		}
		robotek.width = 400;
		robotek.height = 164;
		robotek.hb = new Hitbox(160, 32, 80, 100);
		robotek.speedX = 0;
		robotek.speedY = 0;
		robotek.accelX = 0;
		robotek.accelY = 0;
		robotek.hp = 1000;
		robotek.maxHp = 1000;
		robotek.atk = 24;
		robotek.skill = new Fireball();
		robotek.imageDirection = Direction.LEFT;
		robotek.powerState = PowerState.NORMAL;

		characters[1] = player;
		characters[2] = pikachu;
		characters[3] = spearman;
		characters[4] = sorcerer;
		characters[5] = shaman;
		characters[6] = slime;
		characters[7] = robotek;
	}
}
