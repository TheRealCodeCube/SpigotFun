package io.github.codecube.creation;

import io.github.codecube.engine.Scene;

public class EditorScene extends Scene {
	public static final String EDITOR_WORLD_PREFIX = "editor_";
	private static EditorScene instance = new EditorScene();

	public static EditorScene getInstance() {
		return instance;
	}

	private EditorScene() {
		super();
		worldName = worldName.replaceFirst(SCENE_WORLD_PREFIX, EDITOR_WORLD_PREFIX);
		startWorld();
	}
}
