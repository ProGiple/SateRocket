package org.satellite.progiple.saterocket.menus.realized.confirm.buttons.realized;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.satellite.progiple.saterocket.configs.Config;
import org.satellite.progiple.saterocket.menus.realized.storage.StorageMenu;
import org.satellite.progiple.saterocket.storage.Dump;

public class AcceptButton extends Item {
    private final ItemStack target;
    public AcceptButton(@NonNull ConfigurationSection section, @Range(from = 0L, to = 54L) int slot, ItemStack targetItem) {
        super(section, slot);
        this.target = targetItem;
    }

    @Override
    public Item onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        Dump dump = Dump.create((Player) e.getWhoClicked());
        if (dump.itemStacks.remove(this.target)) {
            ItemStack cloned = this.target.clone();
            NBTManager.removeKey(cloned, Item.MARKER_NBT);

            player.getInventory().addItem(cloned);
            dump.putItems();

            Config.sendMessage(player, "itemIsPickup");
            MenuManager.openInventory(new StorageMenu(player));
        }
        return this;
    }
}
