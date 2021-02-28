package day001stackinterpreter;

import java.util.Scanner;
import java.util.Stack;

public class Day001StackInterpreter {

    static Scanner scan = new Scanner(System.in);

    //show menu
    static void showMenu() {
        System.out.println("The following *.txt files have been found in the current directory:");
        System.out.println("addtwo.txt");
        System.out.println("cel2fah.txt");
        System.out.println("fah2cel.txt");

    }

    //addTwo function
    static void addTwo() {
        AddTwo at = new AddTwo();
        at.clearStack();
        while (true) {
            scan.nextLine();
            System.out.print("Please enter a command(read, push, pop, print, add, sub, mul, div,exchange), enter 'quit' to exit the program: ");
            String inputString = scan.nextLine();

            if (inputString.toUpperCase().equals("QUIT")) {
                System.out.println("Exting the program, bye.");
                break;
            }
            switch (inputString.toUpperCase()) {

                case "READ":
                    at.read("Please enter a floating number");
                    break;
                case "PUSH":
                    at.push(123.2);
                    break;
                case "POP":
                    at.pop();
                    break;
                case "PRINT":
                    at.print("The result is: ");
                    break;
                case "ADD":
                    at.add();
                    break;
                case "SUB":
                    at.sub();
                    break;
                case "MUL":
                    at.mul();
                    break;
                case "DIV":
                    at.div();
                    break;
                case "EXCHANGE":
                    at.exchange();
                    break;
                default:
                    break;

            }

        }

    }

    //cel2Fah function
    static void cel2Fah() {
        AddTwo at = new AddTwo();
        at.clearStack();
        at.read("Enter teamperature in Celsius: ");
        at.push(9);
        at.mul();
        at.push(5);
        at.exchange();
        at.div();
        at.push(32);
        at.add();
        at.print("Temperature in Fahrenheit: ");

    }

    //fah2Cel function
    static void fah2Cel() {
        AddTwo at = new AddTwo();
        at.clearStack();
        at.read("Enter teamperature in Fahrenheit: ");
        at.push(32);
        at.exchange();
        at.sub();
        at.push(1.8);
        at.exchange();
        at.div();
        at.print("Temperature in Celsius: ");
        
        
        at.print("Temperature in Fahrenheit: ");
    }

    public static void main(String[] args) {

        showMenu();

        System.out.print("Enter the name of a file to use:");
        String fileToUse = scan.nextLine();

        switch (fileToUse.toUpperCase()) {

            case "ADDTWO.TXT":
                addTwo();
                break;
            case "CEL2FAH.TXT":
                cel2Fah();
                break;
            case "FAH2CEL.TXT":
                fah2Cel();
                break;
            default:
                System.out.println("File not found, exiting the program");
                break;
        }

    }

}
