package io.github.codecube.waterfall.animation;

import java.util.ArrayList;
import java.util.List;

public class AnimPropertySet {
	private static List<AnimPropertySet> currentlyPlaying = new ArrayList<>();
	private static int animationSync = 0;

	private boolean playing = false;
	private int staticListPos = -1;
	private List<AnimatableProperty<?>> properties = new ArrayList<>();
	private Animation currentAnimation;

	public static void updateAllAnimations() {
		animationSync++;
		for (AnimPropertySet set : currentlyPlaying) {
			if (animationSync % set.getCurrentAnimation().getTimePerFrame() == 0) {
				set.getCurrentAnimation().advanceFrame();
			}
		}
	}

	public void addProperty(AnimatableProperty<?> property) {
		properties.add(property);
	}

	public List<AnimatableProperty<?>> getProperties() {
		return properties;
	}

	public void setCurrentAnimation(Animation animation) {
		animation.load();
		currentAnimation = animation;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	/**
	 * Creates a blank, 1-frame animation that is either copied from the current
	 * animation or filled with 0s if there is no current animation.
	 * @return
	 */
	public Animation createNewAnimation() {
		return new Animation(this);
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
		if (playing) {
			staticListPos = currentlyPlaying.size();
			currentlyPlaying.add(this);
		} else {
			currentlyPlaying.remove(staticListPos);
		}
	}
}
