package io.github.codecube.engine;

import java.util.Timer;
import java.util.TimerTask;

public class ScenesUpdater {
	private static final TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Scene.updateAllScenes();
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
