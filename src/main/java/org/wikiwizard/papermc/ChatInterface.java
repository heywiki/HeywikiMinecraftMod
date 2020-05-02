package org.wikiwizard.papermc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatInterface extends JavaPlugin {

	public static final String VERSION = "0.1";
	
	@Override
	public void onDisable() {
		this.getLogger().info("HeywikiMinecraftMod Version " + VERSION);
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		if (command.getName().contentEquals("hey-version")) {
			player.sendMessage("Version " + VERSION);
		}
		else if (command.getName().contentEquals("hey-die")) {
			player.setHealth(0);
		}
		else if (command.getName().contentEquals("hey-healthplease")) {
			player.setHealth(player.getHealth() + 1);
		}		
		return true;
	}
}