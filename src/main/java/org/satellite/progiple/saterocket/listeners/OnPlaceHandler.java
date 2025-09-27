package org.satellite.progiple.saterocket.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.satellite.progiple.saterocket.configs.LocConfig;

public class OnPlaceHandler implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        ItemStack itemStack = e.getItemInHand();
        if (block.getType() != itemStack.getType() || !NBTManager.hasTag(itemStack, "is_setter_block")) return;

        LocConfig.add(block.getLocation());
        LocConfig.save();
    }
}
