package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;

import io.github.codecube.waterfall.util.StoneHoeIcons;

public class OpenToolbarHTIL extends HotbarToolbarItemListener {
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
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		HotbarToolbar.showToolbar(toOpen, user);
		return true;
	}
}
