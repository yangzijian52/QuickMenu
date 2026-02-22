package com.quickmenu.utils;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.floodgate.api.FloodgateApi;

public class MenuUtil {
    
    public static void openMenu(Player player, String menuName) {
        QuickMenu plugin = QuickMenu.getInstance();
        MenuConfig menu = plugin.getMenuManager().getMenu(menuName);
        
        if (menu == null) {
            player.sendMessage(plugin.getConfigManager().getMessage("menu_not_found", "menu", menuName));
            return;
        }

        boolean isBedrockPlayer = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
        
        if (isBedrockPlayer) {
            openBedrockMenu(player, menu);
        } else {
            openJavaMenu(player, menu);
        }
    }

    private static void openJavaMenu(Player player, MenuConfig menu) {
        int size = ((menu.getItems().size() + 8) / 9) * 9;
        if (size > 54) size = 54;
        if (size < 9) size = 9;
        
        Inventory inv = Bukkit.createInventory(null, size, Component.text(menu.getTitle()));
        
        for (int i = 0; i < menu.getItems().size() && i < size; i++) {
            MenuItem item = menu.getItems().get(i);
            Material material;
            try {
                material = Material.valueOf(item.getJavaItem().toUpperCase());
            } catch (IllegalArgumentException e) {
                material = Material.STONE;
            }
            
            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                meta.displayName(Component.text(item.getText()));
                itemStack.setItemMeta(meta);
            }
            inv.setItem(i, itemStack);
        }
        
        player.openInventory(inv);
        MenuClickListener.registerMenu(player, menu);
    }

    private static void openBedrockMenu(Player player, MenuConfig menu) {
        SimpleForm.Builder builder = SimpleForm.builder()
            .title(menu.getTitle())
            .content(menu.getContent());
        
        for (MenuItem item : menu.getItems()) {
            builder.button(item.getText());
        }
        
        builder.validResultHandler(response -> {
            MenuItem item = menu.getItems().get(response.clickedButtonId());
            handleMenuAction(player, item);
        });
        
        FloodgateApi.getInstance().sendForm(player.getUniqueId(), builder);
    }

    public static void handleMenuAction(Player player, MenuItem item) {
        if ("command".equals(item.getAction()) && item.getCommand() != null) {
            String command = item.getCommand();
            if (command.startsWith("op:")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(3).replace("{player}", player.getName()));
            } else if (command.startsWith("cmd:")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(4).replace("{player}", player.getName()));
            } else {
                player.performCommand(command.replace("{player}", player.getName()));
            }
        } else if ("menu".equals(item.getAction()) && item.getNextMenu() != null) {
            openMenu(player, item.getNextMenu());
        }
    }
}
