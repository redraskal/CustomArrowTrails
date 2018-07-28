package me.redraskal.customarrowtrails;

import lombok.Getter;
import me.redraskal.customarrowtrails.command.ArrowTrailCommand;
import me.redraskal.customarrowtrails.listener.ArrowTrailListener;
import me.redraskal.customarrowtrails.manager.ArrowTrailManager;
import me.redraskal.customarrowtrails.manager.MessageManager;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class CustomArrowTrails extends JavaPlugin {

    @Getter private ArrowTrailManager arrowTrailManager;
    @Getter private MessageManager messageManager;

    @Getter private List<Particle> arrowTrailEffects;

    public void onEnable() {
        this.saveDefaultConfig();

        this.arrowTrailEffects = new ArrayList<>();
        Arrays.asList(Particle.values()).forEach(particle -> arrowTrailEffects.add(particle));

        // These either don't work, or just disconnect people :D
        arrowTrailEffects.remove(Particle.BLOCK_CRACK);
        arrowTrailEffects.remove(Particle.BLOCK_DUST);
        arrowTrailEffects.remove(Particle.ITEM_CRACK);
        arrowTrailEffects.remove(Particle.LEGACY_BLOCK_CRACK);
        arrowTrailEffects.remove(Particle.LEGACY_BLOCK_DUST);
        arrowTrailEffects.remove(Particle.LEGACY_FALLING_DUST);

        try {
            this.arrowTrailManager = new ArrowTrailManager(this);
            this.messageManager = new MessageManager(this);
            this.saveResource("permissions.yml", false);

            this.getServer().getPluginManager().registerEvents(
                    new ArrowTrailListener(this), this);
            this.getCommand("customarrowtrails").setExecutor(new ArrowTrailCommand(this));
        } catch (IOException e) {
            this.getLogger().severe("The plugin could not access the data.yml file, killing process.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }
}