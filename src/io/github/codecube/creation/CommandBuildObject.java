package io.github.codecube.creation;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBuildObject implements CommandExecutor {
	public static final String OBJECT_EDITOR_WORLD_PREFIX = "objecteditor";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			World world = EditorWorld.getInstance().getWorld();
			if (world == null) { // Something went wrong when recreating the world.
				player.sendMessage("Could not reset editor! Are there people still in it?");
				return false;
			} else {
				player.teleport(world.getSpawnLocation());
				return true;
			}

		} else {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
	}
}
