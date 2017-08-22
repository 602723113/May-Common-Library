package me.may.core.util;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    boolean execute(CommandSender sender, String[] args);

}
