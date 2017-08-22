package me.may.core.api.events;

import me.may.core.dto.Tellraw;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 玩家接收Tellraw事件
 *
 * @author May_Speed
 */
public class PlayerReceiveTellrawEvent extends Event implements Cancellable {

    private Player player;
    private Tellraw tellraw;
    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveTellrawEvent(Player player, Tellraw tellraw) {
        this.player = player;
        this.tellraw = tellraw;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Tellraw getTellraw() {
        return tellraw;
    }

    public void setTellraw(Tellraw tellraw) {
        this.tellraw = tellraw;
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
