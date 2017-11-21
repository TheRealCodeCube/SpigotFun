package io.github.codecube.waterfall.props;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.codecube.waterfall.animation.AnimPropertySet;
import io.github.codecube.waterfall.animation.AnimatableVector;
import io.github.codecube.waterfall.animation.Animation;
import io.github.codecube.waterfall.animation.AnimationListener;
import io.github.codecube.waterfall.toolbar.ChatInputHTIL;
import io.github.codecube.waterfall.toolbar.HTIUseMode;
import io.github.codecube.waterfall.toolbar.HotbarToolbar;
import io.github.codecube.waterfall.toolbar.HotbarToolbarItem;
import io.github.codecube.waterfall.toolbar.OpenToolbarHTIL;
import io.github.codecube.waterfall.toolbar.ValueOffsetHTIL;
import io.github.codecube.waterfall.util.StoneHoeIcons;

public class Prop {
	private World world = null;
	protected Location worldPosition = null;
	protected AnimPropertySet data = new AnimPropertySet();
	private AnimatableVector position = new AnimatableVector(data);
	private List<Prop> children = new ArrayList<>();
	private Prop parent = null;
	private boolean placed = false;
	private String name = "unnamed";

	public Prop() {
		Animation defaultAnimation = data.createNewAnimation();
		data.setCurrentAnimation(defaultAnimation);

		position.setListener(new AnimationListener<Vector>() {
			@Override
			public void onValueChange(Vector newValue) {
				if (parent == null) {
					worldPosition.setX(newValue.getX());
					worldPosition.setY(newValue.getY());
					worldPosition.setZ(newValue.getZ());
				} else {
					Vector parentPos = parent.getRealPosition();
					worldPosition.setX(newValue.getX() + parentPos.getX());
					worldPosition.setY(newValue.getY() + parentPos.getY());
					worldPosition.setZ(newValue.getZ() + parentPos.getZ());
				}
				onPositionChange();
			}
		});
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return "Prop";
	}

	public StoneHoeIcons getEditorIcon() {
		return StoneHoeIcons.ICON_GENERIC;
	}

	protected HotbarToolbar createMiscToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

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

		HotbarToolbarItem rename = new HotbarToolbarItem(StoneHoeIcons.ICON_EDIT);
		rename.setName("Rename (Currently " + getName() + ")");
		rename.setListener(new ChatInputHTIL("What should the prop's name be?") {
			@Override
			public void onInput(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking,
					String input) {
				setName(input);
				used.setName("Rename (Currently " + getName() + ")");
			}
		});
		tr.addItem(rename);

		return tr;
	}

	protected HotbarToolbar createTranslateToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		HotbarToolbarItem translateX = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_X);
		translateX.setName("Move X");
		translateX.setDescription("left/right = +/-, sneak = small increments");
		translateX.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(delta, 0, 0)));
			}
		});
		tr.addItem(translateX);

		HotbarToolbarItem translateY = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Y);
		translateY.setName("Move Y");
		translateY.setDescription("left/right = +/-, sneak = small increments");
		translateY.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(0, delta, 0)));
			}
		});
		tr.addItem(translateY);

		HotbarToolbarItem translateZ = new HotbarToolbarItem(StoneHoeIcons.ICON_MOVE_Z);
		translateZ.setName("Move Z");
		translateZ.setDescription("left/right = +/-, sneak = small increments");
		translateZ.setListener(new ValueOffsetHTIL() {
			@Override
			protected void offset(double delta) {
				setPosition(getPosition().add(new Vector(0, 0, delta)));
			}
		});
		tr.addItem(translateZ);

		return position.createEditorToolbar();
	}

	public HotbarToolbar createToolbar() {
		HotbarToolbar tr = new HotbarToolbar(), misc = createMiscToolbar(), translate = createTranslateToolbar();

		HotbarToolbarItem gotoMisc = new HotbarToolbarItem(StoneHoeIcons.ICON_ELLIPSIS);
		gotoMisc.setName("Basic / Misc");
		gotoMisc.setListener(new OpenToolbarHTIL(misc, tr));
		tr.addItem(gotoMisc);

		HotbarToolbarItem gotoTranslate = new HotbarToolbarItem(StoneHoeIcons.ICON_TRANSLATE);
		gotoTranslate.setName("Translate");
		gotoTranslate.setListener(new OpenToolbarHTIL(translate, tr));
		tr.addItem(gotoTranslate);

		return tr;
	}

	public void setPosition(Vector newPosition) {
		Vector delta = newPosition.subtract(position.get());
		position.set(newPosition);
		if (placed) {
			worldPosition = worldPosition.add(delta);
			onPositionChange();
		}
	}

	public void offsetPosition(Vector delta) {
		position.set(position.get().add(delta));
		if (placed) {
			worldPosition = worldPosition.add(delta);
			onPositionChange();
		}
	}

	public Vector getPosition() {
		return position.get();
	}

	public Vector getRealPosition() {
		if (parent == null) {
			return position.get();
		} else {
			return position.get().add(parent.getRealPosition());
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

	public Prop getParent() {
		return parent;
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
