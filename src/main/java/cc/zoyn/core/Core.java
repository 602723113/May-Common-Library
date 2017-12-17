package cc.zoyn.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Class
 *
 * @author Zoyn
 */
public class Core extends JavaPlugin {

    private static Core instance;

    public void onEnable() {
        instance = this;

        // register channel
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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
