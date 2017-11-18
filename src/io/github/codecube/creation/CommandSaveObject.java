package io.github.codecube.creation;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSaveObject implements CommandExecutor {
	public static final int MAX_SEARCH_SIZE = 10; // The maximum radius, in chunks, that the bounding box should be.
	public static final int MAX_SEARCH_GAP = 1; // If there is nothing in this many chunks when searching, then the
												// algorithm will consider that the edge of the object.

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("You must provide a file name to save to!");
			return false;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.getWorld().getName().contains(EditorScene.EDITOR_WORLD_PREFIX)) {
				sender.sendMessage("You must be in the editor to use this command!");
				return false;
			}
			// TODO: Make this more efficient.
			int px = 0, py = 0, pz = 0, nx = 0, nz = 0;
			int radius = 1, maxHeight = player.getWorld().getMaxHeight();
			for (int cx = -radius; cx < radius; cx++) {
				for (int cz = -radius; cz < radius; cz++) {
					Chunk chunk = player.getWorld().getChunkAt(cx, cz);
					for (int y = 1; y < maxHeight; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								if (!chunk.getBlock(x, y, z).isEmpty()) {
									if (cx < 0) {
										nx = Math.max(-cx * 16 - x, nx);
									} else {
										px = Math.max(cx * 16 + x, px);
									}
									if (cz < 0) {
										nz = Math.max(-cz * 16 - x, nz);
									} else {
										pz = Math.max(cz * 16 + x, pz);
									}
									py = Math.max(y, py);
								}
							}
						}
					}
				}
			}
			sender.sendMessage("Dimensions: {" + nx + ", " + px + "}, " + py + ", {" + nz + ", " + pz + "}");
		}
		return true;
	}
}
