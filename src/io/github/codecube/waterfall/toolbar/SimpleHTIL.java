package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public abstract class SimpleHTIL extends HotbarToolbarItemListener {
	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		onUse();
		return true;
	}

	protected abstract void onUse();
}
