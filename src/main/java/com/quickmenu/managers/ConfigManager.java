package com.quickmenu.managers;

import com.quickmenu.QuickMenu;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private final QuickMenu plugin;
    private FileConfiguration langConfig;

    public ConfigManager(QuickMenu plugin) {
        this.plugin = plugin;
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
        try {
            return Material.valueOf(itemName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid trigger_item: " + itemName + ", using CLOCK");
            return Material.CLOCK;
        }
    }
}
