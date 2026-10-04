import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // Validate the command-line arguments
        if (args.length == 0) {
            System.out.println("Error: missing argument \"trees\" or \"states\"");
            System.out.println("Usage: java Test <states|trees>");
            return;
        }
        if (args.length > 1) {
            System.out.println("Error: too many arguments (expected 1, got " + args.length + ").");
            System.out.println("Usage: java Test <states|trees>");
            return;
        }
        String type = args[0].trim().toLowerCase();
        if (!type.equals("states") && !type.equals("trees")) {
            System.out.println("Invalid data type. States and trees are the only supported types");
            return;
        }

        ArrayList<Pair<String, String>> states = new ArrayList<>();
        ArrayList<Pair<String, Integer>> trees = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        if (type.equals("states")) {
            if (readStates(states, type+".txt")) {
                runMenu(states, scanner, type, "capital");
            }
        } else {
            if (readTrees(trees, type+".txt")) {
                runMenu(trees, scanner, type, "height");
            }
        }

        scanner.close();
    }

    public static <E1, E2> int search(ArrayList<Pair<E1, E2>> list, E1 key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFirst().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public static <E2 extends Comparable<E2>> void runMenu(ArrayList<Pair<String, E2>> list,
        Scanner scanner, String type, String secondName) {
        String capitalized = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        int choice = 0;

        while (choice != 5) {
            System.out.println("Select an operation:");
            System.out.println("1: View the list of " + type + "s");
            System.out.println("2: Search " + type + "s by name");
            System.out.println("3: Sort " + type + " by name");
            System.out.println("4: Sort " + type + " by " + secondName);
            System.out.println("5: Exit");

        
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("no operation entered. Please enter a number from 1 to 5.");
                continue;
            }
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("\"" + line + "\" is not a number. Please enter a number from 1 to 5.");
                continue;
            }
            if (choice < 1 || choice > 5) {
                System.out.println(choice + " is not a valid operation. Please enter a number from 1 to 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (list.isEmpty()) {
                        System.out.println("The list of " + type + " is empty.");
                    }
                    for (Pair<String, E2> p : list) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.println("Enter a " + type + " name: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        throw new IllegalArgumentException("the " + type + " name cannot be empty.");
                    }
                    int index = search(list, name);
                    if (index != -1) {
                        System.out.println(capitalized + " found: " + list.get(index));
                    } else {
                        System.out.println("No " + type + " found.");
                    }
                    break;
                case 3:
                    list.sort(new ComparatorByFirst<String, E2>());
                    break;
                case 4:
                    list.sort(new ComparatorBySecond<String, E2>());
                    break;
                default: // case 5: exit
                    break;
            }
        

            if (choice != 5) {
                System.out.println();
            }
        }
    }

    public static boolean readStates(ArrayList<Pair<String, String>> states, String filename) {
        try (Scanner reader = new Scanner(new File(filename))) {
            int lineNumber = 0;
            while (reader.hasNextLine()) {
                lineNumber++;
                String line = reader.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] items = splitLine(line, filename, lineNumber);
                if (items != null) {
                    states.add(new Pair<>(items[0], items[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: cannot open the file " + filename
                    + ". Make sure it is in the folder the program is run from.");
            return false;
        }
        if (states.isEmpty()) {
            System.out.println("Error: no valid states were found in " + filename + ".");
            return false;
        }
        return true;
    }

    public static boolean readTrees(ArrayList<Pair<String, Integer>> trees, String filename) {
        try (Scanner reader = new Scanner(new File(filename))) {
            int lineNumber = 0;
            while (reader.hasNextLine()) {
                lineNumber++;
                String line = reader.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] items = splitLine(line, filename, lineNumber);
                if (items == null) {
                    continue;
                }
                try {
                    int height = Integer.parseInt(items[1]);
                    if (height <= 0) {
                        System.out.println("Warning: skipping line " + lineNumber + " of " + filename
                                + ": height must be positive (\"" + line + "\").");
                        continue;
                    }
                    trees.add(new Pair<>(items[0], height));
                } catch (NumberFormatException e) {
                    System.out.println("Warning: skipping line " + lineNumber + " of " + filename
                            + ": height is not a whole number (\"" + line + "\").");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: cannot open the file " + filename
                    + ". Make sure it is in the folder the program is run from.");
            return false;
        }
        if (trees.isEmpty()) {
            System.out.println("Error: no valid trees were found in " + filename + ".");
            return false;
        }
        return true;
    }

    private static String[] splitLine(String line, String filename, int lineNumber) {
        String[] items = line.split("\\|");
        if (items.length != 2 || items[0].trim().isEmpty() || items[1].trim().isEmpty()) {
            System.out.println("Warning: skipping line " + lineNumber + " of " + filename
                    + ": expected the format name|value (\"" + line + "\").");
            return null;
        }
        items[0] = items[0].trim();
        items[1] = items[1].trim();
        return items;
    }
}
