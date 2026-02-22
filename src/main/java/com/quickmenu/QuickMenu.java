package com.quickmenu;

import com.quickmenu.commands.MenuCommand;
import com.quickmenu.commands.QuickMenuCommand;
import com.quickmenu.listeners.PlayerJoinListener;
import com.quickmenu.listeners.PlayerInteractListener;
import com.quickmenu.managers.ConfigManager;
import com.quickmenu.managers.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickMenu extends JavaPlugin {
    private static QuickMenu instance;
    private ConfigManager configManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        menuManager = new MenuManager(this);
        
        getCommand("menu").setExecutor(new MenuCommand(this));
        getCommand("quickmenu").setExecutor(new QuickMenuCommand(this));
        
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        
        getLogger().info("QuickMenu has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("QuickMenu has been disabled!");
    }

    public static QuickMenu getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void reload() {
        reloadConfig();
        configManager = new ConfigManager(this);
        menuManager = new MenuManager(this);
    }
}
