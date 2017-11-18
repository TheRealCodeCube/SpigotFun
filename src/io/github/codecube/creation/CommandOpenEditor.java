package io.github.codecube.creation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.codecube.engine.Scene;

public class CommandOpenEditor implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Scene editor = EditorScene.getInstance();
			// If the player is already in the editor, leave the editor.
			if (editor.getWorldName() == player.getWorld().getName())
				player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			else
				editor.addPlayer(player);
			return true;
		} else {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
	}
}
