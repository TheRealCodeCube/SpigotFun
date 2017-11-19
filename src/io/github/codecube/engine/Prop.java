package io.github.codecube.engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import io.github.codecube.creation.HotbarToolbar;
import io.github.codecube.creation.HotbarToolbarItem;
import io.github.codecube.creation.OpenToolbarHTIL;
import io.github.codecube.creation.ValueOffsetHTIL;
import io.github.codecube.util.StoneHoeIcons;

public class Prop {
	private World world = null;
	private Vector position = new Vector(0, 0, 0);
	protected Location worldPosition = null;
	private List<Prop> children = new ArrayList<>();
	private Prop parent = null;
	private boolean placed = false;
	private String name = "unnamed";

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return "Prop";
	}

	public HotbarToolbar createToolbar() {
		HotbarToolbar tr = new HotbarToolbar(), misc = new HotbarToolbar(), translate = new HotbarToolbar();

		HotbarToolbarItem gotoMisc = new HotbarToolbarItem(StoneHoeIcons.ICON_ELLIPSIS);
		gotoMisc.setName("Basic / Misc");
		gotoMisc.setListener(new OpenToolbarHTIL(misc, tr));
		tr.addItem(gotoMisc, 1);

		/*
		 * HotbarToolbarItem delete = new HotbarToolbarItem(StoneHoeIcons.ICON_DELETE);
		 * delete.setName("Delete Prop"); delete.setListener(new
		 * HotbarToolbarItemListener() {
		 * 
		 * @Override public boolean onUse(HotbarToolbarItem used, Player user, Action
		 * action, boolean sneaking) { destroy(); return true; }
		 * 
		 * @Override public ItemStack onUpdate(HotbarToolbarItem used, Player holder,
		 * boolean sneaking) { return null; } });
		 */

		HotbarToolbarItem gotoTranslate = new HotbarToolbarItem(StoneHoeIcons.ICON_TRANSLATE);
		gotoTranslate.setName("Translate");
		gotoTranslate.setListener(new OpenToolbarHTIL(translate, tr));
		tr.addItem(gotoTranslate, 2);

		HotbarToolbarItem translateX = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_X);
		translateX.setName("Move X");
		translateX.setDescription("left/right = +/-, sneak = small increments");
		translateX.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(delta, 0, 0)));
			}
		});
		translate.addItem(translateX, 1);

		HotbarToolbarItem translateY = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Y);
		translateY.setName("Move Y");
		translateY.setDescription("left/right = +/-, sneak = small increments");
		translateY.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(0, delta, 0)));
			}
		});
		translate.addItem(translateY, 2);

		HotbarToolbarItem translateZ = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Z);
		translateZ.setName("Move Z");
		translateZ.setDescription("left/right = +/-, sneak = small increments");
		translateZ.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(0, 0, delta)));
			}
		});
		translate.addItem(translateZ, 3);

		return tr;
	}

	public void setPosition(Vector newPosition) {
		position = newPosition;
		if (placed) {
			updateWorldPosition();
		}
	}

	public Vector getPosition() {
		return position;
	}

	private void updateWorldPosition() {
		Vector p = getRealPosition();
		worldPosition = new Location(world, p.getX(), p.getY(), p.getZ());
		onPositionChange();
		for (Prop child : children) {
			child.updateWorldPosition();
		}
	}

	private Vector getRealPosition() {
		if (parent == null) {
			return position;
		} else {
			return position.add(parent.getRealPosition());
		}
	}

	protected void onPositionChange() {

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

	protected void onUpdate(int timeDelta) {

	}

	public void update(int timeDelta) {
		onUpdate(timeDelta);
		for (Prop child : children) {
			child.update(timeDelta);
		}
	}
}
