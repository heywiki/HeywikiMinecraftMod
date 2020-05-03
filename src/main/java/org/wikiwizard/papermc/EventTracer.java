package org.wikiwizard.papermc;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Helper listener to see wich events get fired by what
 * https://hub.spigotmc.org/javadocs/bukkit/index.html?overview-summary.html
 * 
 * @author Christoph Sauer
 */
public class EventTracer implements Listener {
	
	HeywikiPlugin plugin;
	Logger log;
	
	public EventTracer() {
		plugin = HeywikiPlugin.getPlugin(HeywikiPlugin.class);
		log = plugin.getLogger();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)  {	
		log.info("EventTracer.onBlockBreak: " 
				+ event.getBlock().getBlockData().getAsString());
		//log.info(MiscUtil.getJSON(event.getBlock().toString());
	}
	
	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event)  {	
		log.info("EventTracer.onBlockDispense: " 
				+ event.getBlock().getBlockData().getAsString());
		//log.info(MiscUtil.getJSON(event.getBlock().toString());
	}

	@EventHandler
	public void onPickupItem(EntityPickupItemEvent event)  {
		log.info("EventTracer.onPickupItem: by "
				+ event.getEntityType().getKey().getKey()
				+ ", item " + event.getItem().getCustomName());
	}
	
	@EventHandler
	public void onDropItem(EntityDropItemEvent event)  {
		log.info("EventTracer.onDropItem: by "
				+ event.getEntityType().getKey().getKey()
				+ ", item " + event.getItemDrop().getCustomName());
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)  {
		log.info("EventTracer.onPlayerDropItem: by "
				+ event.getPlayer().getName()
				+ ", item " + event.getItemDrop().getCustomName());
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent event)  {
		log.info("EventTracer.onCraftItem: " 
				+ event.getCurrentItem().getItemMeta().getDisplayName());
	}
}