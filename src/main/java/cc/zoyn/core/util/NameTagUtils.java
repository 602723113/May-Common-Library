package cc.zoyn.core.util;

import cc.zoyn.core.util.nms.NMSUtils;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NameTagUtils {

    // Prevent accidental construction
    private NameTagUtils() {
    }

    public static void changeName(String name, Player player) {
        try {
            Object entityPlayer = NMSUtils.getNMSPlayer(player);
            Class<?> entityHuman = entityPlayer.getClass().getSuperclass();
            Field gameProfileField;
            int majVersion = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]
                    .replaceAll("(v|R[0-9]+)", "").split("_")[0]);
            int minVersion = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]
                    .replaceAll("(v|R[0-9]+)", "").split("_")[1]);
            // 判断字段是否在1.9之后
            if (majVersion >= 1 && minVersion >= 9) {
                gameProfileField = entityHuman.getDeclaredField("bS");
            } else {
                gameProfileField = entityHuman.getDeclaredField("bH");
            }
            gameProfileField.setAccessible(true);
            gameProfileField.set(entityPlayer, new GameProfile(player.getUniqueId(), name));
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
                players.showPlayer(player);
            }
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }
}
