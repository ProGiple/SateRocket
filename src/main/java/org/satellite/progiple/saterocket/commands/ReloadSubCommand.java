package org.satellite.progiple.saterocket.commands;

import org.bukkit.command.CommandSender;
import org.novasparkle.lunaspring.API.commands.Invocation;
import org.novasparkle.lunaspring.API.commands.annotations.Permissions;
import org.novasparkle.lunaspring.API.commands.annotations.SubCommand;
import org.satellite.progiple.saterocket.configs.Config;

@SubCommand(appliedCommand = "saterocket", commandIdentifiers = "reload")
@Permissions("@.reload")
public class ReloadSubCommand implements Invocation {
    @Override
    public void invoke(CommandSender sender, String[] strings) {
        Config.reload();
        Config.sendMessage(sender, "reload");
    }
}
