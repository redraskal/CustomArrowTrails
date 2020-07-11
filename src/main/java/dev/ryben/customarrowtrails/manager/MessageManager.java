package dev.ryben.customarrowtrails.manager;

import lombok.Getter;
import dev.ryben.customarrowtrails.CustomArrowTrails;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class MessageManager {

    @Getter private final CustomArrowTrails customArrowTrails;
    private final File f_config;
    private YamlConfiguration config;

    public MessageManager(CustomArrowTrails customArrowTrails) throws IOException {
        this.customArrowTrails = customArrowTrails;
        this.f_config = new File(this.getCustomArrowTrails().getDataFolder(), "messages.yml");
        this.getCustomArrowTrails().saveResource("messages.yml", false);
        this.reloadDataFile();
    }

    public String buildMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&',
                message.replace("<prefix>", config.getString("prefix")));
    }

    public String getMessage(String key) {
        return config.getString(key);
    }

    public List<String> getMessageList(String key) {
        return config.getStringList(key);
    }

    /**
     * Reloads the messages.yml file.
     */
    public void reloadDataFile() {
        this.config = YamlConfiguration.loadConfiguration(this.f_config);
    }
}