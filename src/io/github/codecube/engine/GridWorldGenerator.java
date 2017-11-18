package io.github.codecube.engine;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

public class GridWorldGenerator extends ChunkGenerator {
	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biomes) {
		ChunkData data = createChunkData(world);

		data.setRegion(0, 0, 0, 16, 1, 16, new MaterialData(Material.STAINED_CLAY, (byte) 0));
		data.setRegion(1, 0, 1, 16, 1, 16, new MaterialData(Material.STAINED_CLAY, (byte) 15));
		data.setBlock(15, 0, 0, new MaterialData(Material.STAINED_CLAY, (byte) 14));
		data.setBlock(14, 0, 0, new MaterialData(Material.STAINED_CLAY, (byte) 14));
		data.setBlock(0, 0, 15, new MaterialData(Material.STAINED_CLAY, (byte) 11));
		data.setBlock(0, 0, 14, new MaterialData(Material.STAINED_CLAY, (byte) 11));

		if ((x == 0) && (z == 0)) { // Origin marker.
			data.setBlock(0, 0, 0, Material.DIAMOND_BLOCK);
		}

		return data;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 2, 0);
	}
}
