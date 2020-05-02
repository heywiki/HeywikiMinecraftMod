package org.wikiwizard.papermc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatInterface extends JavaPlugin {

	public static final String VERSION = "0.2";

	@Override
	public void onEnable() {
		this.getLogger().info("HeywikiMinecraftMod Version " + VERSION);
		this.getConfig().options().copyDefaults();
		this.saveDefaultConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		if (command.getName().contentEquals("version")) {
			player.sendMessage("Version " + VERSION);
		}
		else if (command.getName().contentEquals("die")) {
			player.setHealth(0);
		}
		else if (command.getName().contentEquals("healthplease")) {
			player.setHealth(player.getHealth() + 1);
		}
		else if (command.getName().contentEquals("mqtt")) {
			//todo...
		}	
		else if (command.getName().contentEquals("mqttconfig")) {
			player.sendMessage("mqtt-server : " + getConfig().getString("mqtt-server"));
		}
		return true;
	}
}