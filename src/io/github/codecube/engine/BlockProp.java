package io.github.codecube.engine;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockProp extends Prop {
	@Override
	protected boolean onPlace() {
		if (getPosition() == null) {
			return false;
		}
		Block block = getPosition().getBlock();
		block.setType(Material.STONE);
		block.setData((byte) 0);
		return true;
	}

	@Override
	protected boolean onDestroy() {
		if (getPosition() == null) {
			return false;
		}
		Block block = getPosition().getBlock();
		block.setType(Material.AIR);
		return true;
	}
}
