package io.github.codecube.waterfall.toolbar;

import org.bukkit.entity.Player;

public abstract class ValueOffsetHTIL extends HotbarToolbarItemListener {
	private double largeStep = 1.0, smallStep = 0.1;

	public ValueOffsetHTIL() {

	}

	public ValueOffsetHTIL(double largeStep, double smallStep) {
		this.largeStep = largeStep;
		this.smallStep = smallStep;
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		double value = (sneaking) ? smallStep : largeStep;
		if (action == HTIUseMode.RIGHT_CLICK)
			value = -value;
		else if (action != HTIUseMode.LEFT_CLICK) // LClick is positive.
			value = 0.0;
		offset(value);
		return true;
	}

	protected abstract void offset(double delta);
}
