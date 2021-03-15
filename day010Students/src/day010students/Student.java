/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010students;

import java.util.Comparator;

/**
 *
 * @author phili
 */
public class Student {

    public Student(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    
    
    
    
    int id;
    String name;
    byte[] image;//other possible types to store a BLOB are: ByteArray, Blob, dataStream

    @Override
    public String toString() {
        return String.format("%d:%s",id,name);
    }
  
    //ref normal comparator class
    public static final StudentNameComparator compareByName = new StudentNameComparator();
    
    
    
    //declare comparator in limbda expression
    //public static final StudentIdComparator compareById = (Student 01, Student 02) -> {o1.id - o2.id};

    
            
//declare comparator in anonymous clas
    
}



//declare comparator in normal Comparator class
class StudentNameComparator implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
       return o1.name.compareTo(o2.name);
    }

}