package io.github.codecube.spigotfun;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.codecube.engine.Particles;

public class CommandFunTest implements CommandExecutor {
	// Called when someone uses the command.
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Test out particle systems.
		List<Player> players = new ArrayList<>();
		players.add((Player) sender);
		Particles.drawParticleLine(players, Particle.DRAGON_BREATH, players.get(0).getLocation(), new Vector(2, 2, 2),
				0.1);

		return true; // Return false if the command was used incorrectly.
	}
}
