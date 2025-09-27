package org.satellite.progiple.saterocket.menus.realized.rocket;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.SateRocket;
import org.satellite.progiple.saterocket.menus.RMenu;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.RocketButton;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.RocketButtonSetter;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized.ForSendSlotButton;

import java.util.List;

@Getter
public class RocketMenu extends RMenu {
    private final RocketButtonSetter setter;
    private final int maxItems;
    public RocketMenu(@NonNull Player player) {
        super(player, new IConfig(SateRocket.getINSTANCE().getDataFolder(), "menus/rocket"));
        this.setter = new RocketButtonSetter(this.getClickableSection());

        List<String> storageList = new IConfig(SateRocket.getINSTANCE().getDataFolder(), "menus/storage").getStringList("items.clickable.STORAGE_SLOT.slots");
        this.maxItems = Utils.getSlotList(storageList).size();
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.setter.getItems().forEach(i -> i.insert(this));
        this.updateAllLores();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);
        if (e.isCancelled()) return;

        int slot = e.getSlot();
        if (slot == e.getRawSlot()) e.setCancelled(true);

        ItemStack target = e.getCurrentItem();
        if (target == null || target.getType().isAir()) return;

        for (RocketButton item : this.setter.getItems()) {
            if (target.equals(item.getItemStack()) && item.getSlot() == slot) {
                Item clicked = item.onClick(e);

                e.setCancelled(true);
                if (clicked != null && item instanceof ForSendSlotButton sendSlotButton) sendSlotButton.insert(this);

                this.updateAllLores();
                return;
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        this.setter.getItems().forEach(i -> {
            if (i instanceof ForSendSlotButton forSendSlotButton) {
                ItemStack itemStack = forSendSlotButton.getItem();
                if (itemStack != null) {
                    itemStack = itemStack.clone();
                    NBTManager.removeKey(itemStack, Item.MARKER_NBT);
                    this.getPlayer().getInventory().addItem(itemStack);
                }
            }
        });
    }

    public void updateAllLores() {
        this.setter.getItems().forEach(i -> {
            i.updateItemLore(this);
            i.insert(this);
        });
    }
}
