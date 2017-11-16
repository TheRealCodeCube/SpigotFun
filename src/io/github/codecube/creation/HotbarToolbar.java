package io.github.codecube.creation;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class HotbarToolbar {
	private static Map<Player, HotbarToolbar> toolbars = new HashMap<>();

	private HotbarToolbarItem[] items = new HotbarToolbarItem[9];
	private Player user = null;

	public static HotbarToolbar createToolbar(Player user) {
		return new HotbarToolbar(user);
	}

	public static void unlinkToolbar(HotbarToolbar toolbar) {
		toolbar.setUser(null);
	}

	public static void unlinkToolbar(Player user) {
		if (toolbars.containsKey(user)) {
			toolbars.get(user).setUser(null);
		}
	}

	public static boolean use(Player user, int slot, Action action) {
		if (toolbars.containsKey(user)) {
			return toolbars.get(user).use(slot, action);
		}
		return false;
	}

	public static void updateAll() {
		for (HotbarToolbar toolbar : toolbars.values()) {
			toolbar.update();
		}
	}

	public HotbarToolbar() {

	}

	public HotbarToolbar(Player user) {
		this.user = user;
		toolbars.put(user, this);
	}

	public void setUser(Player newUser) {
		if (user != null)
			toolbars.remove(user);
		user = newUser;
		if (user != null)
			toolbars.put(user, this);
	}

	public Player getUser() {
		return user;
	}

	public void addItem(HotbarToolbarItem item, int slot) {
		item.setSlot(slot);
		item.setParent(this);
		items[slot] = item;
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

	public boolean use(int slot, Action action) {
		if (items[slot] != null) {
			return items[slot].use(action);
		}
		return false;
	}
}
