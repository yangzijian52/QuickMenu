package com.quickmenu.commands;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.utils.MenuUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuickMenuCommand implements CommandExecutor {
    private final QuickMenu plugin;

    public QuickMenuCommand(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender, label);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "open" -> open(sender, args);
            case "adminopen" -> adminOpen(sender, args);
            case "reload" -> reload(sender);
            case "close" -> close(sender, args);
            case "clock", "item" -> giveOpenItem(sender, args);
            case "getmaterial" -> getMaterial(sender);
            case "version" -> sender.sendMessage(plugin.getConfigManager().colorize(
                    plugin.getConfigManager().getMessage("version_info", "version", plugin.getDescription().getVersion())));
            default -> sendHelp(sender, label);
        }

        return true;
    }

    private void open(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open menus.");
            return;
        }
        MenuUtil.openMenu(player, args.length > 1 ? args[1] : null);
    }

    private void adminOpen(CommandSender sender, String[] args) {
        if (!sender.hasPermission("quickmenu.admin")) {
            sender.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("no_permission")));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("/quickmenu adminOpen <player> [menu]");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            sender.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("player_not_found")));
            return;
        }
        MenuUtil.openMenu(target, args.length > 2 ? args[2] : null);
    }

    private void reload(CommandSender sender) {
        if (!sender.hasPermission("quickmenu.admin")) {
            sender.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("no_permission")));
            return;
        }
        plugin.reload();
        sender.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("reload_success")));
    }

    private void close(CommandSender sender, String[] args) {
        if (args.length > 1 && sender.hasPermission("quickmenu.admin")) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                target.closeInventory();
            }
            return;
        }
        if (sender instanceof Player player) {
            player.closeInventory();
        }
    }

    private void giveOpenItem(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can receive menu items.");
            return;
        }
        MenuConfig menu = args.length > 1 ? plugin.getMenuManager().getMenu(args[1]) : plugin.getMenuManager().getMainMenu();
        if (menu == null) {
            sender.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("menu_not_found", "menu", args.length > 1 ? args[1] : "main")));
            return;
        }
        player.getInventory().addItem(plugin.getConfigManager().createOpenItem(menu));
        player.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("item_received")));
    }

    private void getMaterial(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can inspect held items.");
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        Material material = itemStack.getType();
        player.sendMessage(plugin.getConfigManager().colorize("&a手中物品材质: &f" + material.name()));
    }

    private void sendHelp(CommandSender sender, String label) {
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " open [菜单] &7- 打开菜单"));
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " clock [菜单] &7- 获取菜单打开钟表"));
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " item [菜单] &7- 获取菜单打开物品，等同 clock"));
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " getMaterial &7- 查看手中物品材质"));
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " close &7- 关闭自己的菜单界面"));
        sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " version &7- 查看插件版本"));
        if (sender.hasPermission("quickmenu.admin")) {
            sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " reload &7- 重载配置"));
            sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " adminOpen <玩家> [菜单] &7- 为玩家打开菜单"));
            sender.sendMessage(plugin.getConfigManager().colorize("&a/" + label + " close <玩家> &7- 关闭指定玩家菜单"));
        }
    }
}
