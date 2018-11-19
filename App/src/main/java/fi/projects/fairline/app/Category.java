package fi.projects.fairline.app;

public class Category {

    public String name;

    public Category (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}