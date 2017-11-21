package io.github.codecube.waterfall.animation;

import java.util.ArrayList;
import java.util.List;

public class Animation {
	private int currentFrame = 0, duration = 1, timePerFrame = 20;
	private AnimPropertySet propertySet;
	private List<AnimationData> rawData = new ArrayList<>();

	/**
	 * Initializes internal variables to hold a new 1-frame animation. If there is
	 * an animation currently active in the provided AnimPropertySet, initial values
	 * will be copied from the current frame of that animation. Otherwise, the
	 * initial values will be all 0.
	 * @param propertySet
	 */
	public Animation(AnimPropertySet propertySet) {
		this.propertySet = propertySet;
		if (propertySet.getCurrentAnimation() == null) {
			for (AnimatableProperty<?> property : propertySet.getProperties()) {
				for (int i = 0; i < property.getNumChannels(); i++) {
					AnimationData ta = new AnimationData(this);
					ta.set(0, 0.0);
					rawData.add(ta);

				}
			}
		} else {
			for (AnimatableProperty<?> property : propertySet.getProperties()) {
				for (int i = 0; i < property.getNumChannels(); i++) {
					AnimationData ta = new AnimationData(this);
					ta.set(0, property.getRaw(i));
					rawData.add(ta);
				}
			}
		}
	}

	/**
	 * Loads the data for this animation into the AnimatableProperties of its parent
	 * AnimPropertySet.
	 */
	public void load() {
		int i = 0;
		for (AnimatableProperty<?> property : propertySet.getProperties()) {
			for (int c = 0; c < property.getNumChannels(); c++, i++) {
				property.setChannel(c, rawData.get(i));
			}
		}
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
		for (AnimatableProperty<?> property : propertySet.getProperties()) {
			property.informListener();
		}
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void advanceFrame() {
		currentFrame = (currentFrame + 1) % duration;
		for (AnimatableProperty<?> property : propertySet.getProperties()) {
			property.informListener();
		}
	}

	public void offsetCurrentFrame(int delta) {
		// +duration is to deal with negative deltas.
		currentFrame = (currentFrame + delta + duration) % duration;
		for (AnimatableProperty<?> property : propertySet.getProperties()) {
			property.informListener();
		}
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void offsetDuration(int delta) {
		duration = Math.max(duration + delta, 1);
	}

	public void setTimePerFrame(int timePerFrame) {
		this.timePerFrame = timePerFrame;
	}

	public int getTimePerFrame() {
		return timePerFrame;
	}
}
