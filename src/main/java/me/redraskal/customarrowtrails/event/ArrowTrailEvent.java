package me.redraskal.customarrowtrails.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class ArrowTrailEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Player shooter;
    @Getter private final Arrow arrow;
    @Getter @Setter private Particle effect;
    @Getter @Setter private boolean cancelled;

    public ArrowTrailEvent(Player shooter, Arrow arrow, Particle effect) {
        this.shooter = shooter;
        this.arrow = arrow;
        this.effect = effect;
    }

    /**
     * Returns true if the Arrow has a pending effect applied.
     * @return
     */
    public boolean hasEffect() {
        return effect != null;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}