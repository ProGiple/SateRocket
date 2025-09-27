package org.satellite.progiple.saterocket.menus.realized.rocket.buttons;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized.ForSendSlotButton;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized.SendButton;
import org.satellite.progiple.saterocket.menus.realized.rocket.buttons.realized.ToContainerButton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class RocketButtonSetter {
    private final Set<RocketButton> items = new HashSet<>();
    public RocketButtonSetter(ConfigurationSection section) {
        for (String id : List.of("SEND_SLOT", "SEND", "TO_STORAGE")) {
            ConfigurationSection itemSection = section.getConfigurationSection(id);
            if (itemSection == null) continue;

            if (itemSection.getKeys(false).contains("slots")) {
                for (int slot : Utils.getSlotList(itemSection.getStringList("slots"))) {
                    this.registerItem(itemSection, (byte) slot);
                }
                continue;
            }

            this.registerItem(itemSection, (byte) itemSection.getInt("slot"));
        }
    }

    private void registerItem(ConfigurationSection section, byte slot) {
        RocketButton item = switch (section.getName()) {
            case "SEND_SLOT" -> new ForSendSlotButton(section, slot);
            case "SEND" -> new SendButton(section, slot);
            case "TO_STORAGE" -> new ToContainerButton(section, slot);
            default -> null;
        };

        if (item != null) this.items.add(item);
    }
}
