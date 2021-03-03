/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day002people;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Test;



/**
 *
 * @author phili
 */
public class PersonTest {
    
    /**
     * Test of getName method, of class Person.
     */
    @Test
    public void testGetName() throws InvalidDataException {
        System.out.println("getName");
        Person instance = new Person("Jimmy",12);
        String expResult = "Jimmy";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Person.
     */
    @Test
    public void testSetName() throws Exception {
        System.out.println("setName");
        String name = "Tonny";
        Person instance = new Person("noname",12);
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAge method, of class Person.
     */
    @Test
    public void testGetAge() throws InvalidDataException {
        System.out.println("getAge");
        Person instance = new Person("Tonny",12);
        int expResult = 12;
        int result = instance.getAge();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAge method, of class Person.
     */
    @Test
    public void testSetAge() throws Exception {
        System.out.println("setAge");
        int age = 0;
        Person instance = null;
        instance.setAge(age);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Person.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Person instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
