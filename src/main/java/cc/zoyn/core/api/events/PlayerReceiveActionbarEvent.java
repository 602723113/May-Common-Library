package cc.zoyn.core.api.events;

import cc.zoyn.core.dto.Actionbar;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 玩家接收ActionBar事件
 *
 * @author Zoyn
 */
public class PlayerReceiveActionbarEvent extends Event implements Cancellable {

    private Player player;
    private Actionbar actionBar;
    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveActionbarEvent(Player player, Actionbar actionBar) {
        this.player = player;
        this.actionBar = actionBar;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Actionbar getActionbar() {
        return actionBar;
    }

    public void setActionbar(Actionbar actionBar) {
        this.actionBar = actionBar;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean cancelled = false;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
