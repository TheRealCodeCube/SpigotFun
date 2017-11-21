package io.github.codecube.waterfall.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Particles {
	public static void drawParticles(List<Player> receivers, Particle type, Location position, int amount,
			double spread) {
		for (Player player : receivers) {
			player.spawnParticle(type, position, 1, spread, spread, spread);
		}
	}

	public static void drawParticleLine(List<Player> receivers, Particle type, Location start, Vector offset,
			double spacing) {
		Vector unit = offset.clone().normalize().multiply(spacing);
		double iterations = offset.length() / unit.length();
		for (int i = 0; i < iterations; i++) {
			for (Player player : receivers) {
				player.spawnParticle(type, start, 1, 0.0, 0.0, 0.0, 0.0);
				start.add(unit);
			}
		}
	}

	public static void drawParticle(List<Player> receivers, Particle type, Location position) {
		for (Player player : receivers) {
			player.spawnParticle(type, position, 1);
		}
	}

	public static void drawColoredParticle(List<Player> receivers, Particle type, Location position, double r, double g,
			double b) {
		for (Player player : receivers) {
			player.spawnParticle(type, position, 0, r, g, b, 1.0);
		}
	}

	public static void drawColoredParticleLine(List<Player> receivers, Particle type, Location start, double r,
			double g, double b, Vector offset, double spacing) {
		Vector unit = offset.clone().normalize().multiply(spacing);
		double iterations = offset.length() / unit.length();
		for (int i = 0; i < iterations; i++) {
			for (Player player : receivers) {
				player.spawnParticle(type, start, 0, r, g, b, 1.0);
				start.add(unit);
			}
		}
	}
}
