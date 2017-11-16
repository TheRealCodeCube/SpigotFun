package io.github.codecube.spigotfun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import io.github.codecube.engine.BlockProp;

public class CommandFunTest implements CommandExecutor {
	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Test out BlockProp
		if (sender instanceof Entity) {
			Entity entity = (Entity) sender;
			BlockProp prop = new BlockProp();
			prop.setPosition(entity.getLocation().add(0, 3, 0));
			prop.place();
		}
		return true; // Return false if the command was used incorrectly.
	}
}
