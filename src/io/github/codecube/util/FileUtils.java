package io.github.codecube.util;

import java.io.File;

public class FileUtils {
	public static void deleteFolder(File folder) {
		File[] children = folder.listFiles();
		if (children != null) {
			for (File file : children) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		folder.delete();
	}
}
