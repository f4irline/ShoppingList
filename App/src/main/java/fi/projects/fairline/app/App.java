package fi.projects.fairline.app;
import fi.projects.fairline.ezparser.EzParser;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        System.out.println("App started.");
        EzParser ezParser = new EzParser();
        Category vegetables = new Category("Vegetables");

        ezParser.write(vegetables);
    }
}