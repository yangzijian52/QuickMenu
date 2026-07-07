package com.quickmenu.models;

import java.util.List;

public class MenuItem {
    private final int slot;
    private final String name;
    private final String material;
    private final int amount;
    private final List<String> lore;
    private final List<String> commands;
    private final boolean glow;
    private final boolean hideFlags;
    private final Integer customModelData;
    private final String headOwner;
    private final String permission;

    public MenuItem(
            int slot,
            String name,
            String material,
            int amount,
            List<String> lore,
            List<String> commands,
            boolean glow,
            boolean hideFlags,
            Integer customModelData,
            String headOwner,
            String permission) {
        this.slot = slot;
        this.name = name;
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.commands = commands;
        this.glow = glow;
        this.hideFlags = hideFlags;
        this.customModelData = customModelData;
        this.headOwner = headOwner;
        this.permission = permission;
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<String> getCommands() {
        return commands;
    }

    public boolean isGlow() {
        return glow;
    }

    public boolean isHideFlags() {
        return hideFlags;
    }

    public Integer getCustomModelData() {
        return customModelData;
    }

    public String getHeadOwner() {
        return headOwner;
    }

    public String getPermission() {
        return permission;
    }
}
