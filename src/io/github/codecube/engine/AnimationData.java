package io.github.codecube.engine;

import java.util.HashMap;

public class AnimationData extends HashMap<Integer, Double> {
	private Animation parent;

	public AnimationData(Animation parent) {
		this.parent = parent;
	}

	public int getDuration() {
		return parent.getDuration();
	}

	public Animation getParentAnimation() {
		return parent;
	}

	public void set(double value) {
		put(this.parent.getCurrentFrame(), value);
	}

	public void set(int frame, double value) {
		put(frame, value);
	}

	/**
	 * Interpolates between two values using linear interpolation.
	 * @param start
	 *        The value at t=0
	 * @param end
	 *        The value at t=1
	 * @param t
	 *        Blend control between the two values. t=0.5, for example, will use
	 *        exactly half of both of them.
	 * @return The blended value.
	 */
	private double interpolate(double start, double end, double t) {
		return end * t + start * (1.0 - t);
	}

	/**
	 * Finds the highest index keyframe that is still before frame.
	 * @param frame
	 *        The frame to look for keyframes before.
	 * @return The latest keyframe before the specified frame, or -1 if there is
	 *         none.
	 */
	public int getKeyframeBefore(int frame) {
		int tr = -1;
		// Find the biggest keyframe that is less than frame.
		for (Integer keyframe : keySet()) {
			if ((keyframe < frame) && (keyframe > tr)) {
				tr = keyframe;
			}
		}
		return tr;
	}

	/**
	 * Finds the lowest index keyframe that is after or equal to a frame.
	 * @param frame
	 *        The frame to look for keyframes after.
	 * @return The earliest keyframe after or equal to the specified frame, or the
	 *         duration of the animation if there is none.
	 */
	public int getKeyframeAfter(int frame) {
		int tr = parent.getDuration();
		// Find the biggest keyframe that is less than frame.
		for (Integer keyframe : keySet()) {
			if ((keyframe >= frame) && (keyframe < tr)) {
				tr = keyframe;
			}
		}
		return tr;
	}

	public double getValue() {
		return getValue(parent.getCurrentFrame());
	}

	public double getValue(int frame) {
		int beforeKeyframe = getKeyframeBefore(frame), afterKeyframe = getKeyframeAfter(frame);

		if (beforeKeyframe == -1) { // There is no start keyframe to interpolate from.
			return get(afterKeyframe);
		} else if (afterKeyframe == parent.getDuration()) { // There is no end keyframe to interpolate to.
			return get(beforeKeyframe);
		} else {
			double percentage = (frame - beforeKeyframe) / (afterKeyframe - beforeKeyframe);
			return interpolate(get(beforeKeyframe), get(afterKeyframe), percentage);
		}
	}

	public boolean hasKeyframe(int frame) {
		return containsKey(frame);
	}

	public void deleteKeyframe(int frame) {
		remove(frame);
	}
}
