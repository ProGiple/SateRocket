package org.satellite.progiple.saterocket.eco.realized;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.OfflinePlayer;
import org.novasparkle.lunaspring.API.util.service.managers.VaultManager;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import org.satellite.progiple.saterocket.eco.BuyingNomy;

@UtilityClass
public class Vault {
    @Getter private BuyingNomy economy;
    public void setup() {
        if (!Utils.isPluginEnabled("Vault")) return;

        economy = new BuyingNomy() {
            @Override
            public boolean enabled() {
                return true;
            }

            @Override
            public void add(OfflinePlayer player, int amount) {
                if (amount > 0) VaultManager.deposit(player, amount);
                else if (amount < 0) VaultManager.withdraw(player, -amount);
            }

            @Override
            public double get(OfflinePlayer player) {
                return VaultManager.getBalance(player);
            }
        };
    }
}
