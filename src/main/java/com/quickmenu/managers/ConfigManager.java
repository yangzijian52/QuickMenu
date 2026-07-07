package com.quickmenu.managers;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private final QuickMenu plugin;
    private FileConfiguration langConfig;
    private final NamespacedKey openItemKey;

    public ConfigManager(QuickMenu plugin) {
        this.plugin = plugin;
        this.openItemKey = new NamespacedKey(plugin, "open_menu");
        loadLanguage();
    }

    private void loadLanguage() {
        String language = plugin.getConfig().getString("language", "zh-CN");
        File langFile = new File(plugin.getDataFolder(), "lang/" + language + ".yml");
        
        if (!langFile.exists()) {
            plugin.saveResource("lang/" + language + ".yml", false);
        }
        
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        
        InputStream defStream = plugin.getResource("lang/" + language + ".yml");
        if (defStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(defStream, StandardCharsets.UTF_8));
            langConfig.setDefaults(defConfig);
        }
    }

    public String getMessage(String key) {
        String prefix = langConfig.getString("prefix", "&a[QuickMenu] &f");
        String message = langConfig.getString(key, key);
        return prefix + message;
    }

    public String getMessage(String key, String placeholder, String value) {
        return getMessage(key).replace("{" + placeholder + "}", value);
    }

    public Material getTriggerItem() {
        String itemName = plugin.getConfig().getString("trigger_item", "CLOCK");
        return getMaterial(itemName, Material.CLOCK);
    }

    public Material getMaterial(String itemName, Material fallback) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return fallback;
        }
        try {
            return Material.valueOf(itemName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid material: " + itemName + ", using " + fallback.name());
            return fallback;
        }
    }

    public ItemStack createOpenItem(MenuConfig menu) {
        Material material = getMaterial(menu.getOpenItemMaterial(), Material.CLOCK);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.displayName(colorize(menu.getOpenItemName()));
            meta.getPersistentDataContainer().set(openItemKey, PersistentDataType.STRING, menu.getId());
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public boolean isOpenItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.has(openItemKey, PersistentDataType.STRING);
    }

    public String getOpenItemMenu(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return null;
        }
        return itemStack.getItemMeta().getPersistentDataContainer().get(openItemKey, PersistentDataType.STRING);
    }

    public Component colorize(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text == null ? "" : text);
    }

    public String applyPlaceholders(String text, String playerName) {
        if (text == null) {
            return "";
        }
        return text
                .replace("{player}", playerName)
                .replace("%player_name%", playerName)
                .replace("%player%", playerName);
    }
}
