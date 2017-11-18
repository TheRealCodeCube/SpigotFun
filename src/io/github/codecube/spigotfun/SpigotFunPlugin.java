package io.github.codecube.spigotfun;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.codecube.creation.CommandBuildObject;
import io.github.codecube.creation.CommandSaveObject;
import io.github.codecube.creation.ToolbarListener;
import io.github.codecube.engine.Scene;
import io.github.codecube.engine.ScenesUpdater;

public class SpigotFunPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		getCommand("funtest").setExecutor(new CommandFunTest());

		// Register the test event listener.
		getServer().getPluginManager().registerEvents(new TestListener(), this);

		// This command allows for creation of objects.
		getCommand("buildobject").setExecutor(new CommandBuildObject());
		// This command saves the object the user is editing to a file to be used later.
		getCommand("save").setExecutor(new CommandSaveObject()); // TODO: NYI

		// Handles the user using toolbar items in their hotbar.
		getServer().getPluginManager().registerEvents(new ToolbarListener(), this);

		// Performs updates on scenes so that ai, animations, and other logic will
		// execute.
		ScenesUpdater.start();
	}

	// Called when the plugin is unloaded / disabled.
	@Override
	public void onDisable() {
		ScenesUpdater.stop();
		Scene.stopAllScenes();
	}
}
