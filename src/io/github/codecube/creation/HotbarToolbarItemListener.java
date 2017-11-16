package io.github.codecube.creation;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface HotbarToolbarItemListener {
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking);

	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking);
}
