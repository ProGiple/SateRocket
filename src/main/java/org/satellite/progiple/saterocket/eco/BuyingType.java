package org.satellite.progiple.saterocket.eco;

import lombok.Getter;
import org.satellite.progiple.saterocket.eco.realized.Jewels;
import org.satellite.progiple.saterocket.eco.realized.Vault;

@Getter
public enum BuyingType {
    ALL(EcoType.JEWELS, EcoType.VAULT),
    ALL_REVERSE(EcoType.VAULT, EcoType.JEWELS),
    ONLY_MONEY(EcoType.VAULT),
    ONLY_JEWELS(EcoType.JEWELS);

    private final EcoType right;
    private final EcoType left;
    BuyingType(EcoType right, EcoType left) {
        this.right = right;
        this.left = left;
    }

    BuyingType(EcoType ecoType) {
        this(ecoType, ecoType);
    }

    public BuyingNomy getLeft() {
        return this.left == EcoType.JEWELS ? Jewels.getEconomy() : Vault.getEconomy();
    }

    public BuyingNomy getRight() {
        return this.right == EcoType.JEWELS ? Jewels.getEconomy() : Vault.getEconomy();
    }

    private enum EcoType {
        VAULT,
        JEWELS
    }
}
