package io.github.codecube.creation;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class ToggleHTIL implements HotbarToolbarItemListener {
	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		toggle();
		return true;
	}

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}

	protected abstract void toggle();
}
