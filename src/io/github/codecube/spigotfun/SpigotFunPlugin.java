package io.github.codecube.spigotfun;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.codecube.creation.CommandBuildObject;
import io.github.codecube.creation.CommandSaveObject;
import io.github.codecube.creation.EditorWorld;
import io.github.codecube.creation.ToolbarListener;

public class SpigotFunPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		getCommand("funtest").setExecutor(new CommandFunTest());

		// Register the test event listener.
		getServer().getPluginManager().registerEvents(new TestListener(), this);

		// Ok, now for real non-testing stuff.
		// Load the editor world now, otherwise when it is created later, the code will
		// not realize that there is already stuff in it and it will not be cleared.
		EditorWorld.getInstance().getWorld(); // Sets up the editor world.

		// This command allows for creation of objects.
		getCommand("buildobject").setExecutor(new CommandBuildObject());
		// This command saves the object the user is editing to a file to be used later.
		getCommand("save").setExecutor(new CommandSaveObject()); // TODO: NYI

		// Handles the user using toolbar items in their hotbar.
		getServer().getPluginManager().registerEvents(new ToolbarListener(), this);
	}

	// Called when the plugin is unloaded / disabled.
	@Override
	public void onDisable() {

	}
}
