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
import io.github.codecube.waterfall.toolbar.HotbarToolbarItemListener;
import io.github.codecube.waterfall.toolbar.OpenToolbarHTIL;
import io.github.codecube.waterfall.toolbar.SimpleHTIL;
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

	public void createDefaultAnimation() {
		Animation defaultAnimation = data.createNewAnimation();
		data.setCurrentAnimation(defaultAnimation);
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

	private static final StoneHoeIcons[] FRAMERATE_ICONS = { StoneHoeIcons.ICON_FPS_1, StoneHoeIcons.ICON_FPS_2,
			StoneHoeIcons.ICON_FPS_5, StoneHoeIcons.ICON_FPS_10, StoneHoeIcons.ICON_FPS_20 };
	private static final int[] FRAMERATE_VALUES = { 20, 10, 4, 2, 1 };

	private HotbarToolbar createFramerateToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		for (int i = 0; i < FRAMERATE_VALUES.length; i++) {
			final int j = i;
			HotbarToolbarItem selector = new HotbarToolbarItem(FRAMERATE_ICONS[i]);
			selector.setName("Set Framerate To " + (int) (20.0 / FRAMERATE_VALUES[i]) + "FPS");
			selector.setListener(new SimpleHTIL() {
				@Override
				protected void onUse() {
					data.getCurrentAnimation().setTimePerFrame(FRAMERATE_VALUES[j]);
				}
			});
			tr.addItem(selector);
		}

		return tr;
	}

	protected HotbarToolbar createAnimationToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		HotbarToolbarItem pausePlay = new HotbarToolbarItem(StoneHoeIcons.ICON_PLAY);
		pausePlay.setName("Play");
		pausePlay.setListener(new HotbarToolbarItemListener() {
			@Override
			public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
				data.setPlaying(!data.isPlaying());
				if (data.isPlaying()) {
					pausePlay.setAppearence(StoneHoeIcons.createIcon(StoneHoeIcons.ICON_PAUSE));
					pausePlay.setName("Pause");
				} else {
					pausePlay.setAppearence(StoneHoeIcons.createIcon(StoneHoeIcons.ICON_PLAY));
					pausePlay.setName("Play");
				}
				return true;
			}
		});
		tr.addItem(pausePlay);

		HotbarToolbarItem seek = new HotbarToolbarItem(StoneHoeIcons.ICON_SEEK);
		seek.setName("Seek (Current frame is " + data.getCurrentAnimation().getCurrentFrame() + ")");
		seek.setDescription("left/right = +/-, sneak = large increments.");
		seek.setListener(new ValueOffsetHTIL(1.0, 10.0) {
			@Override
			public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
				boolean result = super.onUse(used, user, action, sneaking);
				used.setName("Seek (Current frame is " + data.getCurrentAnimation().getCurrentFrame() + ")");
				return result;
			}

			@Override
			protected void offset(double delta) {
				data.getCurrentAnimation().offsetCurrentFrame((int) delta);
			}
		});
		tr.addItem(seek);

		HotbarToolbarItem changeRange = new HotbarToolbarItem(StoneHoeIcons.ICON_RANGE);
		changeRange.setName("Change Duration (Currently is " + data.getCurrentAnimation().getDuration() + ")");
		changeRange.setDescription("left/right = +/-. sneak = large increments.");
		changeRange.setListener(new ValueOffsetHTIL(1.0, 10.0) {
			@Override
			public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
				boolean result = super.onUse(used, user, action, sneaking);
				used.setName("Change Duration (Currently is " + data.getCurrentAnimation().getDuration() + ")");
				return result;
			}

			@Override
			protected void offset(double delta) {
				data.getCurrentAnimation().offsetDuration((int) delta);
			}
		});
		tr.addItem(changeRange);

		HotbarToolbarItem changeFramerate = new HotbarToolbarItem(StoneHoeIcons.ICON_FRAMERATE);
		changeFramerate.setName("Change Framerate");
		changeFramerate.setListener(new OpenToolbarHTIL(createFramerateToolbar(), tr));
		tr.addItem(changeFramerate);

		return tr;
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

	public HotbarToolbar createToolbar() {
		HotbarToolbar tr = new HotbarToolbar(), misc = createMiscToolbar(), translate = position.createEditorToolbar();

		HotbarToolbarItem gotoAnimation = new HotbarToolbarItem(StoneHoeIcons.ICON_ANIMATION);
		gotoAnimation.setName("Animation Settings");
		gotoAnimation.setListener(new OpenToolbarHTIL(createAnimationToolbar(), tr));
		tr.addItem(gotoAnimation);

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
