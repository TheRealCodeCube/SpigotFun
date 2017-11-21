package io.github.codecube.waterfall.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	/**
	 * Adds an invisible OXYGEN enchantment to an ItemStack just for the glowiness.
	 * (NOTE that this will also hide any legit enchantments as well.)
	 * @param stack
	 *        The ItemStack to add the enchantment to.
	 */
	public static void addGlow(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(Enchantment.OXYGEN, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}

	/**
	 * Removes any OXYGEN enchantments on an ItemStack. (This is the enchantment
	 * used by addGlow().) Also turns back on enchantment visibility, if you have
	 * any legit enchantments on the object.
	 * @param stack
	 *        The ItemStack to remove the enchantment from.
	 */
	public static void removeGlow(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.removeEnchant(Enchantment.OXYGEN);
		meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}
}
