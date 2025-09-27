package org.satellite.progiple.saterocket;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.commands.CommandInitializer;
import org.novasparkle.lunaspring.LunaPlugin;
import org.satellite.progiple.saterocket.configs.Config;
import org.satellite.progiple.saterocket.configs.LocConfig;
import org.satellite.progiple.saterocket.eco.realized.Jewels;
import org.satellite.progiple.saterocket.eco.realized.Vault;
import org.satellite.progiple.saterocket.holo.HoloAPI;
import org.satellite.progiple.saterocket.listeners.OnBreakHandler;
import org.satellite.progiple.saterocket.listeners.OnInteractHandler;
import org.satellite.progiple.saterocket.listeners.OnPlaceHandler;
import org.satellite.progiple.saterocket.storage.DataBase;

@Getter
public final class SateRocket extends LunaPlugin {
    @Getter private static SateRocket INSTANCE;
    private DataBase dataBase;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;

        saveDefaultConfig();
        this.loadFiles("locations.yml", "menus/rocket.yml", "menus/storage.yml", "menus/confirm.yml");

        this.registerListeners(new OnBreakHandler(), new OnInteractHandler(), new OnPlaceHandler());
        CommandInitializer.initialize(this, "#.commands");

        ConfigurationSection settings = Config.getSection("settings.storage");
        this.dataBase = new DataBase(settings);

        Vault.setup();
        Jewels.setup();
        HoloAPI.setup();

        LocConfig.getLocations().forEach(HoloAPI::create);
    }
}
