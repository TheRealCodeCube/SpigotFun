package io.github.codecube.engine;

import io.github.codecube.creation.HotbarToolbar;

public abstract class AnimatableProperty<T> {
	private AnimPropertySet parent;
	protected AnimationData[] dataChannels;
	private AnimationListener<T> listener;

	protected AnimatableProperty(AnimPropertySet parent, int dataChannels) {
		this.parent = parent;
		parent.addProperty(this);
		this.dataChannels = new AnimationData[dataChannels];
	}

	public AnimPropertySet getParent() {
		return parent;
	}

	public int getNumChannels() {
		return dataChannels.length;
	}

	public void setChannel(int channel, AnimationData source) {
		dataChannels[channel] = source;
	}

	public void setRaw(int channel, double value) {
		dataChannels[channel].set(value);
	}

	public void setRaw(int channel, int frame, double value) {
		dataChannels[channel].set(frame, value);
	}

	public double getRaw(int channel) {
		return dataChannels[channel].getValue();
	}

	public double getRaw(int channel, int frame) {
		return dataChannels[channel].getValue(frame);
	}

	public void setListener(AnimationListener<T> listener) {
		this.listener = listener;
	}

	public AnimationListener<T> getListener() {
		return listener;
	}

	public void informListener() {
		listener.onValueChange(get());
	}

	/**
	 * Adds or updates a keyframe at the specified position.
	 * @param frame
	 *        The location of the keyframe to add / modify.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public abstract void set(int frame, T value);

	/**
	 * Adds or updates a keyframe at the current frame. Does not inform the
	 * listener.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void set(T value) {
		set(getParent().getCurrentAnimation().getCurrentFrame(), value);
	}

	/**
	 * Adds or updates a keyframe at the current frame, and calls onValueChange() on
	 * the currently loaded listener to update anything with the new value.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setAndInformListener(T value) {
		set(value);
		listener.onValueChange(value);
	}

	/**
	 * Gets what the value should be at a particular frame in the animation.
	 * @param frame
	 *        Where to get the value from.
	 * @return The value, as it should be at that point in the animation.
	 */
	public abstract T get(int frame);

	/**
	 * Gets what the value should be at the current frame in the animation.
	 * @return The value, as it should be at the current frame in the animation.
	 */
	public T get() {
		return get(getParent().getCurrentAnimation().getCurrentFrame());
	}

	/**
	 * Returns true if any channels of data have a keyframe on the specified frame.
	 * @param frame
	 *        The frame to check for existing keyframes.
	 * @return True if there is one or more keyframes on this frame.
	 */
	public boolean hasKeyframe(int frame) {
		for (AnimationData channel : dataChannels) {
			if (channel.hasKeyframe(frame)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if any channels of data have a keyframe on the current frame.
	 * @return True if any channels of data have a keyframe on the current frame.
	 */
	public boolean hasKeyframe() {
		return hasKeyframe(getParent().getCurrentAnimation().getCurrentFrame());
	}

	/**
	 * Deletes all keyframes existing on a particular frame of the animation.
	 * @param frame
	 *        Where to delete keyframes from.
	 */
	public void deleteKeyframe(int frame) {
		for (AnimationData channel : dataChannels) {
			channel.deleteKeyframe(frame);
		}
	}

	/**
	 * Deletes all keyframes on the current frame.
	 */
	public void deleteKeyframe() {
		deleteKeyframe(getParent().getCurrentAnimation().getCurrentFrame());
	}

	public HotbarToolbar createEditorToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		return tr;
	}
}
