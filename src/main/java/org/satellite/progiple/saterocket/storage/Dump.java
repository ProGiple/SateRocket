package org.satellite.progiple.saterocket.storage;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.util.service.realized.Cache;
import org.satellite.progiple.saterocket.SateRocket;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Dump {
    private static final Cache<Player, Dump> dumpCache = new Cache<>(20, TimeUnit.SECONDS, 225);

    public final List<ItemStack> itemStacks;
    private final DataBase storage;
    @Getter private final Player player;
    private Dump(Player player) {
        this.storage = SateRocket.getINSTANCE().getDataBase();
        this.itemStacks = storage.getItems(player.getName());
        this.player = player;
    }

    public void putItems() {
        this.storage.setItems(this.player.getName(), this.itemStacks);
    }

    public static Dump create(Player player) {
        Dump dump = dumpCache.getIfPresent(player);
        if (dump == null) {
            dump = new Dump(player);
            dumpCache.put(player, dump);
        }
        return dump;
    }
}
