package io.github.codecube.spigotfun;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFunTest implements CommandExecutor {
	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			World world = Bukkit.getWorld("testworld");
			if (world != null) {
				Bukkit.unloadWorld(world, false);
			}

			WorldCreator creator = new WorldCreator("testworld");
			creator.environment(Environment.NORMAL);
			creator.generateStructures(false);
			creator.generator(new TestWorldGenerator());

			world = creator.createWorld();
			world.setMonsterSpawnLimit(0);
			world.setAnimalSpawnLimit(0);

			player.teleport(world.getSpawnLocation());
		}
		return true; // Return false if the command was used incorrectly.
	}
}
