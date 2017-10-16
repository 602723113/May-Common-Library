package me.may.core.listener;

import me.may.core.Core;
import me.may.core.util.TabUtils;
import me.may.core.util.TitleUtils;
import me.may.level.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //设置在Tab中的显示
        int level = 0;
        if (me.may.level.Level.getInstance().hasLevelAccount(player)) {
            level = Level.getInstance().getPlayerLevel(player);
        }
        player.setPlayerListName(e.getPlayer().getName() + "§f[§a§l" + level + "§f级]");
        //设置Tab
        TabUtils.setTab(player, Core.getInstance().getConfig().getString("Tab.Head".replaceAll("&", "§")), Core.getInstance().getConfig().getString("Tab.Foot".replaceAll("&", "§")));
        //发送Title
        TitleUtils.sendTitle(player, Core.getInstance().getConfig().getInt("Title.FadeIn"), Core.getInstance().getConfig().getInt("Title.Stay"), Core.getInstance().getConfig().getInt("Title.FadeOut"), Core.getInstance().getConfig().getString("Title.Title".replaceAll("&", "§")), Core.getInstance().getConfig().getString("Title.SubTitle".replaceAll("&", "§")));
        //显示BossBar
//        Core.getInstance().showBossBar(player);
    }
}
