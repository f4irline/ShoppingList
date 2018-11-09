package fi.projects.fairline.ezparser;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.*;

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;

    public EzParser () {
        createJSONFile();
        System.out.println("EzParser Initialized");
    }

    private void createJSONFile() {
        if (new File("EzParser/src/resources/data.json").isFile()) {
            System.out.println("File already exists. Using the existing file.");
        } else {
            try {
                jsonFile = new File("EzParser/src/resources/data.json");
                jsonFile.createNewFile();

                System.out.println("File created.");
            
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void write (Object obj) {
        Class<?> c = obj.getClass();
        List<String> items = new ArrayList<>();
        String className = "";
        String listName = "";

        for (Field field : c.getDeclaredFields()) {
            try {
                if (field.getName().equals("items")) {
                    listName = field.getName();
                    items = (List<String>) field.get(obj);
                } else if (field.getName().equals("name")) {
                    className = field.get(obj).toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(className);

        System.out.println(listName);

        for (String item : items) {
            System.out.println(item);   
        }

        writeToJSON(className, listName, items);
    }

    public void writeToJSON(String className, String listName, List<String> items) {
        String toWrite = "Terse";

        try {
            jsonWriter = new FileWriter(jsonFile);
            jsonWriter.write(toWrite);
            jsonWriter.flush();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}