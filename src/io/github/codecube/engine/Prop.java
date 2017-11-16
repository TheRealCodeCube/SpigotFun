package io.github.codecube.engine;

import org.bukkit.Location;

public abstract class Prop {
	private Location position;
	private boolean placed = false;

	public void setPosition(Location newPosition) {
		position = newPosition;
	}

	public Location getPosition() {
		return position;
	}

	protected abstract boolean onPlace();

	public boolean place() {
		if (!placed) {
			placed = onPlace();
		}
		return placed;
	}

	protected abstract boolean onDestroy();

	public boolean destroy() {
		if (placed) {
			placed = !onDestroy();
		}
		return !placed;
	}
}
