package io.github.codecube.creation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class HotbarToolbarItem {
	private ItemStack appearence = null;
	private int slot = 0;
	private HotbarToolbar parent = null;
	private HotbarToolbarItemListener listener = null;

	public HotbarToolbarItem() {
		appearence = new ItemStack(Material.STONE, 32);
	}

	public HotbarToolbarItem(ItemStack appearence) {
		this.appearence = appearence;
	}

	public ItemStack getAppearence() {
		return appearence;
	}

	public void setAppearence(ItemStack newAppearence) {
		appearence = newAppearence;
	}

	public void setListener(HotbarToolbarItemListener listener) {
		this.listener = listener;
	}

	public void setParent(HotbarToolbar parent) {
		this.parent = parent;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	private void updateInternal(Player holder) {
		holder.getInventory().setItem(slot, appearence);
	}

	public void update() {
		if (this.parent == null)
			return;
		Player holder = parent.getUser();
		if (this.listener != null) {
			ItemStack newAppearence = this.listener.onUpdate(this, holder, holder.isSneaking());
			if (newAppearence != null)
				appearence = newAppearence;
		}
		updateInternal(holder);
	}

	public boolean use(Action action) {
		if ((this.listener == null) || (this.parent == null))
			return false;
		Player user = parent.getUser();
		boolean result = this.listener.onUse(this, user, action, user.isSneaking());
		updateInternal(user);
		return result;
	}
}
