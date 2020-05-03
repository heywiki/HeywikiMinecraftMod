package org.wikiwizard.papermc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HeywikiPlugin extends JavaPlugin {

	MqttManager mqttManager;
	WorkCounter workCounter;
	EventTracer eventTracer;
	
	@Override
	public void onEnable() {
		getLogger().info("HeywikiMinecraftMod Version " 
				+ getDescription().getVersion());
		
		//needed for config.yml
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		mqttManager = new MqttManager();
		getCommand(MqttManager.CMD_MQTTCONF).setExecutor(mqttManager);
		getCommand(MqttManager.CMD_MQTTPUB).setExecutor(mqttManager);

		PluginManager pluginManager = getServer().getPluginManager();
		
		//for debbuging purposes
		eventTracer = new EventTracer();
		pluginManager.registerEvents(eventTracer, this);

		workCounter = new WorkCounter();
		pluginManager.registerEvents(workCounter, this);
	}

	@Override
	public void onDisable() {
		mqttManager.onDisable();
	}
	
	public MqttManager getMqttManager()  {
		return mqttManager;
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