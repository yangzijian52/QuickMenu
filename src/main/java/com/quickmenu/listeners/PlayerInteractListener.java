package com.quickmenu.listeners;

import com.quickmenu.QuickMenu;
import com.quickmenu.utils.MenuUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        Material triggerItem = plugin.getConfigManager().getTriggerItem();
        
        if (item.getType() == triggerItem) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                MenuUtil.openMenu(player, "main");
            }
        }
    }
}
