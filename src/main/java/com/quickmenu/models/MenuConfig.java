package com.quickmenu.models;

import java.util.Map;

public class MenuConfig {
    private final String id;
    private final String title;
    private final int size;
    private final String permission;
    private final String openCommand;
    private final boolean openItemEnabled;
    private final String openItemMaterial;
    private final String openItemName;
    private final int openItemSlot;
    private final Map<Integer, MenuItem> items;

    public MenuConfig(
            String id,
            String title,
            int size,
            String permission,
            String openCommand,
            boolean openItemEnabled,
            String openItemMaterial,
            String openItemName,
            int openItemSlot,
            Map<Integer, MenuItem> items) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.permission = permission;
        this.openCommand = openCommand;
        this.openItemEnabled = openItemEnabled;
        this.openItemMaterial = openItemMaterial;
        this.openItemName = openItemName;
        this.openItemSlot = openItemSlot;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public String getPermission() {
        return permission;
    }

    public String getOpenCommand() {
        return openCommand;
    }

    public boolean isOpenItemEnabled() {
        return openItemEnabled;
    }

    public String getOpenItemMaterial() {
        return openItemMaterial;
    }

    public String getOpenItemName() {
        return openItemName;
    }

    public int getOpenItemSlot() {
        return openItemSlot;
    }

    public Map<Integer, MenuItem> getItems() {
        return items;
    }
}
