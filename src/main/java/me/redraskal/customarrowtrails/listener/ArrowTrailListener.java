package me.redraskal.customarrowtrails.listener;

import lombok.Getter;
import me.redraskal.customarrowtrails.runnable.ArrowTrailRunnable;
import me.redraskal.customarrowtrails.CustomArrowTrails;
import me.redraskal.customarrowtrails.event.ArrowTrailEvent;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class ArrowTrailListener implements Listener {

    @Getter private final CustomArrowTrails customArrowTrails;

    public ArrowTrailListener(CustomArrowTrails customArrowTrails) {
        this.customArrowTrails = customArrowTrails;
    }

    @EventHandler
    public void onArrowLaunch(ProjectileLaunchEvent event) {
        if(!(event.getEntity() instanceof Arrow)) return;
        if(event.getEntity().getShooter() == null
                || !(event.getEntity().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getEntity().getShooter();
        Effect effect = this.getCustomArrowTrails().getArrowTrailManager().getArrowTrail(shooter.getUniqueId());
        Arrow arrow = (Arrow) event.getEntity();

        if(this.getCustomArrowTrails().getConfig().getBoolean("remove-default-trail")) {
            arrow.setCritical(false);
        }

        ArrowTrailEvent arrowTrailEvent = new ArrowTrailEvent(shooter, arrow, effect);
        this.getCustomArrowTrails().getServer().getPluginManager().callEvent(arrowTrailEvent);

        if(!arrowTrailEvent.isCancelled() && arrowTrailEvent.getEffect() != null) {
            arrow.setCritical(false);
            new ArrowTrailRunnable(this.getCustomArrowTrails(), arrow, arrowTrailEvent.getEffect());
        }
    }
}