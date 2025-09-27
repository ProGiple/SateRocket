package org.satellite.progiple.saterocket.holo;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.util.service.managers.ColorManager;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.configs.Config;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class HoloAPI {
    @Getter private final Map<String, Location> ids = new HashMap<>();
    @Getter private HoloIAPI interAPI;
    public void setup() {
        if (Utils.isPluginEnabled("DecentHolograms")) interAPI = new HoloIAPI() {
            @Getter @Setter
            private boolean enabled = Config.getBool("settings.hologram.enabled");

            @Override
            public void remove(String id) {
                if (this.enabled) DHAPI.removeHologram(id);
            }

            @Override
            public String create(Location location) {
                if (!this.enabled) return null;
                String id = "holo-rocket-" + Utils.getRKey((byte) 12);

                ConfigurationSection section = Config.getSection("settings.hologram");
                Hologram hologram = DHAPI.createHologram(id, location.clone().add(0.5, section.getDouble("height"), 0.5));

                section.getStringList("lines").forEach(l -> {
                    if (l.startsWith("Material.")) {
                        Material material = Material.getMaterial(l.replace("Material.", ""));
                        DHAPI.addHologramLine(hologram, material == null ? Material.STONE : material);
                    } else {
                        DHAPI.addHologramLine(hologram, ColorManager.color(l));
                    }
                });
                return id;
            }
        };
    }

    public void remove(String id) {
        if (interAPI != null) {
            interAPI.remove(id);
            ids.remove(id);
        }
    }

    public void create(Location location) {
        if (interAPI == null) return;

        String str = interAPI.create(location);
        if (str != null) ids.put(str, location);
    }
}
