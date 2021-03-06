package dev.ryben.customarrowtrails.manager;

import lombok.Getter;
import dev.ryben.customarrowtrails.CustomArrowTrails;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class ArrowTrailManager {

    @Getter private final CustomArrowTrails customArrowTrails;
    private final File f_config;
    private YamlConfiguration config;

    public ArrowTrailManager(CustomArrowTrails customArrowTrails) throws IOException {
        this.customArrowTrails = customArrowTrails;
        this.f_config = new File(this.getCustomArrowTrails().getDataFolder(), "data.yml");
        if(!this.f_config.exists()) f_config.createNewFile();
        this.reloadDataFile();
    }

    /**
     * Returns the enabled Arrow Trail effect, if any.
     * Returns null if not found.
     * @param uuid
     * @return
     */
    public Particle getArrowTrail(UUID uuid) {
        if(config.contains(uuid.toString())) {
            return Particle.valueOf(config.getString(uuid.toString()).toUpperCase());
        } else {
            return null;
        }
    }

    /**
     * Sets the enabled Arrow Trail effect for a player.
     * @param uuid
     * @param effect
     * @throws IOException
     */
    public void setArrowTrail(UUID uuid, Particle effect) throws IOException {
        if(effect == null) {
            config.set(uuid.toString(), null);
        } else {
            config.set(uuid.toString(), effect.toString());
        }
        config.save(this.f_config);
    }

    /**
     * Returns true if the online player has permission
     * to enable the specified Arrow Trail effect.
     * @param uuid
     * @param effect
     * @return
     */
    public boolean hasPermission(UUID uuid, Particle effect) {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return false;
        return (player.hasPermission("arrowtrail.effect.*")
                || player.hasPermission("arrowtrail.effect." + effect.toString().toLowerCase()));
    }

    /**
     * Reloads the data.yml file.
     */
    public void reloadDataFile() {
        this.config = YamlConfiguration.loadConfiguration(this.f_config);
    }
}