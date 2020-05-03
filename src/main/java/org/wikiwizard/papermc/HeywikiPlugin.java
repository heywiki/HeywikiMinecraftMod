package org.wikiwizard.papermc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HeywikiPlugin extends JavaPlugin {

	MqttManager mqtt;
	
	@Override
	public void onEnable() {
		getLogger().info("HeywikiMinecraftMod Version " 
				+ getDescription().getVersion());
		
		//needed for config.yml
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		mqtt = new MqttManager();
		getCommand(MqttManager.CMD_MQTTCONF).setExecutor(mqtt);
		getCommand(MqttManager.CMD_MQTTPUB).setExecutor(mqtt);
	}

	@Override
	public void onDisable() {
		mqtt.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {

		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		if (command.getName().contentEquals("version")) {
			player.sendMessage("Version " + this.getDescription().getVersion());
			return true;
		}
		else if (command.getName().contentEquals("traceargs")) {
			
			StringBuilder buf = new StringBuilder();
			for(int i=0; i<args.length; i++) {
				buf.append("arg " + i + ": " + args[i] + "\n");
			}
			
			player.sendMessage(buf.toString());
			return true;
		}
		
		//some cheat commands for demo purposes....
		else if (command.getName().contentEquals("die")) {
			player.setHealth(0);
			return true;
		}
		else if (command.getName().contentEquals("healthplease")) {
			player.setHealth(player.getHealth() + 1);
			return true;
		}
		
		return false;
	}
}