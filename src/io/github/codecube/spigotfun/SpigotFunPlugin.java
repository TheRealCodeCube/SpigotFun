package io.github.codecube.spigotfun;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotFunPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		getCommand("funtest").setExecutor(new CommandFunTest());

		// Register the test event listener.
		getServer().getPluginManager().registerEvents(new TestListener(), this);
	}

	// Called when the plugin is unloaded / disabled.
	@Override
	public void onDisable() {

	}
}
