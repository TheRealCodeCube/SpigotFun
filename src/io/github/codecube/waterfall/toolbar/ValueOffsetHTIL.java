package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;

public abstract class ValueOffsetHTIL extends HotbarToolbarItemListener {
	private double largeAmount = 1.0, smallAmount = 0.1;

	public ValueOffsetHTIL() {

	}

	public ValueOffsetHTIL(double largeAmount, double smallAmount) {
		this.largeAmount = largeAmount;
		this.smallAmount = smallAmount;
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		double value = (sneaking) ? smallAmount : largeAmount;
		if (action == HTIUseMode.RIGHT_CLICK)
			value = -value;
		else if (action != HTIUseMode.LEFT_CLICK) // LClick is positive.
			value = 0.0;
		offset(value);
		return true;
	}

	protected abstract void offset(double delta);
}
