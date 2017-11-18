package io.github.codecube.creation;

import io.github.codecube.engine.Scene;

public class EditorWorld extends Scene {
	public static final String EDITOR_WORLD_PREFIX = "editor_";
	private static EditorWorld instance = new EditorWorld();

	public static EditorWorld getInstance() {
		return instance;
	}

	private EditorWorld() {
		super();
		worldName = worldName.replaceFirst(SCENE_WORLD_PREFIX, EDITOR_WORLD_PREFIX);
		startWorld();
	}
}
