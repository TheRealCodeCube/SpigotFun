package io.github.codecube.spigotfun;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.codecube.engine.ArmorStandProp;

public class CommandFunTest implements CommandExecutor {
	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Test out armor stand props.
		Player player = (Player) sender;
		ArmorStandProp asp = new ArmorStandProp();
		asp.setHead(new ItemStack(Material.PUMPKIN));
		asp.setPosition(player.getLocation().add(2.0, 2.0, 2.0).toVector());
		asp.create(player.getWorld());

		return true; // Return false if the command was used incorrectly.
	}
}
