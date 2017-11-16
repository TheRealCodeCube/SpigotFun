package io.github.codecube.creation;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;

import io.github.codecube.util.FileUtils;

public class EditorWorld {
	public static final String EDITOR_WORLD_PREFIX = "editor";

	private static EditorWorld instance = new EditorWorld();

	private World editorWorld;

	private EditorWorld() {

	}

	public static EditorWorld getInstance() {
		return instance;
	}

	public World getWorld() {
		editorWorld = Bukkit.getWorld(EDITOR_WORLD_PREFIX + "0");
		if (editorWorld != null) {
			System.out.println("Deleting world...");
			if (!Bukkit.unloadWorld(editorWorld, true)) {
				return null;
			}
			File directory = editorWorld.getWorldFolder();
			FileUtils.deleteFolder(directory);
		}

		WorldCreator creator = new WorldCreator(EDITOR_WORLD_PREFIX + "0");
		creator.environment(Environment.NORMAL);
		creator.generateStructures(false);
		creator.generator(new GridWorldGenerator());

		editorWorld = creator.createWorld();
		editorWorld.setMonsterSpawnLimit(0);
		editorWorld.setAnimalSpawnLimit(0);

		return editorWorld;
	}
}
