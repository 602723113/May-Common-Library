package me.may.core.command.subcommand;

import me.may.core.Core;
import me.may.core.util.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ListCommand implements SubCommand {

    private List<Plugin> plugins = null;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (plugins == null) {
            plugins = Core.getInstance().getPlugins();
        }
        sender.sendMessage("§6========== §3[ §aPlugins §6◤ §f" + plugins.size() + " §6◢ §3] §6==========");
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).isEnabled()) {
                String prefix = plugins.get(i).getDescription().getPrefix();
                if (prefix == null) {
                    sender.sendMessage("§6" + plugins.get(i).getName() + " §7- §a✔ 已加载");
                } else {
                    sender.sendMessage("§6" + plugins.get(i).getName() + "§7(" + prefix + "§7)" + " §7- §a✔ 已加载");
                }
            } else {
                String prefix = plugins.get(i).getDescription().getPrefix();
                if (prefix == null) {
                    sender.sendMessage("§6" + plugins.get(i).getName() + " §7- §c✘ 未加载");
                } else {
                    sender.sendMessage("§6" + plugins.get(i).getName() + "§7(" + prefix + "§7)" + " §7- §c✘ 未加载");
                }
            }
        }
        return true;
    }
}
