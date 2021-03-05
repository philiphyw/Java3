package day005icecreamflavors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Day005IceCreamFlavors {

    static final String FILE_NAME = "orders.txt";
    static final Scanner input = new Scanner(System.in);
    static ArrayList<IceCreamOrder> orderList = new ArrayList<>();

    static void saveDataToFile() {
        try (PrintWriter pw = new PrintWriter(new File(FILE_NAME))) {//FileNotFoundException

            orderList.forEach(order -> {
                pw.println(order.toDataString());
            });

            
            System.out.printf("\nData saved, total of %d orders. Exiting.\n",orderList.size());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void loadDataFromFile() {
        try (Scanner inputFile = new Scanner(new File(FILE_NAME))) {
            while (inputFile.hasNextLine()) {
                try {
                    IceCreamOrder order = new IceCreamOrder(inputFile.nextLine());//DataInvalidException( icl: ParseException | IndexOutOfBoundsException)
                    orderList.add(order);
                } catch (DataInvalidException e) {
                    System.out.println(e.getMessage());
                }
                
            }
        } catch (FileNotFoundException e) {//it's acceptable to be FileNotFound as the first time to run the program
        }
        
        System.out.printf("\nData loaded from file, total of %d orders.\n",orderList.size());
    }

    static void showMenu() {

        System.out.printf("Welcome to the system\n");
        System.out.println("1. Add an order");
        System.out.println("2. List orders by customer name");
        System.out.println("3. List orders by delivery date");
        System.out.println("0. Exit");

    }

    static void addAnOrder() {

        try {

            System.out.println("Adding an order");

            System.out.printf("Enter your name(2-20 characters, only uppercase, lowercase, digits, space, -,.()'\") :"); //DataInvalidException
            String customerName = input.nextLine();

            System.out.printf("Enter appointment date (yyyy-mm-dd): ");
            String dateStr = input.nextLine(); // ParseException

            System.out.printf("Enter appointment time (hh:mm): ");
            String timeStr = input.nextLine(); // ParseException

            String deliveryDateStr = dateStr + " " + timeStr;

            //List available flavours
            String avaFlav = IceCreamOrder.Flavour.listFlavours();
            System.out.println("Available flavours: " + avaFlav);

            ArrayList<IceCreamOrder.Flavour> flavList = new ArrayList<>();
            //ask customer to input flavours, empty input to exit
            while (true) {
                System.out.printf("Enter which flavour to add, empty to finish: ");
                String inputFlav = input.nextLine();

                if (inputFlav.isEmpty() && flavList.size() <= 0) {
                    System.out.println("There's no flavours have been chosen yet.");
                } else if (inputFlav.isEmpty() && flavList.size() > 0) {
                    break;
                } else {
                    inputFlav = inputFlav.toUpperCase();
                    if (IceCreamOrder.Flavour.isFlavour(inputFlav)) {
                        flavList.add(IceCreamOrder.Flavour.valueOf(inputFlav));
                    } else {
                        System.out.println("No such flavour. Try again.");
                    }
                }
            }
            //create new order    
            IceCreamOrder order = new IceCreamOrder();
            
            //validate user input value bu setters and pass to the new created order
            order.setCustomerName(customerName);

            try {
                //assign values to the new order
                Date deliveryDate = IceCreamOrder.dateFormat.parse(deliveryDateStr);
                order.setDeliveryDate(deliveryDate);
            } catch (ParseException e) {
                throw new DataInvalidException("Error -- Incorrect date time format : " + deliveryDateStr);
            }
            
            order.setFlavList(flavList);
            orderList.add(order);
            System.out.println("Order created:");
            System.out.println(order.toString());
        } catch (DataInvalidException e) {
            System.out.println(e.getMessage());
        }

    }

    static void listOrders(String sortBy) {
        switch (sortBy) {
            case "Date":
                orderList.sort((a1, a2) -> a1.getDeliveryDate().compareTo(a2.getDeliveryDate()));
                break;
            case "Name":
                orderList.sort((a1, a2) -> a1.getCustomerName().compareTo(a2.getCustomerName()));
                break;
            default:
                break;
        }

        orderList.forEach(order -> {
            System.out.println(order.toString());
        });

    }

    static int getIntInput(String intInput) throws InputMismatchException, DataInvalidException, NumberFormatException {
        try {
            int result = Integer.parseInt(intInput);
            return result;
        } catch (InputMismatchException | NumberFormatException e) {
            throw new DataInvalidException("Please input a integer");
        }
    }

    public static void main(String[] args) {
    //initialize the orderList
        orderList.clear();
        //read existing appointment from the "appointment.txt" file
        loadDataFromFile();
        
        while (true) {
            System.out.println("");
            showMenu();
            try {
                //validate if user enter a valid integer to choose the menu
                System.out.print("Please enter your choice:");
                String inputValue = input.nextLine();
                int inputInt = getIntInput(inputValue);

                switch (inputInt) {
                    case 0:
                        System.out.println("Exiting the program, Goodbye.");
                        //write data to file
                        saveDataToFile();
                        System.exit(0);
                    case 1:
                        addAnOrder();
                        break;
                    case 2:
                        listOrders("Date");
                        break;
                    case 3:
                        listOrders("Name");
                        break;
                    default:
                        break;
                }

            } catch (DataInvalidException e) {
                System.out.println(e.getMessage());
            }

        }

     
    }

}
