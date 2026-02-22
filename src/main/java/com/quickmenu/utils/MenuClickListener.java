package com.quickmenu.utils;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuClickListener implements Listener {
    private static final Map<UUID, MenuConfig> activeMenus = new HashMap<>();
    private static boolean registered = false;

    public static void registerMenu(Player player, MenuConfig menu) {
        activeMenus.put(player.getUniqueId(), menu);
        
        if (!registered) {
            QuickMenu.getInstance().getServer().getPluginManager().registerEvents(new MenuClickListener(), QuickMenu.getInstance());
            registered = true;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        MenuConfig menu = activeMenus.get(player.getUniqueId());
        if (menu == null) {
            return;
        }

        event.setCancelled(true);

        int slot = event.getRawSlot();
        if (slot < 0 || slot >= menu.getItems().size()) {
            return;
        }

        MenuItem item = menu.getItems().get(slot);
        player.closeInventory();
        MenuUtil.handleMenuAction(player, item);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            activeMenus.remove(player.getUniqueId());
        }
    }
}
