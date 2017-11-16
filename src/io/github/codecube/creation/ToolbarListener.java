package io.github.codecube.creation;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToolbarListener implements Listener {
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
				event.getAction()));
	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
				(event.getBlock().getType() == Material.AIR) ? Action.LEFT_CLICK_AIR : Action.LEFT_CLICK_BLOCK));
	}
}
