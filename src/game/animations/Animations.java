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

	public void startAnimation() {
		new Thread(this::animationLoop, "Game Animation Thread").start();
	}

	public void stopAnimation() {
		if (timerAnimation != null)
			timerAnimation.stop();
	}

	private void animationLoop() {
		timerAnimation = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			updateAnimation();
		}));
		timerAnimation.setCycleCount(Animation.INDEFINITE);
		timerAnimation.play();
	}

	public static void updateAnimation() {
		animateAll();
	}

	private static void animateAll() {
		for (Player player : Model.getContainer().getPlayerList()) {
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
