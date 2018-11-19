package fi.projects.fairline.app;
import fi.projects.fairline.ezparser.EzParser;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        boolean running = true;
        System.out.println("App started.");
        EzParser ezParser = new EzParser();
        List<String> items = ezParser.getItems();

        while (running) {
            System.out.println("What would you like to do? ('Add' item, 'Remove' item, 'Check' Items): ");
            userInput = input.nextLine();
            if (userInput.equals("check")) {
                printItems(items);
            } else if (userInput.equals("add")) {
                System.out.println("What would you like to add?");
                userInput = input.nextLine();
                ezParser.write(userInput);
                items = ezParser.getItems();
            } else if (userInput.equals("remove")) {
                System.out.println("What would you like to remove?");
                userInput = input.nextLine();
                ezParser.remove(userInput);
                items = ezParser.getItems();
            }
        }
        input.close();
    }

    public static void printItems(List<String> items) {
        System.out.println("Current items: ");
        for (String item : items) {
            System.out.println(item);
        }
    }
}