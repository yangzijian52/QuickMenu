package com.quickmenu.listeners;

import com.quickmenu.QuickMenu;
import com.quickmenu.utils.MenuUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private final QuickMenu plugin;

    public PlayerInteractListener(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (!plugin.getConfigManager().isOpenItem(item)) {
            return;
        }

        String menuName = plugin.getConfigManager().getOpenItemMenu(item);
        event.setCancelled(true);
        MenuUtil.openMenu(event.getPlayer(), menuName);
    }
}
