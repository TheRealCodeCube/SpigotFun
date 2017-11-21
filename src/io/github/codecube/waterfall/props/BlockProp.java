package io.github.codecube.waterfall.props;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockProp extends Prop {
	@Override
	protected boolean onCreate() {
		if (worldPosition == null) {
			return false;
		}
		Block block = worldPosition.getBlock();
		block.setType(Material.STONE);
		block.setData((byte) 0);
		return true;
	}

	@Override
	protected boolean onDestroy() {
		if (worldPosition == null) {
			return false;
		}
		Block block = worldPosition.getBlock();
		block.setType(Material.AIR);
		return true;
	}
}
