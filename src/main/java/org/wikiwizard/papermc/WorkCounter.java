package org.wikiwizard.papermc;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This will count publish a work event
 * 
 * @author heywiki
 *
 */
public class WorkCounter implements Listener {

	HeywikiPlugin plugin;
	Logger log;
	
	public WorkCounter() {
		plugin = HeywikiPlugin.getPlugin(HeywikiPlugin.class);
		log = plugin.getLogger();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)  {
		String player = event.getPlayer().getName();
		String material = event.getBlock().getType().getKey().getKey();
		
		if (material.endsWith("_log")) {
			String message = 
					"WorkCounter WoodHarvested:" + material + " by " + player;
			log.info(message);
			plugin.getMqttManager().publish(message);
		}
	}
}