/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day002people;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author phili
 */
public class Day002People {

    static ArrayList<Person> people = new ArrayList<>();
    static final String SOURCE_FILE_NAME = "People.txt";

    static void readDataFromFile() {
        try (Scanner sc = new Scanner(new File(SOURCE_FILE_NAME))) {
            int lineNO=0;
            while (sc.hasNextLine()) {
                try {
                    lineNO++;
                    String line = sc.nextLine();
                    String[] lineData = line.split(";");
                    
                    if (lineData.length != 2) {
                        throw new InvalidDataException("Every line must have 2 fields");
                    }
                    
                    String personName = lineData[0];
                    int personAge = Integer.parseInt(lineData[1]);//ex NumberFormatException
                    people.add(new Person(personName, personAge));
                }catch (NumberFormatException e) {               
                    System.out.printf("Failed to parse integer in the line %d, error detail is: %s\n",lineNO,e.getMessage());
                }catch (IndexOutOfBoundsException e) {               
                    System.out.printf("Missing data in the line %d, error detail is: %s\n",lineNO,e.getMessage());
                }catch (InvalidDataException e) {               
                    System.out.printf("There's an error in the line %d, error detail is: %s\n",lineNO,e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    


    public static void main(String[] args) {
//        Person p1 = new Person("Jim", 30);
//        Person p2 = new Person("Kim", 34);
//        Person p3 = new Person("Sam", 5);

        readDataFromFile();
        
        for( Person person:people){
            System.out.println(person.toString());
        }
        
        
        
    }

}
