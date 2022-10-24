package com.example.trabajopractico.DetailAdapter;

import com.j256.ormlite.field.DatabaseField;

public class ItemText {
    private String type;
    private String url;
    public ItemText() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
