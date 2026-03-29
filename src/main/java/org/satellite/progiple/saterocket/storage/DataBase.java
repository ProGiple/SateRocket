package org.satellite.progiple.saterocket.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.novasparkle.lunaspring.API.database.AsyncExecutor;
import org.novasparkle.lunaspring.API.database.ResultSetHandler;
import org.novasparkle.lunaspring.API.util.exceptions.SerializerException;
import org.novasparkle.lunaspring.API.util.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends AsyncExecutor {
    private final String tableName;
    private final ResultSetHandler<String> resultSetHandler;
    public DataBase(ConfigurationSection section) {
        super(section);
        this.tableName = section.getString("tableName");

        this.resultSetHandler = rs -> rs.getString("RocketItems");
        this.createTable();
    }

    private void createTable() {
        this.executeAsync(String.format("CREATE TABLE IF NOT EXISTS %s (Player varchar(55), RocketItems MEDIUMTEXT);", this.tableName));
    }

    public @NotNull List<ItemStack> getItems(String uuid) {
        String data = this.executeQuery(String.format("SELECT RocketItems FROM %s WHERE player=?;", this.tableName), this.resultSetHandler, uuid)
                .stream()
                .findFirst()
                .orElse(null);
        if (data == null || data.isEmpty()) return new ArrayList<>();

        try {
            return Utils.Base64.deserializeList(ItemStack.class, data);
        } catch (SerializerException e) {
            return new ArrayList<>();
        }
    }

    public void setItems(String uuid, List<ItemStack> itemStacks) {
        List<String> dataList = this.executeQuery(String.format("SELECT RocketItems FROM %s WHERE Player=?;", this.tableName), this.resultSetHandler, uuid);
        String data = Utils.Base64.serializeList(itemStacks);
        if (dataList.isEmpty()) {
            this.executeSync(String.format("INSERT INTO %s (Player, RocketItems) VALUES (?, ?);", this.tableName), uuid, data);
        }
        else {
            this.executeSync(String.format("UPDATE %s SET RocketItems=? WHERE Player=?;", this.tableName), data, uuid);
        }
    }

    public void clear() {
        this.executeAsync(String.format("TRUNCATE TABLE %s;", this.tableName));
    }
}
