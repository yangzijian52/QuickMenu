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
    private static final Map<UUID, MenuConfig> ACTIVE_MENUS = new HashMap<>();
    private final QuickMenu plugin;

    public MenuClickListener(QuickMenu plugin) {
        this.plugin = plugin;
    }

    public static void registerMenu(Player player, MenuConfig menu) {
        ACTIVE_MENUS.put(player.getUniqueId(), menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        MenuConfig menu = ACTIVE_MENUS.get(player.getUniqueId());
        if (menu == null) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();
        if (slot < 0 || slot >= menu.getSize()) {
            return;
        }

        MenuItem item = menu.getItems().get(slot);
        if (item == null) {
            return;
        }

        plugin.getServer().getScheduler().runTask(plugin, () -> MenuUtil.handleMenuAction(player, item));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            ACTIVE_MENUS.remove(player.getUniqueId());
        }
    }
}
