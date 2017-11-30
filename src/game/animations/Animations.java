package game.animations;

import entity.characters.Monster;
import entity.characters.Player;
import entity.effect.Effect;
import entity.item.Item;
import entity.skill.Skill;
import game.model.Model;
import game.property.State;
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
			if (player.getState() == State.RUNNING) {
				player.changeImage();
			} else if (player.getState() == State.STILL) {
				player.setCurrentAnimation(5);
				player.draw();
			} else if (player.getState() == State.JUMPING || player.getState() == State.SLIDING) {
				player.setCurrentAnimation(8);
				player.draw();
			}
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

		for (Effect effect : Model.getContainer().getEffectList()) {
			effect.changeImage();
		}
	}

	public static Timeline getTimerAnimation() {
		return timerAnimation;
	}

}
