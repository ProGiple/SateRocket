package org.satellite.progiple.saterocket.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.commands.Invocation;
import org.novasparkle.lunaspring.API.commands.annotations.Check;
import org.novasparkle.lunaspring.API.commands.annotations.SubCommand;
import org.novasparkle.lunaspring.API.commands.processor.NoArgCommand;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.satellite.progiple.saterocket.configs.Config;

@SubCommand(appliedCommand = "saterocket", commandIdentifiers = "give")
@Check(permissions = "@.give", flags = NoArgCommand.AccessFlag.PLAYER_ONLY)
public class GiveSubCommand implements Invocation {
    @Override
    public void invoke(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        NonMenuItem item = new NonMenuItem(Config.getSection("settings.setter_item"));
        ItemStack stack = item.getItemStack();

        NBTManager.setBool(stack, "is_setter_block", true);
        player.getInventory().addItem(stack);
    }
}
