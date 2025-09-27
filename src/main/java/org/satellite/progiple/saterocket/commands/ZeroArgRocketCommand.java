package org.satellite.progiple.saterocket.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.novasparkle.lunaspring.API.commands.Invocation;
import org.novasparkle.lunaspring.API.commands.annotations.Check;
import org.novasparkle.lunaspring.API.commands.annotations.ZeroArgCommand;
import org.novasparkle.lunaspring.API.commands.processor.NoArgCommand;
import org.novasparkle.lunaspring.API.menus.MenuManager;
import org.satellite.progiple.saterocket.menus.realized.rocket.RocketMenu;

@ZeroArgCommand("saterocket")
@Check(permissions = "@.open", flags = NoArgCommand.AccessFlag.PLAYER_ONLY)
public class ZeroArgRocketCommand implements Invocation {
    @Override
    public void invoke(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;
        MenuManager.openInventory(new RocketMenu(player));
    }
}
