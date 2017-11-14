package io.github.codecube.spigotfun;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotFunPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		this.getCommand("funtest").setExecutor(new CommandFunTest());
	}

	// Called when the plugin is unloaded / disabled.
	@Override
	public void onDisable() {

	}
}
