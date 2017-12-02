package cc.zoyn.core.util;

import cc.zoyn.core.util.nms.NMSUtils;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class SkinUtils {

    // Prevent accidental construction
    private SkinUtils() {
    }

    public static void changeSkin(Player player, String skinName) {
        Object nmsPlayer = NMSUtils.getNMSPlayer(player);
        try {
            Object gameProfile = nmsPlayer.getClass().getMethod("getProfile").invoke(null);
            CraftPlayer cp = (CraftPlayer) player;
            EntityPlayer ep = cp.getHandle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

