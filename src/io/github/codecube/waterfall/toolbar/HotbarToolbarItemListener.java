package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class HotbarToolbarItemListener {
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		return false;
	}

	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}
}
