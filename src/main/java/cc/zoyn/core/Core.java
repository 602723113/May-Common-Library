package cc.zoyn.core;

import cc.zoyn.core.command.CommandHandler;
import cc.zoyn.core.listener.TestListener;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Core extends JavaPlugin {

    private static Core instance;
    private ProtocolManager pm;
    public static Map<String, BlockPosition> map = Maps.newHashMap();

    public void onEnable() {
        instance = this;

        Bukkit.getPluginCommand("core").setExecutor(new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new TestListener(), this);

        saveDefaultConfig();
        pm = ProtocolLibrary.getProtocolManager();

        pm.addPacketListener(
                new PacketAdapter(this, PacketType.Play.Client.UPDATE_SIGN) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        System.out.println("test");
                        PacketContainer packet = event.getPacket();
                        Player player = event.getPlayer();

                        BlockPosition cacheBlockPosition = map.get(player.getName()); //缓存中的牌子位置数据
                        BlockPosition packetBlockPosition = packet.getBlockPositionModifier().getValues().get(0); //数据包中的牌子位置数据

                        System.out.println(packet.getModifier().getValues().toString());

                        System.out.println(cacheBlockPosition);
                        System.out.println(packetBlockPosition);

                        //NPE检查
                        if (cacheBlockPosition == null || packetBlockPosition == null) {
                            System.out.println("null?");
                            return;
                        }

                        if (cacheBlockPosition.equals(packetBlockPosition)) {
                            System.out.println("123");
                        }
                    }
                }
        );
    }

    /**
     * 以List的方式取服务器插件
     *
     * @return 服务器所有插件的集合
     */
    public List<Plugin> getPlugins() {
        return Arrays.asList(Bukkit.getPluginManager().getPlugins());
    }

    /**
     * 取全部世界的名字
     *
     * @return 全部世界名
     */
    public List<String> getWorldsName() {
        List<String> names = Lists.newArrayList();
        for (World world : Bukkit.getWorlds()) {
            names.add(world.getName());
        }
        return names;
    }

    /**
     * 取Core实例
     *
     * @return Core的实例
     */
    public static Core getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return pm;
    }

}
