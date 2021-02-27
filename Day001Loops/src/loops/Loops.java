package loops;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author phili
 */
public class Loops {

    public static void main(String[] args) {
        ArrayList<String> cars = new ArrayList<>();
        cars.add("Audi");
        cars.add("Benz");
        cars.add("Honda");
        cars.add("Toyota");
        cars.add("Nissan");

        
// Different types of loop

//        1. For loop
        System.out.println("\n\n For Loop:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.print(cars.get(i) + " ");
        }

//        2. Advanced for loop
        System.out.println("\n\n Advanced For Loop:");
        for (String car : cars) {
            System.out.print(car + " ");
        }

//      3. While Loop
        System.out.println("\n\n While Loop:");
        int i = 0;
        while (i < cars.size()) {
            System.out.print(cars.get(i) + " ");
            i++;
            // or just put in one line: System.out.print(cars.get(i++) + " ");
        }

//        4. ListIterator Loop
        ListIterator li = cars.listIterator();
        System.out.println("\n\n ListIterator Loop:");
        while (li.hasNext()) {
            System.out.print(li.next() + " ");
        }

//        5. Java Streams (available after Java 8)
        System.out.println("\n\n Java Streams Loop:");
        cars.forEach(car -> {
            System.out.print(car + " ");
        }
        );

        
        
 // Apply break, continue into loop
        System.out.println("Apply  continue and break in loops: ");
        for (int j = 0; j < 20; j++) {
            if (j % 3 == 0) {
                continue; 
            }else if(j == 17){
                break;
            }
            System.out.println(j);
        }
     
    }

}
