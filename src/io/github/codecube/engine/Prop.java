package io.github.codecube.engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Prop {
	private World world = null;
	private Vector position = new Vector(0, 0, 0);
	protected Location worldPosition = null;
	private List<Prop> children = new ArrayList<>();
	private Prop parent = null;
	private boolean placed = false;

	public void setPosition(Vector newPosition) {
		position = newPosition;
	}

	public Vector getPosition() {
		return position;
	}

	private Vector getRealPosition() {
		if (parent == null) {
			return position;
		} else {
			return position.add(parent.getRealPosition());
		}
	}

	public void addChild(Prop child) {
		child.parent = this;
		children.add(child);
	}

	public List<Prop> getChildren() {
		return children;
	}

	public boolean removeChild(Prop child) {
		child.parent = null;
		return children.remove(child);
	}

	public void removeChild(int index) {
		children.get(index).parent = null;
		children.remove(index);
	}

	public void clearChildren() {
		for (Prop child : children) {
			child.parent = null;
		}
		children.clear();
	}

	protected void onPositionChange() {

	}

	protected boolean onCreate() {
		return true;
	}

	public boolean create(World container) {
		Vector p = getRealPosition();
		world = container;
		worldPosition = new Location(world, p.getX(), p.getY(), p.getZ());
		if (!placed) {
			for (Prop child : children) {
				if (!child.create(container)) {
					return false;
				}
			}
			placed = onCreate();
		}
		return placed;
	}

	protected boolean onDestroy() {
		return true;
	}

	public boolean destroy() {
		if (placed) {
			for (Prop child : children) {
				if (!child.destroy()) {
					return false;
				}
			}
			placed = !onDestroy();
		}
		return !placed;
	}
}
