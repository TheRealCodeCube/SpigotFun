package io.github.codecube.spigotfun;

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
			player.chat("hello");
		}
		return true; // Return false if the command was used incorrectly.
	}
}
