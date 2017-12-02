package cc.zoyn.core.util;

import cc.zoyn.core.Core;
import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * BungeeCord - 工具类
 *
 * @author Zoyn
 */
public final class BungeeCordUtils implements PluginMessageListener {

    // Prevent accidental construction
    private BungeeCordUtils() {
    }

    /**
     * 将玩家传送至某子服
     *
     * @param name       名字
     * @param serverName 服务器名
     */
    public static void playerConnectServer(String name, String serverName) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        try {
            dataOut.writeUTF("Connect");
            dataOut.writeUTF(name);
            dataOut.writeUTF(serverName);
        } catch (IOException e) {
            System.out.println("错误: " + e.getMessage());
        }
        if (Bukkit.getOnlinePlayers() != null) {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(Core.getInstance(), "BungeeCord", byteOut.toByteArray());
        }
    }

    /**
     * 踢出一个玩家
     *
     * @param name    玩家名
     * @param message 信息
     */
    public static void kickPlayer(String name, String message) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        try {
            dataOut.writeUTF("KickPlayer");
            dataOut.writeUTF(name);
            dataOut.writeUTF(message);
        } catch (IOException e) {
            System.out.println("错误: " + e.getMessage());
        }
        if (Bukkit.getOnlinePlayers() != null) {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(Core.getInstance(), "BungeeCord", byteOut.toByteArray());
        }
    }

    public static void sendData(String tag, String message) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        try {
            dataOut.writeUTF(tag);
            dataOut.writeUTF(message);
        } catch (IOException e) {
            System.out.println("错误: " + e.getMessage());
        }
        if (Bukkit.getOnlinePlayers() != null) {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(Core.getInstance(), "BukkitCoreMessage", byteOut.toByteArray());
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BukkitCoreMessage")) {
            return;
        }
    }

}
