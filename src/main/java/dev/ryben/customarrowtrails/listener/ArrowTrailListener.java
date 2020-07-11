package dev.ryben.customarrowtrails.listener;

import lombok.Getter;
import dev.ryben.customarrowtrails.runnable.ArrowTrailRunnable;
import dev.ryben.customarrowtrails.CustomArrowTrails;
import dev.ryben.customarrowtrails.event.ArrowTrailEvent;
import org.bukkit.Particle;
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
                || !(event.getEntity().getShooter() instanceof Player)) return; // bug in paper will break this, fix is updating paper
        Player shooter = (Player) event.getEntity().getShooter();
        Particle effect = this.getCustomArrowTrails().getArrowTrailManager().getArrowTrail(shooter.getUniqueId());
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