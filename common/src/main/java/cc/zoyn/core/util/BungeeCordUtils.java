package cc.zoyn.core.util;

import cc.zoyn.core.Core;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;


/**
 * BungeeCord - 工具类
 *
 * @author Zoyn
 */
public final class BungeeCordUtils {

    private static volatile BungeeCordUtils instance;

    // Prevent accidental construction
    private BungeeCordUtils() {
    }

    public static BungeeCordUtils getInstance() {
        if (instance == null) {
            synchronized (BungeeCordUtils.class) {
                if (instance == null) {
                    instance = new BungeeCordUtils();
                }
            }
        }
        return instance;
    }

    public static void sendData(BungeeCordTagType tagType, String... arguments) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(tagType.getText());
        for (String argument : arguments) {
            out.writeUTF(argument);
        }

        Player player = Iterables.getFirst(BasicUtils.getOnlinePlayers(), null);
        Validate.notNull(player).sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
    }

    /**
     * 将玩家传送至某子服
     *
     * @param playerName 名字
     * @param serverName 服务器名
     */
    public static void playerConnectServer(String playerName, String serverName) {
        sendData(BungeeCordTagType.CONNECT_OTHER, playerName, serverName);
    }

    /**
     * 踢出一个玩家
     *
     * @param playerName 玩家名
     * @param message    信息
     */
    public static void kickPlayer(String playerName, String message) {
        sendData(BungeeCordTagType.KICK_PLAYER, playerName, message);
    }

    public static void sendMessageToPlayer(String playerName, String message) {
        sendData(BungeeCordTagType.MESSAGE, playerName, message);
    }


    @Getter
    public enum BungeeCordTagType {
        CONNECT("Connect"),
        CONNECT_OTHER("ConnectOther"),
        IP("IP"),
        PLAYER_COUNT("PlayerCount"),
        PLAYER_LIST("PlayerList"),
        GET_SERVERS("GetServers"),
        MESSAGE("Message"),
        KICK_PLAYER("KickPlayer");

        private String text;

        BungeeCordTagType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
