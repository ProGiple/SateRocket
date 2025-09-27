package org.satellite.progiple.saterocket.configs;

import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import org.satellite.progiple.saterocket.SateRocket;
import org.satellite.progiple.saterocket.holo.HoloAPI;
import org.satellite.progiple.saterocket.holo.HoloIAPI;

import java.util.List;

@UtilityClass
public class Config {
    private final IConfig config;
    static {
        config = new IConfig(SateRocket.getINSTANCE());
    }

    public void reload() {
        config.reload(SateRocket.getINSTANCE());

        HoloIAPI iapi = HoloAPI.getInterAPI();
        if (iapi != null) iapi.setEnabled(Config.getBool("settings.hologram.enabled"));
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public double getDouble(String path) {
        return config.self().getDouble(path);
    }

    public ConfigurationSection getSection(String path) {
        return config.getSection(path);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public void sendMessage(CommandSender sender, String id, String... rpl) {
        config.sendMessage(sender, id, rpl);
    }

    public boolean getBool(String path) {
        return config.getBoolean(path);
    }

    public List<String> getList(String path) {
        return config.getStringList(path);
    }
}
