package io.github.codecube.creation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToolbarListener implements Listener {
	private static Map<UUID, Long> debouncing = new HashMap<>();
	private static final long DEBOUNCE_INTERVAL = 100;

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					event.getAction()));
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					(event.getBlock().getType() == Material.AIR) ? Action.LEFT_CLICK_AIR : Action.LEFT_CLICK_BLOCK));
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}
}
