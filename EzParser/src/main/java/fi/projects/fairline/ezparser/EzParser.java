package fi.projects.fairline.ezparser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;

    public EzParser () {
        createJSONFile();
        System.out.println("EzParser Initialized");
    }

    private void createJSONFile() {
        try {
            jsonFile = new File("EzParser/src/resources/data.json");
            jsonFile.createNewFile();

            jsonWriter = new FileWriter(jsonFile);
            jsonWriter.write("Test");
            jsonWriter.flush();
    
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}