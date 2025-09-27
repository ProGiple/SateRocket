package org.satellite.progiple.saterocket.menus.realized.storage.buttons;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.ItemListMenu;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.satellite.progiple.saterocket.menus.realized.confirm.ConfirmMenu;
import org.satellite.progiple.saterocket.storage.Dump;

public class StorageItem extends Item {
    private final ItemStack storagedItem;
    private final Dump dump;
    public StorageItem(@NonNull ConfigurationSection section, @Range(from = 0L, to = 54L) int slot, Dump dump, int index) {
        super(section, slot);
        this.dump = dump;
        this.storagedItem = dump.itemStacks.size() <= index ? null : dump.itemStacks.get(index);
    }

    @Override
    public ItemStack getItemStack() {
        return this.storagedItem != null ? this.storagedItem : super.getItemStack();
    }

    @Override
    public Item insert(@NotNull ItemListMenu iMenu) {
        if (this.storagedItem != null) iMenu.getInventory().setItem(this.getSlot(), this.storagedItem);
        else super.insert(iMenu);
        return this;
    }

    @Override
    public Item onClick(InventoryClickEvent e) {
        if (this.storagedItem == null) return this;
        MenuManager.openInventory(new ConfirmMenu(this.dump.getPlayer(), this.storagedItem));
        return this;
    }
}
