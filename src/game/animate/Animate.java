package game.animate;

import game.model.Model;
import game.model.entity.characters.monster.Monster;
import game.model.entity.characters.player.Player;
import game.model.entity.item.Item;
import game.model.entity.skill.Skill;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Animate {
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
