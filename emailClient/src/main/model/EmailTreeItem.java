package main.model;

import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<java.lang.String> {

    private String name;

    public EmailTreeItem(String name) {
        super((java.lang.String) name);
        this.name = name;
    }
}
