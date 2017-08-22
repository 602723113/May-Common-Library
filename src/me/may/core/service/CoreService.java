package me.may.core.service;

import me.may.core.dto.Sign;
import me.may.core.dto.Tellraw;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Core - 应用层接口
 *
 * @author May_Speed
 */
public interface CoreService {

    void sendTellraw(Tellraw tellraw, Player player);

    Tellraw createTellraw(String message);

    void setItemUnbreakable(ItemStack is, boolean unbreakable);

    ItemStack setItemGlow(ItemStack is);

    void setPlayerTab(Player player, String head, String foot);

    void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle);

    void sendActionBar(Player player, String msg);

    void sendSign(Player player, Sign sign);

    Entity[] getNearbyEntitiesArrays(Location loc, double radius);
}
