package io.github.codecube.waterfall.scene;

import java.util.Timer;
import java.util.TimerTask;

import io.github.codecube.waterfall.animation.AnimPropertySet;

public class ScenesUpdater {
	private static final TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Scene.updateAllScenes();
			AnimPropertySet.updateAllAnimations();
		}
	};
	private static Timer timer = null;

	private ScenesUpdater() {

	}

	public static void start() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(task, 0, Scene.MIN_TICK_TIME);
		}
	}

	public static void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
