package com.quickmenu.utils;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuUtil {

    public static void openMenu(Player player, String menuName) {
        QuickMenu plugin = QuickMenu.getInstance();
        MenuConfig menu = menuName == null ? plugin.getMenuManager().getMainMenu() : plugin.getMenuManager().getMenu(menuName);

        if (menu == null) {
            player.sendMessage(plugin.getConfigManager().colorize(
                    plugin.getConfigManager().getMessage("menu_not_found", "menu", String.valueOf(menuName))));
            return;
        }

        if (menu.getPermission() != null && !player.hasPermission(menu.getPermission())) {
            player.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("no_permission")));
            return;
        }

        if (plugin.isFloodgateAvailable() && FloodgateMenuBridge.openIfBedrock(plugin, player, menu)) {
            return;
        }

        openJavaMenu(plugin, player, menu);
    }

    private static void openJavaMenu(QuickMenu plugin, Player player, MenuConfig menu) {
        Inventory inventory = Bukkit.createInventory(null, menu.getSize(), plugin.getConfigManager().colorize(menu.getTitle()));
        for (MenuItem item : menu.getItems().values()) {
            inventory.setItem(item.getSlot(), createItemStack(plugin, player, item));
        }

        player.openInventory(inventory);
        MenuClickListener.registerMenu(player, menu);
    }

    private static ItemStack createItemStack(QuickMenu plugin, Player player, MenuItem item) {
        Material material = plugin.getConfigManager().getMaterial(item.getMaterial(), Material.STONE);
        ItemStack itemStack = new ItemStack(material, item.getAmount());
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        String name = plugin.getConfigManager().applyPlaceholders(item.getName(), player.getName());
        meta.displayName(plugin.getConfigManager().colorize(name));

        if (!item.getLore().isEmpty()) {
            List<Component> lore = new ArrayList<>();
            for (String line : item.getLore()) {
                lore.add(plugin.getConfigManager().colorize(plugin.getConfigManager().applyPlaceholders(line, player.getName())));
            }
            meta.lore(lore);
        }

        if (item.getCustomModelData() != null) {
            meta.setCustomModelData(item.getCustomModelData());
        }

        if (item.isGlow()) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        if (item.isHideFlags()) {
            meta.addItemFlags(ItemFlag.values());
        }

        if (meta instanceof SkullMeta skullMeta && item.getHeadOwner() != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(
                    plugin.getConfigManager().applyPlaceholders(item.getHeadOwner(), player.getName())));
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static void handleMenuAction(Player player, MenuItem item) {
        QuickMenu plugin = QuickMenu.getInstance();
        if (item.getPermission() != null && !player.hasPermission(item.getPermission())) {
            player.sendMessage(plugin.getConfigManager().colorize(plugin.getConfigManager().getMessage("no_permission")));
            return;
        }

        for (String rawCommand : item.getCommands()) {
            executeAction(plugin, player, rawCommand);
        }
    }

    private static void executeAction(QuickMenu plugin, Player player, String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) {
            return;
        }

        String command = plugin.getConfigManager().applyPlaceholders(rawCommand.trim(), player.getName());
        String lower = command.toLowerCase(Locale.ROOT);

        if (lower.startsWith("[command]")) {
            player.performCommand(stripAction(command));
        } else if (lower.startsWith("[console]")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), stripAction(command));
        } else if (lower.startsWith("[op]")) {
            runAsOp(player, stripAction(command));
        } else if (lower.startsWith("[message]")) {
            player.sendMessage(plugin.getConfigManager().colorize(stripAction(command)));
        } else if (lower.startsWith("[open]")) {
            openMenu(player, stripAction(command));
        } else if (lower.startsWith("[close]")) {
            player.closeInventory();
        } else if (lower.startsWith("[sound]")) {
            playSound(player, stripAction(command));
        } else if (lower.startsWith("op:")) {
            runAsOp(player, command.substring(3).trim());
        } else if (lower.startsWith("cmd:")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(4).trim());
        } else {
            player.performCommand(command.startsWith("/") ? command.substring(1) : command);
        }
    }

    private static String stripAction(String command) {
        int end = command.indexOf(']');
        if (end < 0 || end + 1 >= command.length()) {
            return "";
        }
        String value = command.substring(end + 1).trim();
        return value.startsWith("/") ? value.substring(1) : value;
    }

    private static void runAsOp(Player player, String command) {
        boolean wasOp = player.isOp();
        try {
            if (!wasOp) {
                player.setOp(true);
            }
            player.performCommand(command.startsWith("/") ? command.substring(1) : command);
        } finally {
            if (!wasOp) {
                player.setOp(false);
            }
        }
    }

    private static void playSound(Player player, String soundName) {
        String key = soundName.toLowerCase(Locale.ROOT).replace('_', '.');
        Sound sound = Registry.SOUNDS.get(NamespacedKey.minecraft(key));
        if (sound != null) {
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } else {
            QuickMenu.getInstance().getLogger().warning("Invalid sound: " + soundName);
        }
    }

    static String stripColor(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("(?i)&[0-9a-fk-orx]", "");
    }
}
