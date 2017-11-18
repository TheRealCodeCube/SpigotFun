package io.github.codecube.spigotfun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import io.github.codecube.creation.HotbarToolbar;
import io.github.codecube.creation.HotbarToolbarItem;
import io.github.codecube.creation.HotbarToolbarItemListener;
import io.github.codecube.util.StoneHoeIcons;

public class CommandFunTest implements CommandExecutor {
	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Test out HotbarToolbar
		if (sender instanceof Player) {
			sender.sendMessage("Doing it!");
			HotbarToolbarItem item1 = new HotbarToolbarItem(StoneHoeIcons.ICON_CLOCKWISE);
			item1.setName("Rotate");
			item1.setDescription(
					"Rotates a thing by a certain rotation amount so that it becomes more rotated than it was before and yeah more words.");
			HotbarToolbarItem item2 = new HotbarToolbarItem();
			item1.setListener(new HotbarToolbarItemListener() {
				@Override
				public boolean onUse(HotbarToolbarItem used, Player user, Action action, boolean sneaking) {
					user.sendMessage("You did it!");
					return true;
				}

				@Override
				public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
					return null;
				}
			});
			HotbarToolbar toolbar = new HotbarToolbar((Player) sender);
			toolbar.addItem(item1, 0);
			toolbar.addItem(item2, 5);
			toolbar.update();
		}

		return true; // Return false if the command was used incorrectly.
	}
}
