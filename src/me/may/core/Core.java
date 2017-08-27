package me.may.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.may.core.advancement.BackgroundEnum;
import me.may.core.advancement.FrameEnum;
import me.may.core.advancement.TriggerEnum;
import me.may.core.builder.*;
import me.may.core.command.CommandHandler;
import me.may.core.listener.PlayerJoinListener;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Core extends JavaPlugin {

    private static Core instance;
    private ProtocolManager pm;
    private BossBar bar;
    public static Map<String, BlockPosition> map = Maps.newHashMap();

    @SuppressWarnings("deprecation")
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[MayCore] " + ChatColor.GRAY + "已加载");
        Bukkit.getPluginCommand("core").setExecutor(new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        saveDefaultConfig();
        // Bar设置
        bar = Bukkit.getServer().createBossBar(getConfig().getString("BossBar").replaceAll("&", "§"), BarColor.YELLOW,
                BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        instance = this;
        pm = ProtocolLibrary.getProtocolManager();

        ItemStack item = new ItemStack(Material.APPLE);
        JsonObject itemJSON = new JsonObject();
        itemJSON.addProperty("item", "minecraft:" + item.getType().name().toLowerCase());
        itemJSON.addProperty("amount", item.getAmount());
        itemJSON.addProperty("data", item.getData().getData());

        AdvancementBuilder builder = new AdvancementBuilder()
                .setAdvancementId(new NamespacedKey(this, "may/root"))
                .setTitle("Test1")
                .setDescription("我是父进度")
                .setHide(false)
                .setIcon(Material.SIGN)
                .setBackground(BackgroundEnum.STONE)
                .setFrame(FrameEnum.TASK)
                .setCriteria(
                        new CriteriaBuilder("example", TriggerEnum.CONSUME_ITEM)
                                .addCondition(new ConditionBuilder("item", itemJSON))
                );
        builder.add();

        ItemStack item2 = new ItemStack(Material.COBBLESTONE);
        JsonObject itemJSON2 = new JsonObject();
        itemJSON2.addProperty("item", "minecraft:" + item2.getType().name().toLowerCase());

        JsonArray array = new JsonArray();
        array.add(itemJSON2);

        TextComponent title = new TextComponent("emmm");
        title.setBold(true);
        title.setColor(net.md_5.bungee.api.ChatColor.AQUA);

        TextComponent description = new TextComponent("你获得了emmmm之力");
        description.setBold(true);
        AdvancementBuilder builder2 = new AdvancementBuilder()
                .setAdvancementId(new NamespacedKey(this, "may/test"))
                .setTitle(title)
                .setDescription(description)
                .setIcon("minecraft:wooden_pickaxe")
                .setFrame(FrameEnum.GOAL)
                .setCriteria(
                        new CriteriaBuilder("get_stone", TriggerEnum.INVENTORY_CHANGED)
                                .addCondition(new ConditionBuilder("items", array))
                )
                .setParent(builder.getAdvancementId().toString());
        builder2.add();
        builder2.saveToWorldData(Bukkit.getWorld("world"));

        pm.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.UPDATE_SIGN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                System.out.println(event.getPacket().getModifier().getValues().toString());
            }
        });

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
     * 给一名玩家显示BossBar
     *
     * @param player 玩家
     */
    public void showBossBar(Player player) {
        bar.removePlayer(player);
        bar.addPlayer(player);
    }

    /**
     * 取全部世界的名字
     *
     * @return 全部世界名
     */
    public List<String> getWorldsName() {
        List<String> names = new ArrayList<String>();
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
