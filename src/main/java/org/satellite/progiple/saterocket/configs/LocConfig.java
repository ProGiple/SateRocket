package org.satellite.progiple.saterocket.configs;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.novasparkle.lunaspring.API.configuration.Configuration;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.SateRocket;
import org.satellite.progiple.saterocket.holo.HoloAPI;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class LocConfig {
    private final Configuration config;
    static {
        config = new Configuration(new File(SateRocket.getINSTANCE().getDataFolder(), "locations.yml"));
    }

    public Set<Location> getLocations() {
        return config.getSection("locations")
                .getKeys(false)
                .stream()
                .map(k -> config.getLocation("locations." + k))
                .collect(Collectors.toSet());
    }

    public void add(Location location) {
        config.setLocation(String.format("locations.%s", Utils.getRKey((byte) 12)), location, true, false);
        HoloAPI.create(location);
    }

    public void remove(Location location) {
        new HashSet<>(config.getSection("locations").getKeys(false)).forEach(k -> {
                    if (location.equals(config.getLocation("locations." + k))) config.set("locations." + k, null);
                });
        new HashSet<>(HoloAPI.getIds().keySet()).forEach(i -> {
            if (location.equals(HoloAPI.getIds().get(i))) HoloAPI.remove(i);
        });
    }

    public void save() {
        config.save();
    }
}
