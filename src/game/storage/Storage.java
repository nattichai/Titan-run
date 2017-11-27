package game.storage;

import entity.map.Map;
import entity.skill.Darkspear;
import entity.skill.Fireball;
import entity.skill.Meteor;
import entity.skill.Skill;
import entity.skill.Thunderbolt;
import game.property.Hitbox;
import game.property.PowerState;
import game.property.Side;
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
	public Side side; // side
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
		player.side = Side.PLAYER;
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
		pikachu.hp = 100;
		pikachu.maxHp = 100;
		pikachu.atk = 10;
		pikachu.skill = new Thunderbolt();
		pikachu.side = Side.MONSTER;
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
		spearman.hp = 100;
		spearman.maxHp = 100;
		spearman.atk = 10;
		spearman.skill = new Darkspear();
		spearman.side = Side.MONSTER;
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
		sorcerer.hp = 100;
		sorcerer.maxHp = 100;
		sorcerer.atk = 10;
		sorcerer.skill = new Meteor();
		sorcerer.side = Side.MONSTER;
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
		shaman.speedX = -18;
		shaman.speedY = 0;
		shaman.accelX = 0.19;
		shaman.accelY = Map.GRAVITY;
		shaman.hp = 100;
		shaman.maxHp = 100;
		shaman.atk = 10;
		shaman.skill = new Fireball();
		shaman.side = Side.MONSTER;
		shaman.powerState = PowerState.NORMAL;

		Storage slime = new Storage();
		slime.nImage = 6;
		slime.images = new Image[slime.nImage];
		for (int i = 0; i < slime.nImage; ++i) {
			slime.images[i] = new Image("images/character/character6_" + i + ".png");
		}
		slime.width = 300;
		slime.height = 300;
		slime.hb = new Hitbox(120, 90, 60, 160);
		slime.speedX = -18;
		slime.speedY = 0;
		slime.accelX = 0.19;
		slime.accelY = Map.GRAVITY;
		slime.hp = 100;
		slime.maxHp = 100;
		slime.atk = 10;
		slime.skill = new Fireball();
		slime.side = Side.MONSTER;
		slime.powerState = PowerState.NORMAL;

		characters[1] = player;
		characters[2] = pikachu;
		characters[3] = spearman;
		characters[4] = sorcerer;
		characters[5] = shaman;
		characters[6] = slime;
	}
}
