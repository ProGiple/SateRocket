package org.satellite.progiple.saterocket.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.satellite.progiple.saterocket.configs.LocConfig;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;

public class OnInteractHandler implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null || block.getType().isAir() || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (LocConfig.getLocations().contains(block.getLocation())) {
            Player player = e.getPlayer();

            e.setCancelled(true);
            MenuManager.openInventory(new RocketMenu(player));
        }
    }
}
