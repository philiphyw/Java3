/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day009peopledb;

/**
 *
 * @author phili
 */
public class Person {

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    
    int id;
    String name;
    int age;

    @Override
    public String toString() {
       return String.format("id:%d;name:%s;age:%d", id,name,age);
    }
    
    
    
}
