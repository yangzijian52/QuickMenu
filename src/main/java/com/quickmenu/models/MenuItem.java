package com.quickmenu.models;

public class MenuItem {
    private final String text;
    private final String action;
    private final String command;
    private final String nextMenu;
    private final String javaItem;

    public MenuItem(String text, String action, String command, String nextMenu, String javaItem) {
        this.text = text;
        this.action = action;
        this.command = command;
        this.nextMenu = nextMenu;
        this.javaItem = javaItem;
    }

    public String getText() {
        return text;
    }

    public String getAction() {
        return action;
    }

    public String getCommand() {
        return command;
    }

    public String getNextMenu() {
        return nextMenu;
    }

    public String getJavaItem() {
        return javaItem;
    }
}
