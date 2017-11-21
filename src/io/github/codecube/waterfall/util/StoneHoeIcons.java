package io.github.codecube.waterfall.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum StoneHoeIcons {
	ICON_BACK_NAV(1), ICON_UP(2), ICON_DOWN(3), ICON_LEFT(4), ICON_RIGHT(5), ICON_CLOCKWISE(6), ICON_COUNTERWISE(7),
	ICON_CHECK(8), ICON_X(9), ICON_NEW(10), ICON_OPEN(11), ICON_SAVE(12), ICON_DELETE(13), ICON_WAND(14),
	ICON_WAND_SELECT(15), ICON_WAND_ADD(16), ICON_WAND_SUB(17), ICON_WAND_SMOOTH(18), ICON_WAND_RAISE(19),
	ICON_WAND_LOWER(20), ICON_EDIT(21), ICON_MOVE_X(22), ICON_MOVE_Y(23), ICON_MOVE_Z(24), ICON_ROT_X(25),
	ICON_ROT_Y(26), ICON_ROT_Z(27), ICON_TRANSLATE(28), ICON_ROTATE(29), ICON_TRANSFORM(30), ICON_ARMOR_STAND(31),
	ICON_RIGHT_ARM(32), ICON_LEFT_ARM(33), ICON_RIGHT_LEG(34), ICON_LEFT_LEG(35), ICON_BODY(36), ICON_HEAD(37),
	ICON_BASEPLATE_VISIBLE(38), ICON_ARMS_VISIBLE(39), ICON_ARMOR_STAND_VISIBLE(40), ICON_ELLIPSIS(41),
	ICON_VISIBLE(42), ICON_POSE(43), ICON_INVENTORY(44), ICON_HELMET(45), ICON_CHESTPLATE(46), ICON_LEGGINGS(47),
	ICON_BOOTS(48), ICON_HAND(49), ICON_GRAVITY(50), ICON_OUTLINE(51), ICON_RESET(52), ICON_CURSOR(53),
	ICON_GENERIC(54), ICON_GENERIC_FACE(55), ICON_ADD(56), ICON_ANIMATION(57), ICON_PLAY(58), ICON_PAUSE(59),
	ICON_SKIP_BACK(60), ICON_SKIP_FORWARD(61), ICON_SKIP_BEGINNING(62), ICON_SKIP_END(63), ICON_FRAMERATE(64),
	ICON_FPS_5(65), ICON_FPS_10(66), ICON_FPS_15(67), ICON_FPS_20(68), ICON_FPS_30(69), ICON_FPS_40(70), ICON_FPS_2(71),
	ICON_FPS_1(72), ICON_SEEK(73), ICON_RANGE(74);

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
