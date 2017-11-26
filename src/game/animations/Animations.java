package game.animations;

import entity.characters.Monster;
import entity.characters.Player;
import entity.item.Item;
import entity.skill.Skill;
import game.model.Model;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Animations {
	public static final double ANIMATATION_RATE = 20;
	public static final double LOOP_TIME = 1000 / ANIMATATION_RATE;

	private static Timeline timerAnimation;
	static {
		timerAnimation = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			animateAll();
		}));
		timerAnimation.setCycleCount(Animation.INDEFINITE);
	}
	private static Player player;

	public Animations() {
		player = Model.getContainer().getPlayer();
	}

	public void startAnimation() {
		new Thread(new Runnable() {
			public void run() {
				timerAnimation.play();
			}
		}).start();
	}

	public void pauseAnimation() {
		if (timerAnimation != null) {
			timerAnimation.pause();
		}
	}

	public void continueAnimation() {
		if (timerAnimation != null) {
			timerAnimation.play();
		}
	}

	public void stopAnimation() {
		if (timerAnimation != null)
			timerAnimation.stop();
	}

	private static void animateAll() {
		if (player != null) {
			player.changeImage();
		}

		for (Skill skill : Model.getContainer().getSkillList()) {
			skill.changeImage();
		}

		for (Item item : Model.getContainer().getItemList()) {
			item.changeImage();
		}

		for (Monster monster : Model.getContainer().getMonsterList()) {
			monster.changeImage();
		}
	}

	public static Timeline getTimerAnimation() {
		return timerAnimation;
	}

}
