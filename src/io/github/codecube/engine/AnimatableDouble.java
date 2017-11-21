package io.github.codecube.engine;

public class AnimatableDouble extends AnimatableProperty<Double> {
	public AnimatableDouble(AnimPropertySet parent) {
		super(parent, 1);
	}

	public void set(Double value) {
		dataChannels[0].set(value);
	}

	public void set(int frame, Double value) {
		dataChannels[0].set(frame, value);
	}

	public Double get() {
		return dataChannels[0].getValue();
	}

	public Double get(int frame) {
		return dataChannels[0].getValue(frame);
	}
}
