package com.quickmenu.managers;

import com.quickmenu.QuickMenu;
import com.quickmenu.models.MenuConfig;
import com.quickmenu.models.MenuItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MenuManager {
    private final QuickMenu plugin;
    private final Map<String, MenuConfig> menus = new LinkedHashMap<>();
    private final Map<String, String> commandToMenu = new LinkedHashMap<>();

    public MenuManager(QuickMenu plugin) {
        this.plugin = plugin;
        loadMenus();
    }

    private void loadMenus() {
        menus.clear();
        commandToMenu.clear();

        File menusDir = new File(plugin.getDataFolder(), "menus");
        if (!menusDir.exists()) {
            menusDir.mkdirs();
            plugin.saveResource("menus/main.yml", false);
            plugin.saveResource("menus/server.yml", false);
        }

        File[] files = menusDir.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".yml"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            loadMenu(file);
        }
    }

    private void loadMenu(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String menuId = file.getName().substring(0, file.getName().length() - 4);

        String title = config.getString("title", "&0菜单");
        int size = normalizeSize(config.getInt("size", 54));
        String permission = emptyToNull(config.getString("permission"));
        String openCommand = normalizeCommand(config.getString("openCommand"));

        ConfigurationSection openItemSection = config.getConfigurationSection("openItem");
        boolean openItemEnabled = openItemSection != null && openItemSection.getBoolean("enable", false);
        String openItemMaterial = openItemSection == null ? "CLOCK" : openItemSection.getString("material", "CLOCK");
        String openItemName = openItemSection == null ? "&a菜单" : openItemSection.getString("name", "&a菜单");
        int openItemSlot = openItemSection == null ? 8 : openItemSection.getInt("slot", 8);

        Map<Integer, MenuItem> items = new LinkedHashMap<>();
        ConfigurationSection menuSection = config.getConfigurationSection("menu");
        if (menuSection != null) {
            for (String key : menuSection.getKeys(false)) {
                ConfigurationSection itemSection = menuSection.getConfigurationSection(key);
                if (itemSection != null) {
                    MenuItem item = loadSectionItem(itemSection, parseSlot(key, itemSection.getInt("index", -1)));
                    if (item.getSlot() >= 0 && item.getSlot() < size) {
                        items.put(item.getSlot(), item);
                    }
                }
            }
        } else {
            loadLegacyList(config, size, items);
        }

        MenuConfig menu = new MenuConfig(
                menuId,
                title,
                size,
                permission,
                openCommand,
                openItemEnabled,
                openItemMaterial,
                openItemName,
                openItemSlot,
                items);
        menus.put(menuId.toLowerCase(Locale.ROOT), menu);

        if (openCommand != null) {
            commandToMenu.put(openCommand.toLowerCase(Locale.ROOT), menuId);
        }
    }

    private MenuItem loadSectionItem(ConfigurationSection section, int fallbackSlot) {
        int slot = section.getInt("index", fallbackSlot);
        String name = section.getString("name", section.getString("text", "&f未命名"));
        String material = section.getString("material", section.getString("java_icon.item", "STONE"));
        int amount = Math.max(1, Math.min(64, section.getInt("amount", 1)));
        List<String> lore = section.getStringList("lore");
        List<String> commands = section.getStringList("commands");

        String legacyCommand = section.getString("command");
        if (commands.isEmpty() && legacyCommand != null) {
            commands = new ArrayList<>();
            commands.add(legacyCommand);
        }

        String action = section.getString("action");
        String nextMenu = section.getString("menu");
        if (commands.isEmpty() && "menu".equalsIgnoreCase(action) && nextMenu != null) {
            commands = List.of("[open] " + nextMenu);
        }

        boolean glow = section.getBoolean("isEnchant", section.getBoolean("glow", false));
        boolean hideFlags = section.getBoolean("hideFlag", true) || section.getBoolean("hideEnchant", true);
        Integer customModelData = null;
        if (section.isInt("custom-model-data")) {
            customModelData = Integer.valueOf(section.getInt("custom-model-data"));
        } else if (section.isInt("customModelData")) {
            customModelData = Integer.valueOf(section.getInt("customModelData"));
        }
        String headOwner = emptyToNull(section.getString("head"));
        String permission = emptyToNull(section.getString("permission"));

        return new MenuItem(slot, name, material, amount, lore, commands, glow, hideFlags, customModelData, headOwner, permission);
    }

    private void loadLegacyList(YamlConfiguration config, int size, Map<Integer, MenuItem> items) {
        List<Map<?, ?>> menuList = config.getMapList("menu");
        for (int i = 0; i < menuList.size() && i < size; i++) {
            Map<?, ?> itemMap = menuList.get(i);
            String text = stringValue(itemMap.get("text"), "&f未命名");
            String action = stringValue(itemMap.get("action"), "command");
            String command = stringValue(itemMap.get("command"), "");
            String nextMenu = stringValue(itemMap.get("menu"), "");
            Object javaIconObject = itemMap.get("java_icon");
            String javaItem = "STONE";
            if (javaIconObject instanceof Map<?, ?> javaIcon) {
                javaItem = stringValue(javaIcon.get("item"), "STONE");
            }

            List<String> commands = new ArrayList<>();
            if ("menu".equalsIgnoreCase(action) && !nextMenu.isEmpty()) {
                commands.add("[open] " + nextMenu);
            } else if (!command.isEmpty()) {
                commands.add(command);
            }
            items.put(i, new MenuItem(i, text, javaItem, 1, List.of(), commands, false, true, null, null, null));
        }
    }

    public MenuConfig getMenu(String name) {
        if (name == null) {
            return menus.get("main");
        }
        return menus.get(name.toLowerCase(Locale.ROOT));
    }

    public MenuConfig getMainMenu() {
        MenuConfig main = getMenu("main");
        if (main != null) {
            return main;
        }
        return menus.values().stream().findFirst().orElse(null);
    }

    public String getMenuByOpenCommand(String command) {
        return commandToMenu.get(command.toLowerCase(Locale.ROOT));
    }

    public Map<String, MenuConfig> getMenus() {
        return menus;
    }

    public boolean hasMenu(String name) {
        return getMenu(name) != null;
    }

    private int normalizeSize(int size) {
        int normalized = ((size + 8) / 9) * 9;
        if (normalized < 9) {
            return 9;
        }
        return Math.min(normalized, 54);
    }

    private int parseSlot(String key, int fallback) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private String normalizeCommand(String command) {
        String value = emptyToNull(command);
        if (value == null) {
            return null;
        }
        return value.startsWith("/") ? value.substring(1) : value;
    }

    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private String stringValue(Object value, String fallback) {
        return value == null ? fallback : String.valueOf(value);
    }
}
