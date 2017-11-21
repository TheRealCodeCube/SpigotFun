package io.github.codecube.waterfall.toolbar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.codecube.waterfall.animation.AnimatableProperty;
import io.github.codecube.waterfall.animation.AnimationData;
import io.github.codecube.waterfall.util.ItemUtils;

public class AnimValueOffsetHTIL extends ValueOffsetHTIL {
	private AnimatableProperty<?> holder;
	private AnimationData channel;

	public AnimValueOffsetHTIL(AnimatableProperty<?> holder, AnimationData target) {
		this.holder = holder;
		channel = target;
	}

	public AnimValueOffsetHTIL(AnimatableProperty<?> holder, AnimationData target, double largeStep, double smallStep) {
		super(largeStep, smallStep);
		this.holder = holder;
		channel = target;
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		super.onUse(used, user, action, sneaking);
		if (action == HTIUseMode.THROW) {
			if (sneaking) {
				channel.clearKeyframesExceptCurrent();
			} else {
				channel.deleteCurrentKeyframe();
			}
			holder.informListener();
		}
		onUpdate(used, user, sneaking);
		return true;
	}

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		ItemStack current = used.getAppearence();
		if (channel.hasKeyframeOnCurrentFrame()) {
			ItemUtils.addGlow(current);
			used.setName(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + used.getNameWithoutFormatting());
		} else {
			ItemUtils.removeGlow(current);
			used.setName(used.getNameWithoutFormatting());
		}
		return null;
	}

	@Override
	protected void offset(double delta) {
		channel.set(channel.getValue() + delta);
		holder.informListener();
	}
}
