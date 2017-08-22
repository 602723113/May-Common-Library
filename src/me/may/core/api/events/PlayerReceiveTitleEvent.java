package me.may.core.api.events;

import me.may.core.dto.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 玩家接收Title事件
 *
 * @author May_Speed
 */
public class PlayerReceiveTitleEvent extends Event implements Cancellable {

    private Player player;
    private Title title;
    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveTitleEvent(Player player, Title title) {
        this.player = player;
        this.title = title;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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
