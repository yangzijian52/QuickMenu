package com.quickmenu.utils;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class FloodgateMenuBridge {
    private FloodgateMenuBridge() {
    }

    static boolean openIfBedrock(QuickMenu plugin, Player player, MenuConfig menu) {
        try {
            if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                return false;
            }
            openBedrockMenu(plugin, player, menu);
            return true;
        } catch (Throwable throwable) {
            plugin.getLogger().warning("Floodgate form menu failed, falling back to Java GUI: " + throwable.getMessage());
            return false;
        }
    }

    private static void openBedrockMenu(QuickMenu plugin, Player player, MenuConfig menu) {
        List<MenuItem> buttons = menu.getItems().values().stream()
                .filter(item -> !item.getCommands().isEmpty())
                .sorted(Comparator.comparingInt(MenuItem::getSlot))
                .toList();

        SimpleForm.Builder builder = SimpleForm.builder()
                .title(MenuUtil.stripColor(menu.getTitle()))
                .content(buildBedrockContent(plugin, player, menu));

        for (MenuItem item : buttons) {
            builder.button(MenuUtil.stripColor(plugin.getConfigManager().applyPlaceholders(item.getName(), player.getName())));
        }

        builder.validResultHandler(response -> {
            int buttonId = response.clickedButtonId();
            if (buttonId >= 0 && buttonId < buttons.size()) {
                MenuUtil.handleMenuAction(player, buttons.get(buttonId));
            }
        });

        FloodgateApi.getInstance().sendForm(player.getUniqueId(), builder);
    }

    private static String buildBedrockContent(QuickMenu plugin, Player player, MenuConfig menu) {
        List<String> lines = new ArrayList<>();
        for (MenuItem item : menu.getItems().values().stream()
                .filter(menuItem -> menuItem.getCommands().isEmpty())
                .sorted(Comparator.comparingInt(MenuItem::getSlot))
                .toList()) {
            if (!item.getLore().isEmpty()) {
                for (String lore : item.getLore()) {
                    lines.add(MenuUtil.stripColor(plugin.getConfigManager().applyPlaceholders(lore, player.getName())));
                }
            }
        }
        return lines.isEmpty() ? "" : String.join("\n", lines);
    }
}
