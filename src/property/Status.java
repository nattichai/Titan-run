package property;

import javafx.scene.image.Image;
import utility.Hitbox;

public class Status {
	public Image[] images;
	public int nImage;
	public double width, height;	//monster size
	public Hitbox hb;				//hitbox area
	public double speedX, speedY;	//initial speed
	public double hp, maxHp;		//hit point
	public double atk;				//attack
	public Side side;				//side
	public PowerState powerState;	//power state
	
	public static final Status[] allStatus = new Status[100];
	static {
		//PIKACHU
		allStatus[2] = new Status();
		allStatus[2].nImage = 8;
		allStatus[2].images = new Image[allStatus[2].nImage];
		for (int i = 0; i < allStatus[2].nImage; ++i) {
			allStatus[2].images[i] = new Image(ClassLoader.getSystemResource("images/character/character2_" + i + ".png").toString());
		}
		allStatus[2].width = 200;
		allStatus[2].height = 130;
		allStatus[2].hb = new Hitbox(0, 40, 120, 90);
		allStatus[2].speedX = -20;
		allStatus[2].speedY = -20;
		allStatus[2].hp = 100;
		allStatus[2].maxHp = 100;
		allStatus[2].atk = 10;
		allStatus[2].side = Side.MONSTER;
		allStatus[2].powerState = PowerState.NORMAL;

		//SPEARMAN
		allStatus[3] = new Status();
		allStatus[3].nImage = 12;
		allStatus[3].images = new Image[allStatus[3].nImage];
		for (int i = 0; i < allStatus[3].nImage; ++i) {
			allStatus[3].images[i] = new Image(ClassLoader.getSystemResource("images/character/character3_" + i + ".png").toString());
		}
		allStatus[3].width = 300;
		allStatus[3].height = 300;
		allStatus[3].hb = new Hitbox(90, 60, 120, 180);
		allStatus[3].speedX = -20;
		allStatus[3].speedY = -20;
		allStatus[3].hp = 100;
		allStatus[3].maxHp = 100;
		allStatus[3].atk = 10;
		allStatus[3].side = Side.MONSTER;
		allStatus[3].powerState = PowerState.NORMAL;

		//SORCERER
		allStatus[4] = new Status();
		allStatus[4].nImage = 23;
		allStatus[4].images = new Image[allStatus[4].nImage];
		for (int i = 0; i < allStatus[4].nImage; ++i) {
			allStatus[4].images[i] = new Image(ClassLoader.getSystemResource("images/character/character4_" + i + ".png").toString());
		}
		allStatus[4].width = 300;
		allStatus[4].height = 300;
		allStatus[4].hb = new Hitbox(120, 120, 60, 130);
		allStatus[4].speedX = -20;
		allStatus[4].speedY = -20;
		allStatus[4].hp = 100;
		allStatus[4].maxHp = 100;
		allStatus[4].atk = 10;
		allStatus[4].side = Side.MONSTER;
		allStatus[4].powerState = PowerState.NORMAL;

		//SHAMAN
		allStatus[5] = new Status();
		allStatus[5].nImage = 47;
		allStatus[5].images = new Image[allStatus[5].nImage];
		for (int i = 0; i < allStatus[5].nImage; ++i) {
			allStatus[5].images[i] = new Image(ClassLoader.getSystemResource("images/character/character5_" + i + ".png").toString());
		}
		allStatus[5].width = 300;
		allStatus[5].height = 300;
		allStatus[5].hb = new Hitbox(120, 90, 60, 160);
		allStatus[5].speedX = -20;
		allStatus[5].speedY = -20;
		allStatus[5].hp = 100;
		allStatus[5].maxHp = 100;
		allStatus[5].atk = 10;
		allStatus[5].side = Side.MONSTER;
		allStatus[5].powerState = PowerState.NORMAL;
		
		
	}
	
	public Status() {}
	
	public Status(double w, double h, double hbX, double hbY, double hbW, double hbH, double spX, double spY, double mHp, double atk, Side side, PowerState pState) {
		width = w;
		height = h;
		hb = new Hitbox(hbX, hbY, hbW, hbH);
		speedX = spX;
		speedY = spY;
		maxHp = mHp;
		this.atk = atk;
		this.side = side;
		powerState = pState;
	}
}
