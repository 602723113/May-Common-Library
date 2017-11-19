package cc.zoyn.core.service;

import cc.zoyn.core.Core;
import cc.zoyn.core.api.events.PlayerReceiveActionbarEvent;
import cc.zoyn.core.api.events.PlayerReceiveTellrawEvent;
import cc.zoyn.core.api.events.PlayerReceiveTitleEvent;
import cc.zoyn.core.dto.Actionbar;
import cc.zoyn.core.dto.Sign;
import cc.zoyn.core.dto.Tellraw;
import cc.zoyn.core.dto.Title;
import cc.zoyn.core.enchantments.Glow;
import cc.zoyn.core.util.*;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;

/**
 * Core - 接口实现
 *
 * @author Zoyn
 */
public class CoreServiceImpl implements CoreService {

    @Override
    public void sendTellraw(Tellraw tellraw, Player player) {
        if (!player.isOnline()) {
            throw new NullPointerException("错误: 玩家不在线!");
        }
        if (tellraw == null) {
            throw new NullPointerException("错误: Tellraw不能为Null!");
        }
        Tellraw tellRaw = tellraw;
        PlayerReceiveTellrawEvent event = new PlayerReceiveTellrawEvent(player, tellRaw);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        if (Bukkit.getVersion().contains("Paper") && !Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(
                    Core.getInstance(),
                    () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + tellraw.toJsonString())
            );

        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + tellRaw.toJsonString());
        }
    }

    @Override
    public Tellraw createTellraw(String message) {
        return new Tellraw(message.replaceAll("&", "§"));
    }

    @Override
    public void setItemUnbreakable(ItemStack is, boolean unbreakable) {
        if (is == null) {
            throw new NullPointerException("错误: 物品不能为Null!");
        }
        NBTUtils.setUnbreakable(is, unbreakable);
    }

    @Override
    public ItemStack setItemGlow(ItemStack is) {
        if (is == null) {
            throw new NullPointerException("错误: 物品不能为Null!");
        }
        ItemMeta im = is.getItemMeta();
        im.addEnchant(new Glow(255), 1, true);
        is.setItemMeta(im);
        im = null;
        return is;
    }

    @Override
    public void setPlayerTab(Player player, String head, String foot) {
        if (!player.isOnline()) {
            throw new NullPointerException("错误: 玩家不在线!");
        }
        TabUtils.setTab(player, head.replaceAll("&", "§"), foot.replaceAll("&", "§"));
    }

    @Override
    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        if (!player.isOnline()) {
            throw new NullPointerException("错误: 玩家不在线!");
        }
        Title t = new Title(fadeIn, stay, fadeOut, title, subtitle);
        PlayerReceiveTitleEvent event = new PlayerReceiveTitleEvent(player, t);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        TitleUtils.sendTitle(player, t.getFadeIn(), t.getStay(), t.getFadeOut(), t.getTitle(), t.getSubtitle());
    }

    @Override
    public void sendActionBar(Player player, String msg) {
        if (!player.isOnline()) {
            throw new NullPointerException("错误: 玩家不在线!");
        }
        Actionbar action = new Actionbar(msg.replaceAll("&", "§"));
        PlayerReceiveActionbarEvent event = new PlayerReceiveActionbarEvent(player, action);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        ActionBarUtils.sendBar(player, action.getMessage());
    }

    @Override
    public Entity[] getNearbyEntitiesArrays(Location loc, double radius) {
        return EntityUtils.getNearbyEntitiesArrays(loc, radius);
    }

    @Override
    public void sendSign(Player player, Sign sign) {
        if (!player.isOnline()) {
            throw new NullPointerException("错误: 玩家不在线!");
        }
        if (sign == null) {
            throw new NullPointerException("错误: Sign不能为Null!");
        }
        if (sign.isEmpty()) {
            throw new IllegalArgumentException("错误: Sign不能无内容!");
        }
        String[] lines = sign.getTexts();
        //获取Pl管理
        ProtocolManager pm = Core.getInstance().getProtocolManager();

		/*
         * private BlockPosition a; ---> 方块位置
		 * public IBlockData block; ---> 方块数据
		 */
        BlockPosition blockPosition = new BlockPosition(player.getLocation().toVector());

        PacketContainer blockChange = pm.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
        blockChange.getBlockPositionModifier().write(0, blockPosition);
        blockChange.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));

        try {
            pm.sendServerPacket(player, blockChange);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

        PacketContainer updateSign = pm.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);
        updateSign.getBlockPositionModifier().write(0, blockPosition);
        updateSign.getIntegers().write(0, 9);
        NbtCompound compound = NbtFactory.ofCompound("Sign");
        for (int i = 0; i < lines.length; i++) {
            compound.put("Text" + (i + 1), "{\"text\":\"" + lines[i] + "\"}");
        }
        updateSign.getNbtModifier().write(0, compound);

        try {
            pm.sendServerPacket(player, updateSign);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

		/*
		 * 1.8以下为OPEN_SIGN_ENTITY
		 * OpenSignEditor内部字段
		 * private BlockPosition a; ---> 方块位置
		 */
        PacketContainer open = pm.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        open.getBlockPositionModifier().write(0, blockPosition);

        try {
            pm.sendServerPacket(player, open);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return;
        }
        Core.map.put(player.getName(), blockPosition); //丢入缓冲区
    }
}
