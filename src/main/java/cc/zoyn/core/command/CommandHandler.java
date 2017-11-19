package cc.zoyn.core.command;

import cc.zoyn.core.command.subcommand.InfoCommand;
import cc.zoyn.core.command.subcommand.ListCommand;
import cc.zoyn.core.util.SubCommand;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private Map<String, SubCommand> subCommandMap = Maps.newHashMap();

    public CommandHandler() {
        registerSubCommand("list", new ListCommand());
        registerSubCommand("info", new InfoCommand());
    }

    public void registerSubCommand(String commandName, SubCommand subCommand) {
        if (subCommandMap.containsKey(commandName)) {
            Bukkit.getLogger().warning("发现重复注册指令!");
        }
        subCommandMap.put(commandName, subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("core")) {
            if (args.length == 0) {
                sender.sendMessage("§6========== §3[ §eMayCore §l| §e核心 §3] §6==========");
                sender.sendMessage("§b/core list §7查看插件列表");
                sender.sendMessage("§b/core info §7查看服务器信息");
                return true;
            }
            SubCommand subCommand = subCommandMap.get(args[0]);
            if (subCommand == null) {
                sender.sendMessage("§8[§6核心§8] §e>> §c未知指令");
                return true;
            }
            subCommand.execute(sender, args);
        }
        return false;
    }
}
