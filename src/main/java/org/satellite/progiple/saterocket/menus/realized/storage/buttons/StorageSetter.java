package org.satellite.progiple.saterocket.menus.realized.storage.buttons;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaspring.API.menus.IMenu;
import org.novasparkle.lunaspring.API.menus.ItemListMenu;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;
import org.satellite.progiple.saterocket.menus.realized.storage.StorageMenu;
import org.satellite.progiple.saterocket.storage.Dump;

import java.util.HashSet;
import java.util.Set;

@Getter
public class StorageSetter {
    private final Set<Item> items = new HashSet<>();
    public StorageSetter(ConfigurationSection section, Dump dump) {
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            if (itemSection == null) continue;

            if (key.startsWith("BACK")) {
                this.items.add(new Item(itemSection, itemSection.getInt("slot")){
                    @Override
                    public Item onClick(InventoryClickEvent e) {
                        Player player = (Player) e.getWhoClicked();
                        MenuManager.openInventory(new RocketMenu(player));
                        return this;
                    }
                });
                continue;
            }

            if (key.equalsIgnoreCase("STORAGE_SLOT")) {
                int index = 0;
                for (int s : Utils.getSlotList(itemSection.getStringList("slots"))) {
                    this.items.add(new StorageItem(itemSection, s, dump, index));
                    index++;
                }
            }
        }
    }

    public void insert(ItemListMenu iMenu) {
        this.items.forEach(i -> i.insert(iMenu));
    }
}
