package org.satellite.progiple.saterocket.eco.realized;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.OfflinePlayer;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.satejewels.SateJewels;
import org.satellite.progiple.saterocket.eco.BuyingNomy;

@UtilityClass
public class Jewels {
    @Getter private BuyingNomy economy;
    public void setup() {
        if (Utils.isPluginEnabled("SateJewels")) economy = new BuyingNomy() {
            @Override
            public boolean enabled() {
                return true;
            }

            @Override
            public void add(OfflinePlayer player, int amount) {
                SateJewels.getINSTANCE().getAPI().giveJewels(player, amount);
            }

            @Override
            public double get(OfflinePlayer player) {
                return SateJewels.getINSTANCE().getAPI().getJewels(player);
            }
        };
    }
}
