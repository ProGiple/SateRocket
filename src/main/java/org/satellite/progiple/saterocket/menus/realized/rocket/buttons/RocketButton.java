package org.satellite.progiple.saterocket.menus.realized.rocket.buttons;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.menus.IMenu;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;

public interface RocketButton {
    ItemStack getItemStack();
    byte getSlot();
    Item onClick(InventoryClickEvent e);
    Item insert(IMenu rocketMenu);
    void updateItemLore(RocketMenu rocketMenu);
}
