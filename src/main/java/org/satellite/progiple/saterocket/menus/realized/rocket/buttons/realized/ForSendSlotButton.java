package org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.menus.IMenu;
import org.novasparkle.lunaspring.API.menus.items.Item;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.satellite.progiple.saterocket.configs.Config;
import org.satellite.progiple.saterocket.menus.realized.rocket.RItem;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.RocketButton;

@Getter
public class ForSendSlotButton implements RocketButton {
    private ItemStack item;
    private final RItem local;
    private final byte slot;
    public ForSendSlotButton(ConfigurationSection localSection, byte slot) {
        this.slot = slot;
        this.local = new RItem(localSection, slot) {};
    }

    @Override
    public ItemStack getItemStack() {
        return this.item != null ? this.item : this.local.getItemStack();
    }

    @Override @SuppressWarnings("deprecation")
    public Item onClick(InventoryClickEvent e) {
        ItemStack cursor = e.getCursor();
        if (this.inBlacklist(cursor)) {
            Config.sendMessage(e.getWhoClicked(), "itemInBlacklist");
            return null;
        }

        if (((cursor == null ||
                cursor.getType().isAir()) && this.item == null) ||
                    ((cursor != null && !cursor.getType().isAir()) && this.item != null)) return null;

        if (this.item != null) {
            ItemStack itemStack = this.item.clone();
            NBTManager.removeKey(itemStack, Item.MARKER_NBT);

            e.setCursor(itemStack);
            this.item = null;
        } else {
            this.item = cursor.clone();
            this.item.setAmount(1);
            NBTManager.setBool(this.item, Item.MARKER_NBT, true);

            cursor.setAmount(cursor.getAmount() - 1);
        }

        return this.local;
    }

    public boolean inBlacklist(ItemStack item) {
        if (item == null || item.getType().isAir() || item.getAmount() <= 0) return false;

        ConfigurationSection blacklists = Config.getSection("settings.blacklists");
        Material material = item.getType();
        if (blacklists.getBoolean("disableShulkers") && material.name().endsWith("SHULKER_BOX")) return true;

        if (blacklists.getStringList("materials").contains(material.name())) return true;

        return blacklists.getStringList("nbt").stream().anyMatch(n -> NBTManager.hasTag(item, n));
    }

    public void clear(IMenu iMenu) {
        this.item = null;
        this.local.insert(iMenu);
    }

    @Override
    public Item insert(IMenu rocketMenu) {
        if (this.item == null) this.local.insert(rocketMenu);
        else rocketMenu.getInventory().setItem(this.slot, this.item);
        return this.local;
    }

    @Override
    public void updateItemLore(RocketMenu rocketMenu) {
        this.local.updateItemLore(rocketMenu);
    }
}
