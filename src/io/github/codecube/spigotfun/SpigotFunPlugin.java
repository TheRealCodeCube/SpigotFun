package io.github.codecube.spigotfun;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.codecube.creation.CommandBuildObject;
import io.github.codecube.creation.CommandSaveObject;

public class SpigotFunPlugin extends JavaPlugin {
	// Called when the plugin is first registered.
	@Override
	public void onEnable() {
		// Register the FUNTEST command! :D SoFuN!
		getCommand("funtest").setExecutor(new CommandFunTest());

		// Register the test event listener.
		getServer().getPluginManager().registerEvents(new TestListener(), this);

		// Load the objecteditor world now, otherwise when it is created later, the code
		// will not realize that there is already stuff in it and it will not be
		// cleared.
		getServer().getWorlds().add(CommandBuildObject.OBJECT_EDITOR_WORLD_PREFIX + "0");

		// Ok, now for real non-testing stuff.
		// This command allows for creation of objects.
		getCommand("buildobject").setExecutor(new CommandBuildObject());
		// This command saves the object the user is editing to a file to be used later.
		getCommand("save").setExecutor(new CommandSaveObject());
	}

	// Called when the plugin is unloaded / disabled.
	@Override
	public void onDisable() {

	}
}
