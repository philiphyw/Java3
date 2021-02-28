package day001stackinterpreter;

import static day001stackinterpreter.Day001StackInterpreter.scan;
import java.util.Stack;

public class AddTwo {

    public static Stack<Double> addTwoStack = new Stack<Double>();

    //Initialize (empty) the stack list
    public void clearStack(){
        addTwoStack.clear();
    }
    
    
    //the Read process
    public void read(String msg) {
                    
        
        
        System.out.print(msg);
        double inputDouble = scan.nextDouble();
        addTwoStack.push(inputDouble);
    }

    //the Push process
    public void push(double value) {
        addTwoStack.push(value);
    }

    //the Pop process
    public void pop() {
        checkEmptyStack();
        addTwoStack.pop();
        
    }

    //the Print process
    public void print(String msg) {
        checkEmptyStack();
        double result = addTwoStack.peek();
        System.out.println(msg + result);
    }

    //the Add process
    public void add() {
        checkLessThan2Stack();
        double topValue = addTwoStack.elementAt(addTwoStack.size()-1);
        double secondValue = addTwoStack.elementAt(addTwoStack.size()-2);
//        System.out.println("top value is " + topValue);
//        System.out.println("second value is " + secondValue);
       addTwoStack.push(topValue + secondValue);

    }
    
 //the Sub process
    public void sub() {
        checkLessThan2Stack();
        double topValue = addTwoStack.elementAt(addTwoStack.size()-1);
        double secondValue = addTwoStack.elementAt(addTwoStack.size()-2);
//        System.out.println("top value is " + topValue);
//        System.out.println("second value is " + secondValue);
        addTwoStack.push(topValue - secondValue);

    }
    
    
     //the Mul process
    public void mul() {
        checkLessThan2Stack();
        double topValue = addTwoStack.elementAt(addTwoStack.size()-1);
        double secondValue = addTwoStack.elementAt(addTwoStack.size()-2);
//        System.out.println("top value is " + topValue);
//        System.out.println("second value is " + secondValue);
       addTwoStack.push(topValue * secondValue);

    }
    
         //the Div process
    public void div() {
        checkLessThan2Stack();
        double topValue = addTwoStack.elementAt(addTwoStack.size()-1);
        double secondValue = addTwoStack.elementAt(addTwoStack.size()-2);
//        System.out.println("top value is " + topValue);
//        System.out.println("second value is " + secondValue);
        addTwoStack.push(topValue/secondValue);

    }
    
        //the Div process
        public void exchange() {
        checkLessThan2Stack();
        double topValue = addTwoStack.elementAt(addTwoStack.size()-1);
        double secondValue = addTwoStack.elementAt(addTwoStack.size()-2);
        addTwoStack.pop();
        addTwoStack.pop();
        addTwoStack.push(topValue);
        addTwoStack.push(secondValue);
//        System.out.println("top value is " + topValue);
//        System.out.println("second value is " + secondValue);
        //System.out.printf("The top value %f and second value %f have been exchanged\n", topValue, secondValue);

    }
    
    
    private void checkEmptyStack() {
        if (addTwoStack.empty()) {
            System.out.println("There're no values on the stack, exiting the program");
            System.exit(0);
        }
    }
        
    private void checkLessThan2Stack() {
        if (addTwoStack.size()<2) {
            System.out.println("There're less than 2 values on the stack, exiting the program");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        String result="";
        for (int i = 0; i < addTwoStack.size(); i++) {
            result += addTwoStack.get(i).toString();
        }
        return result;
    }

    
    
}
