package com.quickmenu.listeners;

import com.quickmenu.QuickMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements Listener {
    private final QuickMenu plugin;

    public PlayerJoinListener(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Material triggerItem = plugin.getConfigManager().getTriggerItem();
        
        // 统计背包中触发物品的数量
        int itemCount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == triggerItem) {
                itemCount += item.getAmount();
            }
        }
        
        if (itemCount == 0) {
            // 没有，给一个
            ItemStack item = new ItemStack(triggerItem);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.displayName(net.kyori.adventure.text.Component.text("菜单"));
                item.setItemMeta(meta);
            }
            player.getInventory().addItem(item);
        } else if (itemCount > 1) {
            // 大于1，先移除所有，再给一个
            player.getInventory().remove(triggerItem);
            ItemStack item = new ItemStack(triggerItem);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.displayName(net.kyori.adventure.text.Component.text("菜单"));
                item.setItemMeta(meta);
            }
            player.getInventory().addItem(item);
        }
    }
}
