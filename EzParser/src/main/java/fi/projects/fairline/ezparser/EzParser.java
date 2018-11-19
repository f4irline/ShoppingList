package fi.projects.fairline.ezparser;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;
    private List<String> lines;
    private HashMap<Integer, String> items = new HashMap<>();
    private int itemNumber = 100;
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
            readFileToArray();
        } else {
            try {
                jsonFile = new File("data.json");
                jsonFile.createNewFile();

                initJSONWriter();

                initJSON();

                readFileToArray();

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
        itemNumber = checkNextValidID();
        items.put(itemNumber, item);
        addItemToJSON(item);
    }

    public void remove(int key) {
        items.remove(key);
        removeItemFromJSON(key);
    }

    private int checkNextValidID() {
        int index = 101;
        Set set = items.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (index != (int) mentry.getKey()) {
                break;
            }
            index++;
        } 
        return index;
    }

    public HashMap<Integer, String> getItems() {
        return items;
    }

    private void removeItemFromJSON(int key) {
        int removeIndex = 0;
        int index = 0;
        String keyString = Integer.toString(key);

        for (String line : lines) {
            index++;
            if (line.contains(keyString)) {
                removeIndex = index;
            }
        }

        lines.remove(removeIndex-1);

        int startIndex = 0;
        int lastIndex = 0;
        index = 0;

        for (String line : lines) {
            index++;
            if (line.contains("]")) {
                lastIndex = index;
                break;
            } else if (line.contains("[")) {
                startIndex = index;
            }
        }
        writeToFile(startIndex, lastIndex-1);
    }

    private void addItemToJSON(String item) {
        item = "{ \""+itemNumber+"\" : \""+item+"\" }";
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

    private void writeToFile(int startIndex, int lastIndex) {
        int index = 0;
        jsonString = new StringBuilder();
        for (String line : lines) {
            if (line.contains("}") || line.contains("]")) {
                if (!line.contains("{") && !line.contains("[")) {
                    indentAmount -= 4;
                }
            }
            indent();
            if (index > startIndex-1 && index < lastIndex-1) {
                jsonString.append(line+",\n");
            } else {
                jsonString.append(line+"\n");
            }
            if (line.contains("{") || line.contains("[")) {
                if (!line.contains("}") && !line.contains("]")) {
                    indentAmount += 4;
                }
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

    private void readFileToArray() {
        lines = new ArrayList<String>();
        boolean nextIsArray = false;
        String indent = "    ";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data.json"))) {
            String line;

            while ((line = fileReader.readLine()) != null) {
                line = line.replaceAll(indent, "");
                line = line.replaceAll(",", "");
                lines.add(line);
                if (nextIsArray && !line.contains("]")) {
                    itemNumber++;
                    line = line.replaceAll("\"", "");
                    line = line.replaceAll(" ", "");
                    int key = Integer.parseInt(line.substring(1, 4));
                    String value = line.substring(5);
                    value = value.replaceAll("}", "");
                    items.put(key, value);
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