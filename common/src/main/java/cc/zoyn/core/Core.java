package cc.zoyn.core;

import cc.zoyn.core.event.PlayerOpenBackpackEvent;
import cc.zoyn.core.test.AsyncPlayerChatListener;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Class
 *
 * @author Zoyn
 */
public class Core extends JavaPlugin {

    private static Core instance;

    @Override
    public void onEnable() {
        instance = this;

        // register channel
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // register packet listener
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Client.CLIENT_COMMAND) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (event.getPacket().getClientCommands().read(0) == EnumWrappers.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                    // call event
                    PlayerOpenBackpackEvent openBackpackEvent = new PlayerOpenBackpackEvent(player);
                    Bukkit.getPluginManager().callEvent(openBackpackEvent);
                    event.setCancelled(openBackpackEvent.isCancelled());
                }
            }
        });

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
    }

    /**
     * get Core instance
     *
     * @return {@link Core}
     */
    public static Core getInstance() {
        return instance;
    }


}
