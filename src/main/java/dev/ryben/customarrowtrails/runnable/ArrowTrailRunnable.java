package dev.ryben.customarrowtrails.runnable;

import lombok.Getter;
import dev.ryben.customarrowtrails.CustomArrowTrails;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class ArrowTrailRunnable extends BukkitRunnable {

    @Getter private final CustomArrowTrails customArrowTrails;
    @Getter private final Arrow arrow;
    @Getter private final Particle effect;

    //private final int range;
    private Vector lastVelocity;

    public ArrowTrailRunnable(CustomArrowTrails customArrowTrails, Arrow arrow, Particle effect) {
        this.customArrowTrails = customArrowTrails;
        this.arrow = arrow;
        this.effect = effect;
        //this.range = customArrowTrails.getConfig().getInt("arrow-trail-range");
        this.runTaskTimer(this.getCustomArrowTrails(), 0, customArrowTrails.getConfig().getLong("arrow-trail-tick-rate"));
    }

    @Override
    public void run() {
        if(arrow.isDead() || (lastVelocity != null && lastVelocity.equals(arrow.getVelocity()))) {
            this.cancel();
        } else {
            for(int offset=0; offset<4; ++offset) {
                Location location = arrow.getLocation().clone();
                location.setX(location.getX() + arrow.getVelocity().getX() * (double) offset / 4.0D);
                location.setY(location.getY() + arrow.getVelocity().getY() * (double) offset / 4.0D);
                location.setZ(location.getZ() + arrow.getVelocity().getZ() * (double) offset / 4.0D);
                // Range was removed in 1.13 for particles?
                arrow.getWorld().spawnParticle(effect, location, 1,
                        0, 0, 0,
                        0);
            }
            lastVelocity = arrow.getVelocity();
        }
    }
}