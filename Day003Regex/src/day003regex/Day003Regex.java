package day003regex;

import java.util.Scanner;

public class Day003Regex {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String test = "abc123adfas22f243d1";
        boolean result1= test.matches(".*[\\d]{2,4}.*");// the key is the ".*" before and after the key pattern, which apply wildcards in the selection. otherwise it must be 100% match to return true
        System.out.println(result1);
  
        while (true) {
            
            System.out.print("Please enter the regex command(enter \"Exit\" to quit the program): ");
            String regexCmd = input.nextLine();
            if (regexCmd.toLowerCase().equals("exit")) {
                System.out.println("Exiting the program, bye~~~");
                break;
            }
            System.out.print("Please enter the to-be-verified text: ");
            String verifyText = input.nextLine();
            
            String result2 = (verifyText.matches(".*" + regexCmd + ".*"))?"The text contains the regex":"The text does NOT contain the regex";
            System.out.println(result2);
          
            
        }
       
    }

}
