package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public abstract class ValueOffsetHTIL extends HotbarToolbarItemListener {
	private double largeAmount = 1.0, smallAmount = 0.1;

	public ValueOffsetHTIL() {

	}

	public ValueOffsetHTIL(double largeAmount, double smallAmount) {
		this.largeAmount = largeAmount;
		this.smallAmount = smallAmount;
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
		double value = (sneaking) ? smallAmount : largeAmount;
		value = ((action == Action.LEFT_CLICK_AIR) || (action == Action.LEFT_CLICK_BLOCK)) ? value : -value;
		offset(value);
		return true;
	}

	protected abstract void offset(double delta);
}
