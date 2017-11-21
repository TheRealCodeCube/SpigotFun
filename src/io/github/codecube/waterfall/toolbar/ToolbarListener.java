package io.github.codecube.waterfall.toolbar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ToolbarListener implements Listener {
	private static Map<UUID, Long> debouncing = new HashMap<>();
	private static final long DEBOUNCE_INTERVAL = 50;

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					event.getAction()));
		else
			event.setCancelled(true);
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					(event.getBlock().getType() == Material.AIR) ? Action.LEFT_CLICK_AIR : Action.LEFT_CLICK_BLOCK));
		else
			event.setCancelled(true);
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					Action.RIGHT_CLICK_BLOCK));
		else
			event.setCancelled(true);
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			Long lastTime = debouncing.get(player.getUniqueId());
			if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
				event.setCancelled(
						HotbarToolbar.use(player, player.getInventory().getHeldItemSlot(), Action.LEFT_CLICK_BLOCK));
			else
				event.setCancelled(true);
			debouncing.put(player.getUniqueId(), System.currentTimeMillis());
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		HotbarToolbar.unlinkToolbar(event.getPlayer());
	}
}
