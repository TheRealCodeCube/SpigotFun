package io.github.codecube.waterfall.animation;

import org.bukkit.util.EulerAngle;

import io.github.codecube.waterfall.toolbar.AnimValueOffsetHTIL;
import io.github.codecube.waterfall.toolbar.HotbarToolbar;
import io.github.codecube.waterfall.toolbar.HotbarToolbarItem;
import io.github.codecube.waterfall.util.StoneHoeIcons;

public class AnimatableAngle extends AnimatableProperty<EulerAngle> {
	public AnimatableAngle(AnimPropertySet parent) {
		super(parent, 3);
	}

	@Override
	public void set(int frame, EulerAngle value) {
		dataChannels[0].set(frame, value.getX());
		dataChannels[1].set(frame, value.getY());
		dataChannels[2].set(frame, value.getZ());
	}

	@Override
	public EulerAngle get(int frame) {
		return new EulerAngle(dataChannels[0].getValue(frame), dataChannels[1].getValue(frame),
				dataChannels[2].getValue(frame));
	}

	/**
	 * Creates or modifies a keyframe for X at the specified frame, without
	 * notifying the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setX(int frame, double value) {
		dataChannels[0].set(frame, value);
	}

	/**
	 * Creates or modifies a keyframe for Y at the specified frame, without
	 * notifying the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setY(int frame, double value) {
		dataChannels[1].set(frame, value);
	}

	/**
	 * Creates or modifies a keyframe for Z at the specified frame, without
	 * notifying the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setZ(int frame, double value) {
		dataChannels[2].set(frame, value);
	}

	/**
	 * Creates or modifies a keyframe for X at the current frame, without notifying
	 * the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setX(double value) {
		dataChannels[0].set(getParent().getCurrentAnimation().getCurrentFrame(), value);
	}

	/**
	 * Creates or modifies a keyframe for Y at the current frame, without notifying
	 * the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setY(double value) {
		dataChannels[1].set(getParent().getCurrentAnimation().getCurrentFrame(), value);
	}

	/**
	 * Creates or modifies a keyframe for Z at the current frame, without notifying
	 * the listener.
	 * @param frame
	 *        The frame to create / modify the keyframe at.
	 * @param value
	 *        The value the keyframe should have.
	 */
	public void setZ(double value) {
		dataChannels[2].set(getParent().getCurrentAnimation().getCurrentFrame(), value);
	}

	/**
	 * Returns the value that X should have at a particular frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that X should have at the frame.
	 */
	public double getX(int frame) {
		return dataChannels[0].getValue(frame);
	}

	/**
	 * Returns the value that Y should have at a particular frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that Y should have at the frame.
	 */
	public double getY(int frame) {
		return dataChannels[1].getValue(frame);
	}

	/**
	 * Returns the value that Z should have at a particular frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that Z should have at the frame.
	 */
	public double getZ(int frame) {
		return dataChannels[2].getValue(frame);
	}

	/**
	 * Returns the value that X should have at the current frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that X should have at the frame.
	 */
	public double getX() {
		return dataChannels[0].getValue(getParent().getCurrentAnimation().getCurrentFrame());
	}

	/**
	 * Returns the value that Y should have at the current frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that Y should have at the frame.
	 */
	public double getY() {
		return dataChannels[1].getValue(getParent().getCurrentAnimation().getCurrentFrame());
	}

	/**
	 * Returns the value that Z should have at the current frame in the animation.
	 * @param frame
	 *        The frame to retrieve from.
	 * @return The value that Z should have at the frame.
	 */
	public double getZ() {
		return dataChannels[2].getValue(getParent().getCurrentAnimation().getCurrentFrame());
	}

	@Override
	public HotbarToolbar createEditorToolbar() {
		HotbarToolbar tr = super.createEditorToolbar();

		final double bigStep = Math.PI / 4.0, smallStep = Math.PI / 32.0;

		HotbarToolbarItem rotateX = new HotbarToolbarItem(StoneHoeIcons.ICON_ROT_X);
		rotateX.setName("Rotate X");
		rotateX.setDescription("left/right = +/-, sneak = small increments");
		rotateX.setListener(new AnimValueOffsetHTIL(this, dataChannels[0], bigStep, smallStep));
		tr.addItem(rotateX);

		HotbarToolbarItem rotateY = new HotbarToolbarItem(StoneHoeIcons.ICON_ROT_Y);
		rotateY.setName("Rotate Y");
		rotateY.setDescription("left/right = +/-, sneak = small increments");
		rotateY.setListener(new AnimValueOffsetHTIL(this, dataChannels[1], bigStep, smallStep));
		tr.addItem(rotateY);

		HotbarToolbarItem rotateZ = new HotbarToolbarItem(StoneHoeIcons.ICON_ROT_Z);
		rotateZ.setName("Rotate Z");
		rotateZ.setDescription("left/right = +/-, sneak = small increments");
		rotateZ.setListener(new AnimValueOffsetHTIL(this, dataChannels[2], bigStep, smallStep));
		tr.addItem(rotateZ);

		return tr;
	}
}
