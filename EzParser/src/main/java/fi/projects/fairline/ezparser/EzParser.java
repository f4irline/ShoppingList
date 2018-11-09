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
        System.out.println("EzParser Initialized");
    }

    private void createJSONFile() {
        if (new File("EzParser/src/resources/data.json").isFile()) {
            System.out.println("File already exists. Using the existing file.");
            initJSONWriter();
        } else {
            try {
                jsonFile = new File("EzParser/src/resources/data.json");
                jsonFile.createNewFile();

                initJSONWriter();

                initJSON();

                System.out.println("File created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initJSONWriter() {
        try {
            jsonWriter = new FileWriter("EzParser/src/resources/data.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initJSON() {
        StringBuilder jsonInit = new StringBuilder();
        jsonInit.append("{");
        jsonInit.append("\n\n");
        jsonInit.append("}");
        
        try {
            jsonWriter.write(jsonInit.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        flushWriter();
    }

    public void write(Object obj) {
        StringBuilder jsonString = new StringBuilder();
        Class<?> c = obj.getClass();
        jsonString.append("\"name\" : ");
        for (Field field : c.getDeclaredFields()) {
            try {
                jsonString.append("\""+field.getName()+"\"");
                jsonWriter.write(jsonString.toString());
                System.out.println("\""+field.getName() + "\": \"" +  field.get(obj).toString() + "\"");
            } catch (IllegalAccessException | IOException e) {
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