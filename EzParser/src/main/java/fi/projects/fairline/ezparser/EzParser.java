package fi.projects.fairline.ezparser;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;
    private List<String> lines;
    private List<String> items = new ArrayList<>();
    private int indentAmount = 0;
    private StringBuilder jsonString;

    public EzParser () {
        createJSONFile();
        System.out.println("EzParser Initialized");
    }

    private void createJSONFile() {
        if (new File("data.json").isFile() &&
            new File("data.json").length() != 0) {
            System.out.println("File already exists. Using the existing file.");
            initJSONWriter();
            readFileToArray(false);
        } else {
            try {
                jsonFile = new File("data.json");
                jsonFile.createNewFile();

                initJSONWriter();

                initJSON();

                System.out.println("File initialized.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initJSONWriter() {
        try {
            jsonWriter = new FileWriter("data.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initJSON() {
        jsonString = new StringBuilder();
        jsonString.append("{");
        jsonString.append("\n");
        jsonString.append("    \"list\": [");
        jsonString.append("\n");
        jsonString.append("    ]");
        jsonString.append("\n");
        jsonString.append("}");
        
        try {
            jsonWriter.write(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        flushWriter();
    }

    public void write(String item) {
        readFileToArray(false);
        items.add(item);
        addItemToJSON(item);
    }

    public void remove(String item) {
        readFileToArray(true);
        items.remove(item);
    }

    private void addItemToJSON(String item) {
        item = "\""+item+"\"";
        int startIndex = 0;
        int addIndex = 0;
        int index = 0;
        for (String line : lines) {
            index++;
            if (line.contains("]")) {
                addIndex = index;
                break;
            } else if (line.contains("[")) {
                startIndex = index;
            }
        }
        lines.add(addIndex-1, item);
        writeToFile(startIndex, addIndex);
    }

    private void writeToFile(int startIndex, int addIndex) {
        int index = 0;
        jsonString = new StringBuilder();
        for (String line : lines) {
            if (line.contains("}") || line.contains("]")) {
                indentAmount -= 4;
            }
            indent();
            if (index > startIndex-1 && index < addIndex-1) {
                jsonString.append(line+",\n");
            } else {
                jsonString.append(line+"\n");
            }
            if (line.contains("{") || line.contains("[")) {
                indentAmount += 4;
            }
            index++;
        }
        try {
            jsonWriter = new FileWriter("data.json");
            jsonWriter.write(jsonString.toString());
            flushWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getItems() {
        return items;
    }

    private void readFileToArray(boolean removing) {
        lines = new ArrayList<String>();
        items = new ArrayList<String>();
        int itemArrayIndex = 0;
        boolean nextIsArray = false;
        String indent = "    ";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data.json"))) {
            String line;

            while ((line = fileReader.readLine()) != null) {
                line = line.replaceAll(indent, "");
                line = line.replaceAll(",", "");
                lines.add(line);
                if (nextIsArray && !line.contains("]")) {
                    line = line.replaceAll("\"", "");
                    items.add(line);
                } else if (line.contains("[")) {
                    nextIsArray = true;
                } else if (line.contains("]")) {
                    nextIsArray = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private void readFileToArray() {
    //     lines = new ArrayList<String>();
    //     try (BufferedReader fileReader = new BufferedReader(new FileReader("EzParser/src/resources/data.json"))) {
    //         String line;

    //         while ((line = fileReader.readLine()) != null) {
    //             line = line.replaceAll("\\s+", "");
    //             lines.add(line);
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }    
    // }

    private void indent() {
        for (int i = 0; i < indentAmount; i++) {
            jsonString.append(" ");
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