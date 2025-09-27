package org.satellite.progiple.saterocket.menus;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import org.novasparkle.lunaspring.API.menus.AMenu;
import org.satellite.progiple.saterocket.storage.Dump;

import java.util.Objects;

@Getter
public abstract class RMenu extends AMenu {
    private final ConfigurationSection clickableSection;
    private final Dump dump;
    public RMenu(@NonNull Player player, IConfig config) {
        super(player,
                Objects.requireNonNull(config.getString("title")),
                (byte) (config.getInt("rows") * 9),
                Objects.requireNonNull(config.getSection("items.decorations")));
        this.clickableSection = config.getSection("items.clickable");
        this.dump = Dump.create(player);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (e.isShiftClick() || (this.getDecoration().checkSlot((byte) e.getSlot()))
        && current != null && this.getDecoration().checkItemStack(current) || e.getClick() == ClickType.DOUBLE_CLICK) e.setCancelled(true);
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        if (e.getRawSlots().stream().anyMatch(s -> s < this.getInventory().getSize())) e.setCancelled(true);
    }
}
