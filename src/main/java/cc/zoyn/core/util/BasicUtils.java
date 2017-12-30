package cc.zoyn.core.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * 基础 - 工具类
 *
 * @author Zoyn
 * @since 2016/12/26
 */
public final class BasicUtils {

    // Prevent accidental construction
    private BasicUtils() {
    }

//    /**
//     * 给一个玩家发送json数据
//     *
//     * @param player 玩家
//     * @param msg    JSON数据
//     */
//    public static void sendJson(Player player, String msg) {
//        String version = NMSUtils.getVersion();
//        try {
//            Object icbc = NMSUtils
//                    .getNMSClass(version.equalsIgnoreCase("v1_8_R1") ? "ChatSerializer"
//                            : "IChatBaseComponent$ChatSerializer")
//                    .getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{msg});
//            Object ppoc = NMSUtils.getNMSClass("PacketPlayOutChat")
//                    .getConstructor(new Class[]{NMSUtils.getNMSClass("IChatBaseComponent"), Byte.TYPE})
//                    .newInstance(icbc, Byte.valueOf("1"));
//            NMSUtils.sendPacket(player, ppoc);
//        } catch (Exception e) {
//            System.out.println("错误: " + e.getMessage());
//        }
//    }

    /**
     * get server all online players
     * @return
     */
    public static List<Player> getOnlinePlayers() {
        // 实例化两个List用于存放Player和World
        List<Player> players = Lists.newArrayList();
        List<World> worlds = Lists.newArrayList();
        worlds.addAll(Bukkit.getWorlds());
        // 遍历所有的世界
        for (int i = 0; i < worlds.size(); i++) {
            // 如果第i个世界的玩家是空的则进行下一次循环
            if (!worlds.get(i).getPlayers().isEmpty()) {
                // 不是空的则添加到players集合中
                players.addAll(worlds.get(i).getPlayers());
            }
        }
        return players;
    }

    /**
     * 将玩家名转换为UUID
     * <br />
     * convert player name to UUID
     *
     * @param name the player name
     * @return the name of the corresponding uuid
     */
    public UUID translateNameToUUID(String name) {
        final Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            return player.getUniqueId();
        } else {
            return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
        }
    }
}
