package io.github.codecube.waterfall.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.codecube.waterfall.props.ArmorStandProp;
import io.github.codecube.waterfall.props.Prop;
import io.github.codecube.waterfall.scene.Scene;
import io.github.codecube.waterfall.toolbar.HTIUseMode;
import io.github.codecube.waterfall.toolbar.HotbarToolbar;
import io.github.codecube.waterfall.toolbar.HotbarToolbarItem;
import io.github.codecube.waterfall.toolbar.HotbarToolbarItemListener;
import io.github.codecube.waterfall.toolbar.OpenToolbarHTIL;
import io.github.codecube.waterfall.util.StoneHoeIcons;

public class EditorScene extends Scene {
	public static final String EDITOR_WORLD_PREFIX = "editor_";
	private static EditorScene instance = new EditorScene();

	public static EditorScene getInstance() {
		return instance;
	}

	private EditorScene() {
		super();
		worldName = worldName.replaceFirst(SCENE_WORLD_PREFIX, EDITOR_WORLD_PREFIX);
		startWorld();
	}

	private HotbarToolbar createNewPropToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		final class PrivateUtil {
			public void addPropClass(Prop example) {
				HotbarToolbarItem creator = new HotbarToolbarItem(example.getEditorIcon());
				creator.setName("Create New " + example.getTypeName());
				creator.setListener(new HotbarToolbarItemListener() {
					@Override
					public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
						try {
							Prop newProp = example.getClass().newInstance();
							newProp.setPosition(user.getLocation().add(1.0, 0.0, 0.0).toVector());
							addProp(newProp);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
						return true;
					}
				});
				tr.addItem(creator);
			}
		}

		PrivateUtil util = new PrivateUtil();
		util.addPropClass(new ArmorStandProp());

		return tr;
	}

	private class SelectPropHTIL extends HotbarToolbarItemListener {
		private HotbarToolbar root;
		private Prop toEdit;

		public SelectPropHTIL(HotbarToolbar root, Prop toEdit) {
			this.root = root;
			this.toEdit = toEdit;
		}

		@Override
		public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
			HotbarToolbar toShow = toEdit.createToolbar();
			toShow.offsetItems();
			HotbarToolbarItem back = new HotbarToolbarItem(StoneHoeIcons.ICON_BACK_NAV);
			back.setName("Go Back");
			back.setListener(new OpenToolbarHTIL(root));
			toShow.addItem(back);
			HotbarToolbar.showToolbar(toShow, user);
			return true;
		}

		@Override
		public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
			return null;
		}
	}

	private class OpenPropSelectorHTIL extends HotbarToolbarItemListener {
		private HotbarToolbar root;

		public OpenPropSelectorHTIL(HotbarToolbar root) {
			this.root = root;
		}

		@Override
		public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
			final int SEARCH_SIZE = 10, SEARCH_SIZE_2 = SEARCH_SIZE * 2;
			Vector center = user.getLocation().toVector();
			final double minx = center.getX() - SEARCH_SIZE, miny = center.getY() - SEARCH_SIZE,
					minz = center.getZ() - SEARCH_SIZE, maxx = minx + SEARCH_SIZE_2, maxy = miny + SEARCH_SIZE_2,
					maxz = minz + SEARCH_SIZE_2;
			List<Prop> close = new ArrayList<>();
			for (Prop prop : getProps()) {
				Vector ppos = prop.getRealPosition();
				// Quick bounding-box search
				if ((ppos.getX() > minx) && (ppos.getX() < maxx) && (ppos.getY() > miny) && (ppos.getY() < maxy)
						&& (ppos.getZ() > minz) && (ppos.getZ() < maxz)) {
					close.add(prop);
				}
			}
			Collections.sort(close, new Comparator<Prop>() {
				@Override
				public int compare(Prop o1, Prop o2) {
					// TODO: Make this more efficient, if it becomes a problem.
					return (int) ((o1.getRealPosition().distance(center) - o2.getRealPosition().distance(center))
							* 100.0);
				}
			});
			HotbarToolbar nearbyPropSelector = new HotbarToolbar();
			for (int i = 0; i < Math.min(9, close.size()); i++) {
				Prop topic = close.get(i);
				HotbarToolbarItem selectProp = new HotbarToolbarItem(topic.getEditorIcon());
				selectProp.setName("Select " + topic.getName() + " (" + topic.getTypeName() + ")");
				selectProp.setListener(new SelectPropHTIL(root, topic));
				nearbyPropSelector.addItem(selectProp);
			}
			HotbarToolbar.showToolbar(nearbyPropSelector, user);
			return true;
		}
	}

	public HotbarToolbar createEditorToolbar() {
		HotbarToolbar tr = new HotbarToolbar();

		HotbarToolbarItem createProp = new HotbarToolbarItem(StoneHoeIcons.ICON_ADD);
		createProp.setName("Add New Prop");
		createProp.setListener(new OpenToolbarHTIL(createNewPropToolbar(), tr));
		tr.addItem(createProp);

		HotbarToolbarItem selectProp = new HotbarToolbarItem(StoneHoeIcons.ICON_CURSOR);
		selectProp.setName("Select Nearby Prop");
		selectProp.setListener(new OpenPropSelectorHTIL(tr));
		tr.addItem(selectProp);

		return tr;
	}
}
