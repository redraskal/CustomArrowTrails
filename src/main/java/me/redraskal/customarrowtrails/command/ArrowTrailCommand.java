package me.redraskal.customarrowtrails.command;

import lombok.Getter;
import me.redraskal.customarrowtrails.CustomArrowTrails;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Copyright (c) Redraskal 2017.
 * <p>
 * Please do not copy the code below unless you
 * have permission to do so from me.
 */
public class ArrowTrailCommand implements CommandExecutor {

    @Getter private final CustomArrowTrails customArrowTrails;

    public ArrowTrailCommand(CustomArrowTrails customArrowTrails) {
        this.customArrowTrails = customArrowTrails;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            for(String line : this.getCustomArrowTrails().getMessageManager().getMessageList("help-command-arguments")) {
                sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                        line.replace("<label>", label)));
            }
        } else {
            switch(args[0].toLowerCase()) {
                case "disable":
                    if(sender instanceof Player) {
                        Player player = (Player) sender;
                        if(player.hasPermission("arrowtrail.disable")) {
                            try {
                                this.getCustomArrowTrails().getArrowTrailManager().setArrowTrail(player.getUniqueId(), null);
                                sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                        this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-disabled-message").replace("<label>", label)));
                            } catch (IOException e) {
                                e.printStackTrace();
                                sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                        this.getCustomArrowTrails().getMessageManager().getMessage("error-while-changing-message").replace("<label>", label)));
                            }
                        } else {
                            sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                    this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                        }
                    } else {
                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("not-a-player-message").replace("<label>", label)));
                    }
                    break;
                case "list":
                    if(sender.hasPermission("arrowtrail.list")) {
                        String effects = "";

                        for(Particle effect : this.getCustomArrowTrails().getArrowTrailEffects()) {
                            if(!effects.isEmpty()) effects+=this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-effects.separator");
                            effects+=this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-effects.prefix") + effect.toString().toLowerCase();
                        }

                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-effects-message").replace("<effects>", effects)));
                    } else {
                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                    }
                    break;
                case "reload":
                    if(sender.hasPermission("arrowtrail.reload")) {
                        this.getCustomArrowTrails().getArrowTrailManager().reloadDataFile();
                        this.getCustomArrowTrails().getMessageManager().reloadDataFile();
                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("data-files-reloaded-message").replace("<label>", label)));
                    } else {
                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                    }
                    break;
                default:
                    if(Bukkit.getPlayerExact(args[0]) != null) {
                        Player player = Bukkit.getPlayerExact(args[0]);
                        if(sender.hasPermission("arrowtrail.force")) {
                            if(args.length > 1) {
                                if(args[1].equalsIgnoreCase("disable")) {
                                    try {
                                        this.getCustomArrowTrails().getArrowTrailManager().setArrowTrail(player.getUniqueId(), null);
                                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-disabled-message").replace("<label>", label)));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                this.getCustomArrowTrails().getMessageManager().getMessage("error-while-changing-message").replace("<label>", label)));
                                    }
                                } else {
                                    for(Particle effect : this.getCustomArrowTrails().getArrowTrailEffects()) {
                                        if(effect.toString().equalsIgnoreCase(args[1])) {
                                            if(this.getCustomArrowTrails().getArrowTrailManager().hasPermission(player.getUniqueId(), effect)) {
                                                try {
                                                    this.getCustomArrowTrails().getArrowTrailManager().setArrowTrail(player.getUniqueId(), effect);
                                                    sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                            this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-set-message").replace("<label>", label)
                                                                    .replace("<trail>", effect.toString().toLowerCase())));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                            this.getCustomArrowTrails().getMessageManager().getMessage("error-while-changing-message").replace("<label>", label)));
                                                }
                                            } else {
                                                sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                        this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                                            }
                                            return false;
                                        }
                                    }
                                    sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                            this.getCustomArrowTrails().getMessageManager().getMessage("invalid-arguments-message").replace("<label>", label)));
                                }
                            } else {
                                sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                        this.getCustomArrowTrails().getMessageManager().getMessage("invalid-arguments-message").replace("<label>", label)));
                            }
                        } else {
                            sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                    this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                        }
                    } else {
                        for(Particle effect : this.getCustomArrowTrails().getArrowTrailEffects()) {
                            if(effect.toString().equalsIgnoreCase(args[0])) {
                                if(sender instanceof Player) {
                                    Player player = (Player) sender;
                                    if(this.getCustomArrowTrails().getArrowTrailManager().hasPermission(player.getUniqueId(), effect)) {
                                        try {
                                            this.getCustomArrowTrails().getArrowTrailManager().setArrowTrail(player.getUniqueId(), effect);
                                            sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                    this.getCustomArrowTrails().getMessageManager().getMessage("arrow-trail-set-message").replace("<label>", label)
                                                            .replace("<trail>", effect.toString().toLowerCase())));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                    this.getCustomArrowTrails().getMessageManager().getMessage("error-while-changing-message").replace("<label>", label)));
                                        }
                                    } else {
                                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                                this.getCustomArrowTrails().getMessageManager().getMessage("no-permission-message").replace("<label>", label)));
                                    }
                                } else {
                                    sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                            this.getCustomArrowTrails().getMessageManager().getMessage("not-a-player-message").replace("<label>", label)));
                                }
                                return false;
                            }
                        }
                        sender.sendMessage(this.getCustomArrowTrails().getMessageManager().buildMessage(
                                this.getCustomArrowTrails().getMessageManager().getMessage("invalid-arguments-message").replace("<label>", label)));
                    }
                    break;
            }
        }
        return false;
    }
}