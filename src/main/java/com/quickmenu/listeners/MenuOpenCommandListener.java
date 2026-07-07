package com.quickmenu.listeners;

import com.quickmenu.QuickMenu;
import com.quickmenu.utils.MenuUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Locale;

public class MenuOpenCommandListener implements Listener {
    private final QuickMenu plugin;

    public MenuOpenCommandListener(QuickMenu plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message == null || !message.startsWith("/")) {
            return;
        }

        String command = message.substring(1).split("\\s+", 2)[0].toLowerCase(Locale.ROOT);
        String menuName = plugin.getMenuManager().getMenuByOpenCommand(command);
        if (menuName == null) {
            return;
        }

        event.setCancelled(true);
        MenuUtil.openMenu(player, menuName);
    }
}
