package io.github.codecube.spigotfun;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Bukkit.broadcastMessage("Player has just joined!");
	}
}
