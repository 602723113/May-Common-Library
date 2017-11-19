package cc.zoyn.core.command.subcommand;

import cc.zoyn.core.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class InfoCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§b[核心] §e>> §c权限不足");
            return true;
        }
        sender.sendMessage("§6运行版本§c:§r " + Bukkit.getBukkitVersion() + " | " + Bukkit.getVersion());
        sender.sendMessage("§6当前在线§c:§r " + Bukkit.getOnlinePlayers().size());
        sender.sendMessage("§6最大人数§c:§r " + Bukkit.getMaxPlayers());
        sender.sendMessage("§6封禁玩家§c:§r " + Bukkit.getBannedPlayers().size());
        sender.sendMessage("§6封禁IP数§c:§r " + Bukkit.getIPBans().size());

        return false;
    }
}
