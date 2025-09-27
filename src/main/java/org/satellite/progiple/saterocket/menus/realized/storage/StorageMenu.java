package org.satellite.progiple.saterocket.menus.realized.storage;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.satellite.progiple.saterocket.SateRocket;
import org.satellite.progiple.saterocket.menus.RMenu;
import org.satellite.progiple.saterocket.menus.realized.storage.buttons.StorageSetter;

public class StorageMenu extends RMenu {
    private final StorageSetter setter;
    public StorageMenu(@NonNull Player player) {
        super(player, new IConfig(SateRocket.getINSTANCE().getDataFolder(), "menus/storage"));
        this.setter = new StorageSetter(this.getClickableSection(), this.getDump());
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.setter.insert(this);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);
        if (e.isCancelled()) return;

        int slot = e.getSlot();
        if (slot == e.getRawSlot()) e.setCancelled(true);

        ItemStack target = e.getCurrentItem();
        if (target == null || target.getType().isAir()) return;

        for (Item item : this.setter.getItems()) {
            if (item.getItemStack().equals(target) && item.getSlot() == e.getSlot()) {
                item.onClick(e);
                e.setCancelled(true);
                return;
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }
}
