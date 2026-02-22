package com.quickmenu.commands;

import com.quickmenu.QuickMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class QuickMenuCommand implements CommandExecutor {
    private final QuickMenu plugin;

    public QuickMenuCommand(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("quickmenu.admin")) {
            sender.sendMessage(colorize(plugin.getConfigManager().getMessage("no_permission")));
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reload();
                sender.sendMessage(colorize(plugin.getConfigManager().getMessage("reload_success")));
                break;
            case "version":
                String version = plugin.getDescription().getVersion();
                sender.sendMessage(colorize(plugin.getConfigManager().getMessage("version_info", "version", version)));
                break;
            default:
                return false;
        }

        return true;
    }

    private Component colorize(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }
}
