package com.quickmenu;

import com.quickmenu.commands.MenuCommand;
import com.quickmenu.commands.QuickMenuCommand;
import com.quickmenu.listeners.MenuOpenCommandListener;
import com.quickmenu.listeners.PlayerJoinListener;
import com.quickmenu.listeners.PlayerInteractListener;
import com.quickmenu.utils.MenuClickListener;
import com.quickmenu.managers.ConfigManager;
import com.quickmenu.managers.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickMenu extends JavaPlugin {
    private static QuickMenu instance;
    private ConfigManager configManager;
    private MenuManager menuManager;
    private boolean floodgateAvailable;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        floodgateAvailable = getServer().getPluginManager().isPluginEnabled("floodgate");
        configManager = new ConfigManager(this);
        menuManager = new MenuManager(this);
        
        MenuCommand menuCommand = new MenuCommand(this);
        QuickMenuCommand quickMenuCommand = new QuickMenuCommand(this);
        getCommand("menu").setExecutor(menuCommand);
        getCommand("quickmenu").setExecutor(quickMenuCommand);
        
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuOpenCommandListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        
        if (floodgateAvailable) {
            getLogger().info("Floodgate detected, Bedrock form menus are enabled.");
        } else {
            getLogger().warning("Floodgate was not detected, Bedrock form menus are disabled. Java menus still work.");
        }
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

    public boolean isFloodgateAvailable() {
        return floodgateAvailable;
    }

    public void reload() {
        reloadConfig();
        configManager = new ConfigManager(this);
        menuManager = new MenuManager(this);
    }
}
