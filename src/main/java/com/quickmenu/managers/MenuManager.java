package com.quickmenu.managers;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {
    private final QuickMenu plugin;
    private final Map<String, MenuConfig> menus = new HashMap<>();

    public MenuManager(QuickMenu plugin) {
        this.plugin = plugin;
        loadMenus();
    }

    private void loadMenus() {
        menus.clear();
        File menusDir = new File(plugin.getDataFolder(), "menus");
        
        if (!menusDir.exists()) {
            menusDir.mkdirs();
            plugin.saveResource("menus/main.yml", false);
            plugin.saveResource("menus/demo.yml", false);
        }

        File[] files = menusDir.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                loadMenu(file);
            }
        }
    }

    private void loadMenu(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String menuName = file.getName().replace(".yml", "");
        
        String title = config.getString("title", "Menu");
        String content = config.getString("content", "");
        List<MenuItem> items = new ArrayList<>();
        
        List<Map<?, ?>> menuList = config.getMapList("menu");
        for (Map<?, ?> itemMap : menuList) {
            String text = (String) itemMap.get("text");
            String action = (String) itemMap.get("action");
            String command = (String) itemMap.get("command");
            String nextMenu = (String) itemMap.get("menu");
            
            Map<?, ?> javaIcon = (Map<?, ?>) itemMap.get("java_icon");
            String javaItem = javaIcon != null ? (String) javaIcon.get("item") : "STONE";
            
            items.add(new MenuItem(text, action, command, nextMenu, javaItem));
        }
        
        menus.put(menuName, new MenuConfig(title, content, items));
    }

    public MenuConfig getMenu(String name) {
        return menus.get(name);
    }

    public boolean hasMenu(String name) {
        return menus.containsKey(name);
    }
}
