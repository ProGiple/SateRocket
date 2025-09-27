package org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Range;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.satellite.progiple.saterocket.configs.Config;
import org.satellite.progiple.saterocket.eco.BuyingNomy;
import org.satellite.progiple.saterocket.eco.BuyingType;
import org.satellite.progiple.saterocket.eco.realized.Vault;
import org.satellite.progiple.saterocket.menus.realized.rocket.RItem;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;
import org.satellite.progiple.saterocket.storage.Dump;

import java.util.List;

public class SendButton extends RItem {
    public SendButton(@NonNull ConfigurationSection section, @Range(from = 0L, to = 54L) int slot) {
        super(section, slot);
    }

    @Override
    public Item onClick(InventoryClickEvent e) {
        RocketMenu rocketMenu = this.getMenu() instanceof RocketMenu rMenu ? rMenu : null;
        if (rocketMenu == null) return this;

        Player player = rocketMenu.getPlayer();
        ClickType clickType = e.getClick();

        BuyingType buyingType = BuyingType.valueOf(Config.getString("settings.buying_type"));
        BuyingNomy economy = clickType.isRightClick() ? buyingType.getRight() : (clickType.isLeftClick() ? buyingType.getLeft() : null);
        if (economy == null) return this;

        boolean isVault = economy.equals(Vault.getEconomy());
        List<ForSendSlotButton> sendSlotButtons = rocketMenu.getSetter().getItems()
                .stream()
                .filter(i -> i instanceof ForSendSlotButton f && f.getItem() != null)
                .map(i -> (ForSendSlotButton) i)
                .toList();
        if (sendSlotButtons.isEmpty()) {
            Config.sendMessage(player, "forSendItemsAreNull");
            return this;
        }

        Dump dump = rocketMenu.getDump();
        int storagedItems = dump.itemStacks.size();
        if (storagedItems + sendSlotButtons.size() > rocketMenu.getMaxItems()) {
            Config.sendMessage(player, "storageIsFull");
            return this;
        }

        int cost = BuyingNomy.getCost(isVault, storagedItems, sendSlotButtons.size());
        if (economy.get(player) < cost) {
            Config.sendMessage(player, isVault ? "noMoney" : "noJewels", String.valueOf(cost));
            return this;
        }

        economy.add(player, -cost);
        Config.sendMessage(player, "sendToRocket");

        sendSlotButtons.forEach(b -> {
            dump.itemStacks.add(b.getItem());
            b.clear(rocketMenu);
        });

        dump.putItems();
        return this;
    }
}
