package io.github.codecube.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum StoneHoeIcons {
	ICON_BACK_NAV(1), ICON_UP(2), ICON_DOWN(3), ICON_LEFT(4), ICON_RIGHT(5), ICON_CLOCKWISE(6), ICON_COUNTERWISE(7),
	ICON_CHECK(8), ICON_X(9), ICON_NEW(10), ICON_OPEN(11), ICON_SAVE(12), ICON_DELETE(13), ICON_WAND(14),
	ICON_WAND_SELECT(15), ICON_WAND_ADD(16), ICON_WAND_SUB(17), ICON_WAND_SMOOTH(18), ICON_WAND_RAISE(19),
	ICON_WAND_LOWER(20), ICON_WAND_EDIT(21);

	private final short damage;

	private StoneHoeIcons(int damage) {
		this.damage = (short) damage;
	}

	public short getDamage() {
		return damage;
	}

	public static ItemStack createIcon(StoneHoeIcons icon) {
		ItemStack tr = new ItemStack(Material.STONE_HOE, 1, icon.getDamage());
		ItemMeta meta = tr.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		tr.setItemMeta(meta);
		return tr;
	}
}
