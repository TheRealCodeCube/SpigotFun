package io.github.codecube.waterfall.toolbar;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class HotbarToolbar {
	private static Map<Player, HotbarToolbar> toolbars = new HashMap<>();

	private HotbarToolbarItem[] items = new HotbarToolbarItem[9];
	private Player user = null;

	public static void showToolbar(HotbarToolbar toolbar, Player user) {
		unlinkToolbar(user);
		toolbar.setUser(user);
	}

	public static void unlinkToolbar(HotbarToolbar toolbar) {
		toolbar.setUser(null);
	}

	public static void unlinkToolbar(Player user) {
		if (toolbars.containsKey(user)) {
			toolbars.get(user).setUser(null);
		}
	}

	public static boolean use(Player user, int slot, HTIUseMode action) {
		if (toolbars.containsKey(user)) {
			return toolbars.get(user).use(slot, action);
		}
		return false;
	}

	public static boolean canUse(Player user, int slot) {
		return toolbars.containsKey(user) && toolbars.get(user).canUse(slot);
	}

	public static void updateAll() {
		for (HotbarToolbar toolbar : toolbars.values()) {
			toolbar.update();
		}
	}

	public HotbarToolbar() {

	}

	private void setUser(Player newUser) {
		if (user != null) {
			for (int i = 0; i < 9; i++) { // Clear hotbar.
				user.getInventory().clear(i);
			}
			toolbars.remove(user);
		}
		user = newUser;
		if (user != null) {
			for (int i = 0; i < 9; i++) { // Clear hotbar.
				user.getInventory().clear(i);
			}
			toolbars.put(user, this);
			update();
		}
	}

	public Player getUser() {
		return user;
	}

	public void addItem(HotbarToolbarItem item, int slot) {
		item.setSlot(slot);
		item.setParent(this);
		items[slot] = item;
	}

	public void addItem(HotbarToolbarItem item) {
		for (int i = 0; i < 9; i++) {
			if (items[i] == null) {
				item.setSlot(i);
				item.setParent(this);
				items[i] = item;
				return;
			}
		}
	}

	/**
	 * Pushes all items to the right by 1 slot, overflow is removed.
	 */
	public void offsetItems() {
		if (items[8] != null) {
			items[8].setParent(null);
			items[8] = null;
		}

		for (int i = 8; i > 0; i--) {
			if (items[i - 1] != null) {
				items[i] = items[i - 1];
				items[i].setSlot(i);
			}
		}

		items[0] = null;
	}

	public void update() {
		if (user == null)
			return;
		for (HotbarToolbarItem item : items) {
			if (item != null) {
				item.update();
			}
		}
	}

	public boolean use(int slot, HTIUseMode action) {
		if (items[slot] != null) {
			return items[slot].use(action);
		}
		return false;
	}

	public boolean canUse(int slot) {
		return items[slot] != null;
	}
}
