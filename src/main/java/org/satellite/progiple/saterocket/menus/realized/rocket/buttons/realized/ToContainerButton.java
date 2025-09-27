package org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.satellite.progiple.saterocket.menus.realized.rocket.RItem;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.RocketButton;
import org.satellite.progiple.saterocket.menus.realized.storage.StorageMenu;

public class ToContainerButton extends RItem {
    public ToContainerButton(@NonNull ConfigurationSection section, @Range(from = 0L, to = 54L) int slot) {
        super(section, slot);
    }

    @Override
    public Item onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        MenuManager.openInventory(new StorageMenu(player));
        return this;
    }
}
