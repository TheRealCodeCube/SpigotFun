package io.github.codecube.waterfall.toolbar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.codecube.waterfall.core.WaterfallPlugin;

public class ToolbarListener implements Listener {
	private static Map<UUID, Long> debouncing = new HashMap<>();
	private static final long DEBOUNCE_INTERVAL = 50;

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		Action action = event.getAction();
		HTIUseMode mode = ((action == Action.LEFT_CLICK_AIR) || (action == Action.LEFT_CLICK_BLOCK))
				? HTIUseMode.LEFT_CLICK
				: HTIUseMode.RIGHT_CLICK;
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(
					HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(), mode));
		else
			event.setCancelled(true);
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					HTIUseMode.LEFT_CLICK));
		else
			event.setCancelled(true);
		debouncing.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
		Long lastTime = debouncing.get(event.getPlayer().getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
			event.setCancelled(HotbarToolbar.use(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(),
					HTIUseMode.RIGHT_CLICK));
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
						HotbarToolbar.use(player, player.getInventory().getHeldItemSlot(), HTIUseMode.LEFT_CLICK));
			else
				event.setCancelled(true);
			debouncing.put(player.getUniqueId(), System.currentTimeMillis());
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		HotbarToolbar.unlinkToolbar(event.getPlayer());
	}

	@EventHandler
	public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			Long lastTime = debouncing.get(player.getUniqueId());
			if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL))
				event.setCancelled(
						HotbarToolbar.use(player, player.getInventory().getHeldItemSlot(), HTIUseMode.MIDDLE_CLICK));
			else
				event.setCancelled(true);
			if (event.isCancelled()) {
				// Yes, redundant, but otherwise the client will still change slots even though
				// the event has been cancelled. Cancelling only prevents the block from
				// entering the player's inventory.
				player.getInventory().setHeldItemSlot(player.getInventory().getHeldItemSlot());
			}
			debouncing.put(player.getUniqueId(), System.currentTimeMillis());
		}
	}

	private void playerDropItemInternal(Player whoDidIt) {
		HotbarToolbar.use(whoDidIt, whoDidIt.getInventory().getHeldItemSlot(), HTIUseMode.THROW);
	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Long lastTime = debouncing.get(player.getUniqueId());
		if ((lastTime == null) || (System.currentTimeMillis() - lastTime > DEBOUNCE_INTERVAL)) {
			if (HotbarToolbar.canUse(player, player.getInventory().getHeldItemSlot())) {
				event.setCancelled(true);
				// This painfulness is necessary because using items can open up new toolbars
				// and the old item would still hang around if we did use() immediately, since
				// the event finishes canceling after use would be called.
				Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(WaterfallPlugin.class),
						new Runnable() {
							@Override
							public void run() {
								playerDropItemInternal(event.getPlayer());
							}
						}, 2l);
			}

		} else
			event.setCancelled(true);
		debouncing.put(player.getUniqueId(), System.currentTimeMillis());
	}
}
