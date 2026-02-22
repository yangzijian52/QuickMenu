package com.quickmenu.models;

import java.util.List;

public class MenuConfig {
    private final String title;
    private final String content;
    private final List<MenuItem> items;

    public MenuConfig(String title, String content, List<MenuItem> items) {
        this.title = title;
        this.content = content;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
