package day004appointment;

import static day004appointment.Appointment.dateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.DataFormatException;

public class Day004Appointment {

    static final String FILE_NAME = "appointment.txt";
    static final Scanner input = new Scanner(System.in);
    static ArrayList<Appointment> apList = new ArrayList<>();

    static void writeDataToFile() {
        try (PrintWriter pw = new PrintWriter(new File(FILE_NAME))) {

            apList.forEach(ap -> {
                pw.println(ap.toDataString());
            });

            System.out.println("Data has been written to the file");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void readDataFromFile() {
        try (Scanner inputFile = new Scanner(new File(FILE_NAME))) {
            while (inputFile.hasNextLine()) {
                try {
                    System.out.println("Reading data from the file");
                    Appointment ap = new Appointment(inputFile.nextLine());//DataInvalidException( icl: ParseException | IndexOutOfBoundsException)
                    apList.add(ap);
                } catch (DataInvalidException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {//it's acceptable to be FileNotFound as the first time to run the program
        }
    }

    static void showMenu() {
        /*
    Main menu:
        1. Make an appointment
        2. List appointments by Date
        3. List appointments by Name
        4. List appointments by their first Reason
        0. Exit
         */
        System.out.println("1. Make an appointment");
        System.out.println("2. List appointments by Date");
        System.out.println("3. List appointments by Name");
        System.out.println("4. List appointments by their first Reason");
        System.out.println("0. Exit");

    }

    static void makeAnAppointment() {

        try {

            System.out.println("Making an appointment");

            System.out.printf("Enter appointment date (yyyy-mm-dd): ");
            String dateStr = input.nextLine(); // ParseException

            System.out.printf("Enter appointment time (hh:mm): ");
            String timeStr = input.nextLine(); // ParseException

            System.out.printf("Enter duration (20, 40, or 60): ");
            int durMin = input.nextInt(); // ParseException
            input.nextLine(); //comsume the empty line in the cache;

            System.out.printf("Enter your name: "); //DataInvalidException
            String name = input.nextLine();

            System.out.println("Possible reasons for visit are Checkup, Referral, Tests, FollowUp, Unwell.");
            System.out.printf("Enter comma-separate list of reasons for visit: "); //DataInvalidException
            String reasons = input.nextLine();
            System.out.println(reasons);
            reasons = reasons.replaceAll("\\s", "");
            System.out.println(reasons);
            //concat all the input into a stirng in dataString format
            String dataString = dateStr + " " + timeStr + ";"
                    + durMin + ";"
                    + name + ";"
                    + reasons;

            Appointment ap = new Appointment(dataString);
            apList.add(ap);

            System.out.println("Appointment created.");
            System.out.println(ap.toString());
        } catch (DataInvalidException e) {
            System.out.println(e.getMessage());
        }

    }

    static void listAppoitments(String sortBy) {
        switch (sortBy) {
            case "Date":
                apList.sort((Appointment a1, Appointment a2) -> a1.getDate().compareTo(a2.getDate()));
                break;
            case "Name":
                apList.sort((Appointment a1, Appointment a2) -> a1.getName().compareTo(a2.getName()));
                break;
            case "First Reason":
                apList.sort((Appointment a1, Appointment a2) -> a1.getReasonList().toString().compareTo(a2.getReasonList().toString()));
                break;
            default:
                break;
        }

        apList.forEach(ap -> {
            System.out.println(ap.toString());
        });

    }

    static int getIntInput(String intInput) throws InputMismatchException, DataInvalidException {
        try {
            int result = Integer.parseInt(intInput);
            return result;
        } catch (InputMismatchException e) {
            throw new DataInvalidException("Please input a integer");
        }
    }

    public static void main(String[] args) {
        //initialize the apList
        apList.clear();
        System.out.println(apList);
        //read existing appointment from the "appointment.txt" file
        readDataFromFile();
        apList.forEach(ap -> {
            System.out.println(ap.toDataString());
        });

        while (true) {
            System.out.println("");
            showMenu();
            try {
                //validate if user enter a valid integer to choose the menu
                System.out.print("Enter your choice:");
                String inputValue = input.nextLine();
                int inputInt = getIntInput(inputValue);

                switch (inputInt) {
                    case 0:
                        System.out.println("Exiting the program, Goodbye.");
                        System.out.println(apList.toString());
                        //write data to file
                        writeDataToFile();
                        System.exit(0);
                    case 1:
                        makeAnAppointment();
                        break;
                    case 2:
                        listAppoitments("Date");
                        break;
                    case 3:
                        listAppoitments("Name");
                        break;
                    case 4:
                        listAppoitments("First Reason");
                        break;

                }

            } catch (DataInvalidException e) {
                System.out.println(e.getMessage());
            }

        }




//        //A block of code to test the conversion from String to Enum Reason
//        Set<Reason> rsSet = new LinkedHashSet<>();
//        String s1 = "tests";
//        s1 = s1.toUpperCase();
//        Reason r1 = Reason.valueOf(s1);
//        System.out.println(r1);
//        rsSet.add(r1);
//
//        String s2 = "unwell";
//        s2 = s2.toUpperCase();
//        Reason r2 = Reason.valueOf(s2);
//        System.out.println(r2);
//        rsSet.add(r2);
//
//        
//        System.out.println(rsSet);
    }

}
