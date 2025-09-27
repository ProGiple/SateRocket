package org.satellite.progiple.saterocket.menus.realized.rocket;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.IMenu;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.eco.BuyingNomy;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.RocketButton;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized.ForSendSlotButton;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class RItem extends Item implements RocketButton {
    public RItem(@NonNull ConfigurationSection section, @Range(from = 0L, to = 54L) int slot) {
        super(section, slot);
    }

    @Override
    public Item insert(IMenu rocketMenu) {
        rocketMenu.getInventory().setItem(this.getSlot(), this.getItemStack());
        return this;
    }

    public void updateItemLore(RocketMenu rocketMenu) {
        int storagedItems = rocketMenu.getDump().itemStacks.size();
        int putted = (int) rocketMenu.getSetter().getItems()
                .stream()
                .filter(i -> i instanceof ForSendSlotButton b && b.getItem() != null)
                .count();

        List<String> defaultLore = this.getDefaultLore();
        if (defaultLore == null || defaultLore.isEmpty()) return;

        defaultLore = new ArrayList<>(defaultLore);
        defaultLore.replaceAll(l -> Utils.setPlaceholders(rocketMenu.getPlayer(),
                Utils.applyReplacements(l,
                        "putted-%-" + putted,
                        "vault_cost-%-" + BuyingNomy.getCost(true, storagedItems, putted),
                        "jewels_cost-%-" + BuyingNomy.getCost(false, storagedItems, putted),
                        "storaged-%-" + storagedItems,
                        "max-%-" + rocketMenu.getMaxItems())));

        this.setLore(defaultLore);
        this.insert(rocketMenu);
    }
}
