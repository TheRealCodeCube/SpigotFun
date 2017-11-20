package io.github.codecube.creation;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import io.github.codecube.util.StoneHoeIcons;

public class OpenToolbarHTIL implements HotbarToolbarItemListener {
	private HotbarToolbar toOpen;

	public OpenToolbarHTIL(HotbarToolbar toOpen) {
		this.toOpen = toOpen;
	}

	public OpenToolbarHTIL(HotbarToolbar toOpen, HotbarToolbar parent) {
		this.toOpen = toOpen;
		toOpen.offsetItems();
		HotbarToolbarItem back = new HotbarToolbarItem(StoneHoeIcons.ICON_BACK_NAV);
		back.setName("Go Back");
		back.setListener(new OpenToolbarHTIL(parent));
		toOpen.addItem(back, 0);
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		HotbarToolbar.showToolbar(toOpen, user);
		return true;
	}

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}
}
