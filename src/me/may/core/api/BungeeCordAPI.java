package me.may.core.api;

import me.may.core.util.BungeeCordUtils;

/**
 * 蹦极代理API
 *
 * @author May_Speed
 */
public class BungeeCordAPI {

    /**
     * 踢出一个玩家
     *
     * @param playerName 玩家名
     * @param message    信息
     */
    public static void kickPlayer(String playerName, String message) {
        BungeeCordUtils.kickPlayer(playerName, message);
    }

    /**
     * 将玩家传送至某子服
     *
     * @param playerName 玩家名
     * @param serverName 服务器名
     */
    public static void playerConnectServer(String playerName, String serverName) {
        BungeeCordUtils.playerConnectServer(playerName, serverName);
    }

    /**
     * 给所有服务器的玩家发送Title
     *
     * @param title    标题
     * @param subtitle 子标题
     */
    public static void sendTitleToAllServer(String title, String subtitle) {
        BungeeCordUtils.sendData("BukkitCoreMessage", title + "%ncyn%" + subtitle);
    }

}
