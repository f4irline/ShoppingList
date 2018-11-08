package fi.projects.fairline.ezparser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;

    public EzParser () {
        createJSONFile();
        initJSONWriter();
        System.out.println("EzParser Initialized");
    }

    private void createJSONFile() {
        if (new File("EzParser/src/resources/data.json").isFile()) {
            jsonFile = new File("EzParser/src/resources/data.json");
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

    private void initJSONWriter() {
        try {
            jsonWriter = new FileWriter(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object obj) {
        Class<?> c = obj.getClass();
        for (Field field : c.getDeclaredFields()) {
            try {
                System.out.println("\""+field.getName() + "\": \"" +  field.get(obj).toString() + "\"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void flushWriter() {
        try {
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWriter() {
        try {
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}