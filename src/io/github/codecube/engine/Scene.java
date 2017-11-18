package io.github.codecube.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import io.github.codecube.util.FileUtils;

public class Scene {
	public static final String SCENE_WORLD_PREFIX = "scene_";
	public static final int MAX_TICK_TIME = 200; // How many milliseconds should happen between an update tick. If there
													// is a delay larger than this, it is rounded down and things will
													// go slower.
	public static final int MIN_TICK_TIME = 50; // The least number of milliseconds it should take between ticks. This
												// should be used when creating a thread to call updates on scenes.
	private static List<Scene> startedScenes = new ArrayList<>();

	private World stage = null;
	private List<Prop> props = new ArrayList<>();
	private long lastTick = 0;
	protected String worldName = SCENE_WORLD_PREFIX + UUID.randomUUID().toString();

	public static void updateAllScenes() {
		for (Scene scene : startedScenes) {
			scene.doUpdateTick();
		}
	}

	public static void stopAllScenes() {
		// Reverse order because scenes remove themselves from the list when stopped.
		for (int i = startedScenes.size() - 1; i >= 0; i--) {
			startedScenes.get(i).stopWorld();
		}
	}

	private boolean createWorld() {
		stage = Bukkit.getWorld(worldName);
		if (stage != null) { // There is still gunk in the world folder.
			destroyWorld();
		}

		WorldCreator creator = new WorldCreator(worldName);
		creator.environment(Environment.NORMAL);
		creator.generateStructures(false);
		creator.generator(new GridWorldGenerator());

		stage = creator.createWorld();
		stage.setMonsterSpawnLimit(0);
		stage.setAnimalSpawnLimit(0);

		return true;
	}

	private boolean destroyWorld() {
		if (!Bukkit.unloadWorld(stage, true))
			return false;
		File directory = stage.getWorldFolder();
		FileUtils.deleteFolder(directory);
		return true;
	}

	private void createProps() {
		for (Prop prop : props) {
			prop.create(stage);
		}
	}

	private void destroyProps() {
		for (Prop prop : props) {
			prop.destroy();
		}
	}

	public void startWorld() {
		if (worldStarted())
			return;
		startedScenes.add(this);
		createWorld();
		createProps();
	}

	public void stopWorld() {
		if (!worldStarted())
			return;
		startedScenes.remove(this);
		destroyProps();
		destroyWorld();
	}

	public boolean worldStarted() {
		return stage != null;
	}

	public void addProp(Prop prop) {
		props.add(prop);
		if (stage != null) {
			prop.create(stage);
		}
	}

	public void removeProp(int index) {
		if (stage != null) {
			props.get(index).destroy();
		}
		props.remove(index);
	}

	public void removeProps(Prop toRemove) {
		if (stage != null) {
			toRemove.destroy();
		}
		props.remove(toRemove);
	}

	public void clearProps() {
		if (stage != null) {
			for (Prop prop : props) {
				prop.destroy();
			}
		}
		props.clear();
	}

	public boolean addPlayer(Player joining) {
		if (stage == null)
			if (!createWorld())
				return false;
		return joining.teleport(stage.getSpawnLocation());
	}

	public void doUpdateTick() {
		if (!worldStarted())
			return;
		int time = (int) (System.currentTimeMillis() - lastTick);
		time = Math.min(time, MAX_TICK_TIME);
		lastTick = System.currentTimeMillis();
		for (Prop prop : props) {
			prop.update(time);
		}
	}
}
