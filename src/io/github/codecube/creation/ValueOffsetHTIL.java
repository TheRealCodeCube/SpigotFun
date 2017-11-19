package io.github.codecube.creation;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class ValueOffsetHTIL implements HotbarToolbarItemListener {
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

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}

	protected abstract void offset(double delta);
}
