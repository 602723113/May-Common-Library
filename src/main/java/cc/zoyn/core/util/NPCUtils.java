package cc.zoyn.core.util;

import cc.zoyn.core.Core;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public final class NPCUtils {

    // Prevent accidental construction
    private NPCUtils() {
    }

    public static String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[]{texture, signature};
        } catch (IOException e) {
            System.err.println("无法从服务器读取皮肤信息!");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 增加npc
     *
     * @param name     NPC显示名
     * @param skinName 皮肤名
     * @param x        坐标X
     * @param y        坐标Y
     * @param z        坐标Z
     */
    public static void addNpc(String name, String skinName, double x, double y, double z) {
        String[] a = getFromName(skinName);
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        profile.getProperties().put("textures", new Property("textures", a[0], a[1]));
        EntityPlayer npc = new EntityPlayer(server, server.getWorldServer(0), profile, new PlayerInteractManager(server.getWorldServer(0)));
        npc.setPosition(x, y, z);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }
            }, 5);
        }
    }

    /**
     * 使用ProtocolLib制造NPC
     *
     * @param name     NPC名
     * @param skinName 皮肤名
     * @param x        坐标X
     * @param y        坐标Y
     * @param z        坐标Z
     */
    public static void addNPCUsePL(String name, String skinName, double x, double y, double z) {
        //获取皮肤信息
        String[] skin = getFromName(skinName);
        //获取Server的nms对象
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        //新建一个NPC的GameProfile
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        //设置皮肤
        profile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));

        //实例化NPC对象
        EntityPlayer npc = new EntityPlayer(server, server.getWorldServer(0), profile, new PlayerInteractManager(server.getWorldServer(0)));
        //设置坐标
        npc.setPosition(x, y, z);

        //获取PL管理工具
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        PacketContainer packet = pm.createPacket(PacketType.Play.Server.PLAYER_INFO);
        /*
         * PlayerInfo
		 * private EnumPlayerInfoAction a;
		 * private final List<PlayerInfoData> b = Lists.newArrayList();
		 */
        //依次写入相关数据
        packet.getPlayerInfoAction().write(0, PlayerInfoAction.ADD_PLAYER);
        List<PlayerInfoData> datas = Lists.newArrayList();
        datas.add(new PlayerInfoData(WrappedGameProfile.fromHandle(npc.getProfile()), npc.ping, NativeGameMode.CREATIVE, WrappedChatComponent.fromText(name)));
        packet.getPlayerInfoDataLists().write(0, datas);

        //已命名实体生成
        PacketContainer packet2 = pm.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        /*
		 * NamedEntitySpawn
		 * private int a;
		 * private UUID b;
		 * private double c;
		 * private double d;
		 * private double e;
		 * private byte f;
		 * private byte g;
		 * private DataWatcher h;
		 * private List<DataWatcher.Item<?>> i;
		 */
        packet2.getIntegers().write(0, npc.getId());
        packet2.getUUIDs().write(0, profile.getId());
        packet2.getDoubles().write(0, npc.locX);
        packet2.getDoubles().write(1, npc.locY);
        packet2.getDoubles().write(2, npc.locZ);
        packet2.getBytes().write(0, (byte) (int) (npc.yaw * 256.0F / 360.0F));
        packet2.getBytes().write(1, (byte) (int) (npc.pitch * 256.0F / 360.0F));
        packet2.getDataWatcherModifier().write(0, WrappedDataWatcher.getEntityWatcher(npc.getBukkitEntity()));

        PacketContainer packet3 = pm.createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet3.getPlayerInfoAction().write(0, PlayerInfoAction.REMOVE_PLAYER);
        packet3.getPlayerInfoDataLists().write(0, datas);

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                pm.sendServerPacket(player, packet);
                pm.sendServerPacket(player, packet2);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        pm.sendServerPacket(player, packet3);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }, 5);
        }
    }
}
