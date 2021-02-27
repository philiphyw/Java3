package filehandling;

import com.sun.source.tree.WhileLoopTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Filehandling {

    //define the file source
    static final String SOURCE_FILE_NAME = "input.txt";
    static final String Target_FILE_NAME = "duplicate names.txt";
    static ArrayList<String> nameList = new ArrayList<>();
    static ArrayList<Double> numList = new ArrayList<>();
    static ArrayList<String> duplicateNames = new ArrayList<>();

    static void readDataFromFile() {
        // use try(with resource to close resource automatically at the end of the try section)
        try (Scanner fileInput = new Scanner(new File(SOURCE_FILE_NAME))) {
            //NOT to use fileInput.hasNext, use other functions like hasNextLine, hasNextDouble...instead
            while (fileInput.hasNextLine()) { //use nextLine in case there's a space in names
                String line = fileInput.nextLine();
                try {
                    double num = Double.parseDouble(line);
                    numList.add(num);
                } catch (NumberFormatException e) {
                    nameList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The source file doest not exist, please check");
            System.out.println(e.getMessage());
        }
    }

    static void writeDataToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Target_FILE_NAME))) {
            String result = String.join(",", duplicateNames);
            pw.print("Duplicate names: ");
            pw.print(result);
            System.out.println("Following data has been written into the target file: " + result);
            pw.close();
        } catch (Exception e) {
            System.out.println("The target file can not be editted, please check");
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        readDataFromFile();

//        1. Sort names alphabetically.
        Collections.sort(nameList);

//        2. Sort numbers from smallest to largest.
        Collections.sort(numList);

//        3. Display names (sorted) on a single line, comma-separated.
        String names = String.join(",", nameList);
        System.out.print("Names sorted: ");
        System.out.print(names);
        System.out.println();

//        4. Display numbers (sorted) on a single line, comma-separated.
        for (int i = 0; i < numList.size(); i++) {
            String delimiter = (i != 0) ? ", " : "";
            System.out.print(delimiter + numList.get(i));
        }
        System.out.print("Numbers sorted: ");
        System.out.println();

//        5. Compute the average length on names in characters (floating point) and display it.
        int nameLengthTotal = 0;

        for (int i = 0; i < nameList.size(); i++) {
            nameLengthTotal += nameList.get(i).length();
        }

        double nameLengthAvg = (double) nameLengthTotal / nameList.size();
        System.out.print("Average length of names: " + nameLengthAvg);
        System.out.println();

//        6. Find and display any names that occur more than once in the list. Only report each such name once.
        for (int i = 0; i < nameList.size(); i++) {
            if (i < nameList.size() - 1 && i > 0) {
                if (nameList.get(i).equals(nameList.get(i - 1)) && !nameList.get(i).equals(nameList.get(i + 1))) {
                    duplicateNames.add(nameList.get(i));
                }
            } else if (i == nameList.size() - 1) {
                if (nameList.get(i).equals(nameList.get(i - 1))) {
                    duplicateNames.add(nameList.get(i));
                }
            }
        }

//        7. Write the list form item 6 into "dupliates.txt" file, one per line.
        writeDataToFile();

    }

}
