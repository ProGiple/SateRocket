package org.satellite.progiple.saterocket.menus.realized.confirm;

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
import org.satellite.progiple.saterocket.menus.realized.confirm.buttons.ConfirmsSetter;

public class ConfirmMenu extends RMenu {
    private final ConfirmsSetter setter;
    public ConfirmMenu(@NonNull Player player, ItemStack itemStack) {
        super(player, new IConfig(SateRocket.getINSTANCE().getDataFolder(), "menus/confirm"));
        this.setter = new ConfirmsSetter(this.getClickableSection(), itemStack);
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
                e.setCancelled(true);
                item.onClick(e);
                return;
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }
}
