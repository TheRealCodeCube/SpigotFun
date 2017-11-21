package io.github.codecube.waterfall.animation;

import org.bukkit.util.Vector;

import io.github.codecube.waterfall.toolbar.HotbarToolbar;
import io.github.codecube.waterfall.toolbar.HotbarToolbarItem;
import io.github.codecube.waterfall.toolbar.ValueOffsetHTIL;
import io.github.codecube.waterfall.util.StoneHoeIcons;

public class AnimatableVector extends AnimatableProperty<Vector> {
	public AnimatableVector(AnimPropertySet parent) {
		super(parent, 3);
	}

	@Override
	public void set(int frame, Vector value) {
		dataChannels[0].set(frame, value.getX());
		dataChannels[1].set(frame, value.getY());
		dataChannels[2].set(frame, value.getZ());
	}

	@Override
	public Vector get(int frame) {
		return new Vector(dataChannels[0].getValue(frame), dataChannels[1].getValue(frame),
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

		HotbarToolbarItem translateX = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_X);
		translateX.setName("Move X");
		translateX.setDescription("left/right = +/-, sneak = small increments");
		translateX.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setX(getX() + delta);
				informListener();
			}
		});
		tr.addItem(translateX);

		HotbarToolbarItem translateY = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Y);
		translateY.setName("Move Y");
		translateY.setDescription("left/right = +/-, sneak = small increments");
		translateY.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setY(getY() + delta);
				informListener();
			}
		});
		tr.addItem(translateY);

		HotbarToolbarItem translateZ = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Z);
		translateZ.setName("Move Z");
		translateZ.setDescription("left/right = +/-, sneak = small increments");
		translateZ.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setZ(getZ() + delta);
				informListener();
			}
		});
		tr.addItem(translateZ);

		return tr;
	}
}
