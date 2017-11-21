package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;

public abstract class SimpleHTIL extends HotbarToolbarItemListener {
	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		onUse();
		return true;
	}

	protected abstract void onUse();
}
