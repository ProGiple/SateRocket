package org.satellite.progiple.saterocket.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.satellite.progiple.saterocket.configs.LocConfig;

public class OnBreakHandler implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (LocConfig.getLocations().contains(block.getLocation())) {
            if (!e.getPlayer().hasPermission("saterocket.admin")) {
                e.setCancelled(true);
                return;
            }

            LocConfig.remove(block.getLocation());
            LocConfig.save();
        }
    }
}
