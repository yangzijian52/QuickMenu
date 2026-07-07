package com.quickmenu.listeners;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {
    private final QuickMenu plugin;

    public PlayerJoinListener(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("give-open-item-on-join", true)) {
            return;
        }

        Player player = event.getPlayer();
        MenuConfig menu = plugin.getMenuManager().getMainMenu();
        if (menu == null || !menu.isOpenItemEnabled()) {
            return;
        }

        for (ItemStack item : player.getInventory().getContents()) {
            if (plugin.getConfigManager().isOpenItem(item)) {
                return;
            }
        }

        ItemStack openItem = plugin.getConfigManager().createOpenItem(menu);
        int slot = menu.getOpenItemSlot();
        if (slot >= 0 && slot < player.getInventory().getSize() && player.getInventory().getItem(slot) == null) {
            player.getInventory().setItem(slot, openItem);
        } else {
            player.getInventory().addItem(openItem);
        }
    }
}
