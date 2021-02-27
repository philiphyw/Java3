
package day002people;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;


public class Person {
    private String name;
    private int age;
    
    Person(String name, int age) throws InvalidDataException{
    this.setName(name);
    this.setAge(age);
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidDataException{
        if (name.length()<2 || name.length() >20 || name.contains(";")) {
            throw new InvalidDataException ("Name must be 2-20 characters long and not contains ';'") ;
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidDataException {
        if (age<0 || age >150) {
            throw new InvalidDataException("Age must be between 0 - 150");
        }
            this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s is %d y/o",getName(),getAge());
    }
    
    
    
}
