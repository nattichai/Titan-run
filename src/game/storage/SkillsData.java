package game.storage;

import java.util.Random;

import game.model.Map;
import game.property.Hitbox;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import scene.SceneManager;

public class SkillsData {
	private int nImage;
	private Image[] images;
	private double width, height;
	private Hitbox hb;
	private double speedX, speedY;
	private double accelX, accelY;
	private double damage;
	private double cooldown;
	private double collisionDelay;
	private int currentAnimation;
	private int lastAnimation;
	private boolean isStickToOwner;
	private boolean isOnceCollision;
	private AudioClip skillfx;

	// default's skill
	public SkillsData() {
		hb = null;
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = 0;
		collisionDelay = 0;
		currentAnimation = 0;
		lastAnimation = 1000;
		isStickToOwner = false;
		isOnceCollision = false;
	}

	public static final SkillsData[] data = new SkillsData[20];
	static {
		SkillsData fireball = new SkillsData();

		fireball.nImage = 10;
		fireball.images = new Image[fireball.nImage];
		for (int i = 0; i < fireball.nImage; ++i) {
			fireball.images[i] = new Image("images/skill/fireball/fireball" + i + ".png");
		}
		fireball.width = 50;
		fireball.height = 30;
		fireball.speedX = 20;
		fireball.damage = 0.5;
		fireball.cooldown = 0.2;
		fireball.currentAnimation = new Random().nextInt(fireball.nImage);
		fireball.isOnceCollision = true;
		fireball.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Fireball.wav").toString());
		;

		SkillsData lightning = new SkillsData();
		lightning.nImage = 12;
		lightning.images = new Image[lightning.nImage];
		for (int i = 0; i < lightning.nImage; ++i) {
			lightning.images[i] = new Image("images/skill/lightning/lightning" + i + ".png");
		}
		lightning.width = 100;
		lightning.height = Map.FLOOR_HEIGHT;
		lightning.damage = 0.65;
		lightning.cooldown = 8;
		lightning.lastAnimation = 11;
		lightning.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Lightning.wav").toString());
		;

		SkillsData thunderbolt = new SkillsData();
		thunderbolt.nImage = 38;
		thunderbolt.images = new Image[thunderbolt.nImage];
		for (int i = 0; i < thunderbolt.nImage; ++i) {
			thunderbolt.images[i] = new Image("images/skill/thunderbolt/thunderbolt" + i + ".png");
		}
		thunderbolt.width = 600;
		thunderbolt.height = SceneManager.SCREEN_HEIGHT;
		thunderbolt.hb = new Hitbox(200, 0, 200, thunderbolt.height);
		thunderbolt.speedX = 6;
		thunderbolt.damage = 1.5;
		thunderbolt.cooldown = 15;
		thunderbolt.collisionDelay = 100;
		thunderbolt.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Thunderbolt.wav").toString());
		;

		SkillsData slashy = new SkillsData();
		slashy.nImage = 20;
		slashy.images = new Image[slashy.nImage];
		for (int i = 0; i < slashy.nImage; ++i) {
			slashy.images[i] = new Image("images/skill/slashy/slashy" + i + ".png");
		}
		slashy.width = SceneManager.SCREEN_WIDTH;
		slashy.height = SceneManager.SCREEN_HEIGHT;
		slashy.damage = 2.4;
		slashy.cooldown = 24;
		slashy.collisionDelay = 60;
		slashy.lastAnimation = 19;
		slashy.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Slashy.wav").toString());
		;

		SkillsData shield = new SkillsData();
		shield.nImage = 63;
		shield.images = new Image[shield.nImage];
		for (int i = 0; i < shield.nImage; ++i) {
			shield.images[i] = new Image("images/skill/shield/shield" + i + ".png");
		}
		shield.width = 400;
		shield.height = 300;
		shield.hb = new Hitbox(50, 0, 300, 300);
		shield.cooldown = 0.1;
		shield.isStickToOwner = true;
		shield.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Shield.wav").toString());
		;

		SkillsData meteor = new SkillsData();
		meteor.nImage = 60;
		meteor.images = new Image[meteor.nImage];
		for (int i = 0; i < meteor.nImage; ++i) {
			meteor.images[i] = new Image("images/skill/meteor/meteor" + i + ".png");
		}
		meteor.width = 800;
		meteor.height = 270;
		meteor.hb = new Hitbox(150, 235, 200, 200);
		meteor.speedX = 12;
		meteor.speedY = 9;
		meteor.damage = 2.5;
		meteor.cooldown = 25;
		meteor.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Meteor.wav").toString());
		;

		SkillsData darkspear = new SkillsData();
		darkspear.nImage = 16;
		darkspear.images = new Image[darkspear.nImage];
		for (int i = 0; i < darkspear.nImage; ++i) {
			darkspear.images[i] = new Image("images/skill/darkspear/darkspear" + i + ".png");
		}
		darkspear.width = 600;
		darkspear.height = 600;
		darkspear.hb = new Hitbox(250, 250, 100, 300);
		darkspear.speedX = 20;
		darkspear.damage = 2;
		darkspear.cooldown = 12;
		darkspear.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Darkspear.wav").toString());
		;

		SkillsData drill = new SkillsData();
		drill.nImage = 3;
		drill.images = new Image[drill.nImage];
		for (int i = 0; i < drill.nImage; ++i) {
			drill.images[i] = new Image("images/skill/drill/drill" + i + ".png");
		}
		drill.width = 150;
		drill.height = 75;
		drill.hb = new Hitbox(37.5, 22.5, 75, 30);
		drill.speedX = 15;
		drill.damage = 0.8;
		drill.cooldown = 6;
		drill.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Drill.wav").toString());
		;

		SkillsData beam = new SkillsData();
		beam.nImage = 12;
		beam.images = new Image[beam.nImage];
		for (int i = 0; i < beam.nImage; ++i) {
			beam.images[i] = new Image("images/skill/beam/beam" + i + ".png");
		}
		beam.width = 150;
		beam.height = 600;
		beam.damage = 2;
		beam.cooldown = 10;
		beam.skillfx = new AudioClip(ClassLoader.getSystemResource("sounds/Skillfx/Beam.wav").toString());
		;

		data[0] = fireball;
		data[1] = lightning;
		data[2] = thunderbolt;
		data[3] = slashy;
		data[4] = shield;
		data[5] = meteor;
		data[6] = darkspear;
		data[7] = drill;
		data[8] = beam;
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

	public double getDamage() {
		return damage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public double getCollisionDelay() {
		return collisionDelay;
	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public int getLastAnimation() {
		return lastAnimation;
	}

	public boolean isStickToOwner() {
		return isStickToOwner;
	}

	public boolean isOnceCollision() {
		return isOnceCollision;
	}

	public AudioClip getAudioClip() {
		return skillfx;
	}

}
