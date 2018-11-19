package fi.projects.fairline.app;
import fi.projects.fairline.ezparser.EzParser;

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        System.out.println("App started.");
        EzParser ezParser = new EzParser();

        HashMap<Integer, String> items = ezParser.getItems();

        while (running) {
            System.out.println("What would you like to do? ('Add' item, 'Remove' item, 'Check' Items): ");
            String userInput = "";
            userInput = input.nextLine();
            if (userInput.equals("check")) {
                printItems(items);
            } else if (userInput.equals("add")) {
                System.out.println("What would you like to add?");
                String addInput = "";
                addInput = input.nextLine();
                ezParser.write(addInput);
                items = ezParser.getItems();
            } else if (userInput.equals("remove")) {
                int keyInput = 0;
                System.out.println("Enter key for removal");
                printItems(items);
                keyInput = input.nextInt();
                ezParser.remove(keyInput);
                items = ezParser.getItems();
            }
            items = ezParser.getItems();
        }
        input.close();
    }

    public static void printItems(HashMap<Integer, String> items) {
        Set set = items.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        } 
    }

    public static void printLines(List<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }
}