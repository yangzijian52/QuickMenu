package com.quickmenu.commands;

import com.quickmenu.QuickMenu;
import com.quickmenu.utils.MenuUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {
    private final QuickMenu plugin;

    public MenuCommand(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open menus.");
            return true;
        }

        MenuUtil.openMenu(player, args.length > 0 ? args[0] : null);
        return true;
    }
}
