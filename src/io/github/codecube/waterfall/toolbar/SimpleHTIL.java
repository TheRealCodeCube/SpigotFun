package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class SimpleHTIL implements HotbarToolbarItemListener {
	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		onUse();
		return true;
	}

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}

	protected abstract void onUse();
}
