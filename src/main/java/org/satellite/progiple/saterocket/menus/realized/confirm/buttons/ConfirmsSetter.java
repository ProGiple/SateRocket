package org.satellite.progiple.saterocket.menus.realized.confirm.buttons;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.menus.ItemListMenu;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.menus.realized.confirm.buttons.realized.AcceptButton;
import org.satellite.progiple.saterocket.menus.realized.confirm.buttons.realized.CancelButton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ConfirmsSetter {
    private final Set<Item> items = new HashSet<>();
    public ConfirmsSetter(ConfigurationSection section, ItemStack targetItem) {
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            if (itemSection == null) continue;

            List<Integer> slots = Utils.getSlotList(itemSection.getStringList("slots"));
            if (key.equalsIgnoreCase("CONFIRM")) {
                slots.forEach(s -> this.items.add(new AcceptButton(itemSection, s, targetItem)));
                continue;
            }

            if (key.equalsIgnoreCase("INFO")) {
                this.items.add(new Item(NonMenuItem.fromItemStack(targetItem.clone()), (byte) itemSection.getInt("slot")));
                continue;
            }

            slots.forEach(s -> this.items.add(new CancelButton(itemSection, s)));
        }
    }

    public void insert(ItemListMenu iMenu) {
        this.items.forEach(i -> i.insert(iMenu));
    }
}
