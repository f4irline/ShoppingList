package fi.projects.fairline.app;

import java.util.List;
import java.util.ArrayList;

public class Category {

    public String name;
    public List<String> items;

    public Category (String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getList () {
        return items;
    }

    @Override
    public String toString () {
        return name;
    }
}