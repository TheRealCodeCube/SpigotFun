package io.github.codecube.waterfall.core;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.codecube.waterfall.editor.CommandOpenEditor;
import io.github.codecube.waterfall.editor.CommandSaveObject;
import io.github.codecube.waterfall.scene.Scene;
import io.github.codecube.waterfall.scene.ScenesUpdater;
import io.github.codecube.waterfall.toolbar.ToolbarListener;

public class WaterfallPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		getCommand("funtest").setExecutor(new CommandFunTest());

		// Register the test event listener.
		getServer().getPluginManager().registerEvents(new TestListener(), this);

		// This command allows for editing of things.
		getCommand("editor").setExecutor(new CommandOpenEditor());
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
