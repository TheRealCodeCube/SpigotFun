package io.github.codecube.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.codecube.util.StoneHoeIcons;
import net.md_5.bungee.api.ChatColor;

public class HotbarToolbarItem {
	private ItemStack appearence = null;
	private int slot = 0;
	private HotbarToolbar parent = null;
	private HotbarToolbarItemListener listener = null;
	private boolean updatedOnce = false;

	public HotbarToolbarItem() {
		appearence = new ItemStack(Material.STONE, 32);
	}

	public HotbarToolbarItem(ItemStack appearence) {
		this.appearence = appearence;
	}

	public HotbarToolbarItem(StoneHoeIcons icon) {
		appearence = StoneHoeIcons.createIcon(icon);
	}

	public ItemStack getAppearence() {
		return appearence;
	}

	public void setAppearence(ItemStack newAppearence) {
		appearence = newAppearence;
		if (updatedOnce)
			update();
	}

	public void setName(String name) {
		ItemMeta meta = appearence.getItemMeta();
		meta.setDisplayName(name);
		appearence.setItemMeta(meta);
		if (updatedOnce)
			update();
	}

	public void setDescription(List<String> description) {
		ItemMeta meta = appearence.getItemMeta();
		description.set(0, ChatColor.RESET + description.get(0));
		meta.setLore(description);
		appearence.setItemMeta(meta);
		if (updatedOnce)
			update();
	}

	public void setDescription(String[] description) {
		setDescription(Arrays.asList(description));
		if (updatedOnce)
			update();
	}

	public static final int WORD_WRAP_LINE_LENGTH = 30;

	/**
	 * This version does word wrapping.
	 * 
	 * @param description
	 *            A string that will be word-wrapped and set as the lore for this
	 *            item's appearence.
	 */
	public void setDescription(String description) {
		String[] words = description.split(" ");
		List<String> wordWrapped = new ArrayList<>();
		int charCount = WORD_WRAP_LINE_LENGTH;
		for (String word : words) {
			if (charCount + 1 + word.length() > WORD_WRAP_LINE_LENGTH) {
				charCount = word.length();
				wordWrapped.add(word);
			} else {
				charCount += 1 + word.length();
				wordWrapped.set(wordWrapped.size() - 1, wordWrapped.get(wordWrapped.size() - 1) + " " + word);
			}
		}
		setDescription(wordWrapped);
		if (updatedOnce)
			update();
	}

	public void setListener(HotbarToolbarItemListener listener) {
		this.listener = listener;
	}

	public void setParent(HotbarToolbar parent) {
		this.parent = parent;
	}

	public void setSlot(int slot) {
		this.slot = slot;
		if (updatedOnce)
			update();
	}

	private void updateInternal(Player holder) {
		updatedOnce = true;
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
		return result;
	}
}
