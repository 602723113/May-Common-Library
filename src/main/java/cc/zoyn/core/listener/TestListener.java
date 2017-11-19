package cc.zoyn.core.listener;


import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.common.collect.Lists;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

/**
 * 测试用Listener
 *
 * @author Zoyn
 * @since 2017-10-03
 */
public class TestListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        List<NbtBase<String>> list = Lists.newArrayList();
        list.add(NbtFactory.of("name", "null"));
        list.add(NbtFactory.of("Value", "xxx"));

        NbtCompound value = NbtFactory.ofCompound("value");
        NbtCompound array = NbtFactory.ofCompound("value");
        array.put("name", "null");
        array.put("Value", "xxx");
        value.put(array);

        System.out.println(value.getKeys().toString());
        System.out.println(value.toString());
        
    }
}
