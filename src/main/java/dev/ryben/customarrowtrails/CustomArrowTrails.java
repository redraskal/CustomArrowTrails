package dev.ryben.customarrowtrails;

import lombok.Getter;
import dev.ryben.customarrowtrails.command.ArrowTrailCommand;
import dev.ryben.customarrowtrails.listener.ArrowTrailListener;
import dev.ryben.customarrowtrails.manager.ArrowTrailManager;
import dev.ryben.customarrowtrails.manager.MessageManager;
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

        // These either don't work, or just disconnect people :D
        List<String> bannedParticles = Arrays.asList(new String[]{
            "BLOCK_CRACK",
            "BLOCK_DUST",
            "ITEM_CRACK",
            "LEGACY_BLOCK_CRACK",
            "LEGACY_BLOCK_DUST",
            "LEGACY_FALLING_DUST"
        });
        
        Arrays.asList(Particle.values()).forEach(particle -> {
            if(!bannedParticles.contains(particle.toString())) {
                arrowTrailEffects.add(particle);
            }
        });

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