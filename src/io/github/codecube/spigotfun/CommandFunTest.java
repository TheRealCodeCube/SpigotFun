package io.github.codecube.spigotfun;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.codecube.creation.HotbarToolbar;
import io.github.codecube.engine.ArmorStandProp;
import io.github.codecube.engine.Scene;

public class CommandFunTest implements CommandExecutor {
	private static final Scene testScene = new Scene();

	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Test out scenes, they are fancy.
		Player player = (Player) sender;
		if (!testScene.worldStarted()) {
			ArmorStandProp prop = new ArmorStandProp();
			prop.setPosition(new Vector(2, 3, 2));
			prop.setHead(new ItemStack(Material.PUMPKIN));
			prop.setStructureVisible(false);
			prop.setGlowing(true);
			HotbarToolbar.showToolbar(prop.createToolbar(), player);
			testScene.addProp(prop);
			testScene.startWorld();
		}
		testScene.addPlayer(player);

		return true; // Return false if the command was used incorrectly.
	}
}
