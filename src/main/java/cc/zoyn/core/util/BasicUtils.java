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
public class BasicUtils {

    /**
     * 取服务器版本
     *
     * @return 服务器版本
     */
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    /**
     * 给一个玩家发送json数据
     *
     * @param player 玩家
     * @param msg    JSON数据
     */
    public static void sendJson(Player player, String msg) {
        String version = getServerVersion();
        try {
            Object icbc = NMSUtils
                    .getNMSClass(version.equalsIgnoreCase("v1_8_R1") ? "ChatSerializer"
                            : "IChatBaseComponent$ChatSerializer")
                    .getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{msg});
            Object ppoc = NMSUtils.getNMSClass("PacketPlayOutChat")
                    .getConstructor(new Class[]{NMSUtils.getNMSClass("IChatBaseComponent"), Byte.TYPE})
                    .newInstance(icbc, Byte.valueOf("1"));
            NMSUtils.sendPacket(player, ppoc);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }

    /**
     * 转换Unicode
     *
     * @param ori 字符
     * @return 转换后的字符串
     */
    public static String convertUnicode(String ori) {
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        int x = 0;
        while (true) {
            while (true) {
                while (x < len) {
                    char aChar = ori.charAt(x++);
                    if (aChar == 92) {
                        aChar = ori.charAt(x++);
                        if (aChar == 117) {
                            int value = 0;
                            for (int i = 0; i < 4; ++i) {
                                aChar = ori.charAt(x++);
                                switch (aChar) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                        value = (value << 4) + aChar - 48;
                                        break;
                                    case ':':
                                    case ';':
                                    case '<':
                                    case '=':
                                    case '>':
                                    case '?':
                                    case '@':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '[':
                                    case '\\':
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                    default:
                                        throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        value = (value << 4) + 10 + aChar - 65;
                                        break;
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        value = (value << 4) + 10 + aChar - 97;
                                }
                            }
                            outBuffer.append((char) value);
                        } else {
                            if (aChar == 116) {
                                aChar = 9;
                            } else if (aChar == 114) {
                                aChar = 13;
                            } else if (aChar == 110) {
                                aChar = 10;
                            } else if (aChar == 102) {
                                aChar = 12;
                            }
                            outBuffer.append(aChar);
                        }
                    } else {
                        outBuffer.append(aChar);
                    }
                }
                return outBuffer.toString();
            }
        }
    }

    /**
     * 取服务器在线玩家
     *
     * @return 玩家集合
     */
    public static List<Player> getOnlinePlayers() {
        // 实例化两个List用于存放Player和World
        List<Player> players = Lists.newArrayList();
        List<World> worlds = Lists.newArrayList();
        worlds.addAll(Bukkit.getWorlds());
        // 遍历所有的世界
        for (int i = 0; i < worlds.size(); i++) {
            // 如果第i个世界的玩家是空的则进行下一次循环
            if (worlds.get(i).getPlayers().isEmpty()) {
                continue;
            } else {
                // 不是空的则添加到players集合中
                players.addAll(worlds.get(i).getPlayers());
            }
        }
        return players;
    }

    /**
     * 将名字转换为UUID
     *
     * @param name 名字
     * @return 该名的UUID对象
     */
    public UUID translateNameToUUID(String name) {
        UUID uuid = null;
        uuid = Bukkit.getPlayer(name).getUniqueId();
        return uuid;
    }
}
