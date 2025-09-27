package org.satellite.progiple.saterocket.eco;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.util.utilities.LunaMath;
import org.satellite.progiple.saterocket.configs.Config;

public interface BuyingNomy {
    boolean enabled();
    void add(OfflinePlayer player, int amount);
    double get(OfflinePlayer player);

    static int getCost(boolean isVault, int storagedItems, int amount) {
        ConfigurationSection settings = Config.getSection("settings");
        int cost = (int) (settings.getInt(String.format("%s_cost", isVault ? "money" : "jewels"))
                * Math.pow(settings.getDouble("cost_multiplier"), storagedItems));
        cost = cost * amount;

        if (settings.getBoolean("beautifulCost") && cost >= 100) {
            String strNum = String.valueOf(cost);

            String firstNums = strNum.substring(0, 2);
            cost = LunaMath.toInt(firstNums + "0".repeat(strNum.length() - 2));
        }
        return cost;
    }
}
