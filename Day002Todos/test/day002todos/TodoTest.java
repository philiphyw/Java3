/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day002todos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author phili
 */
public class TodoTest {
    
    public TodoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTask method, of class Todo.
     */
    @Test
    public void testGetTask() {
        System.out.println("getTask");
        Todo instance = null;
        String expResult = "";
        String result = instance.getTask();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTask method, of class Todo.
     */
    @Test
    public void testSetTask() throws Exception {
        System.out.println("setTask");
        String task = "";
        Todo instance = null;
        instance.setTask(task);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDueDate method, of class Todo.
     */
    @Test
    public void testGetDueDate() {
        System.out.println("getDueDate");
        Todo instance = null;
        Date expResult = null;
        Date result = instance.getDueDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDueDate method, of class Todo.
     */
    @Test
    public void testSetDueDate() throws Exception {
        System.out.println("setDueDate");
        Date dueDate = null;
        Todo instance = null;
        instance.setDueDate(dueDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHoursOfWork method, of class Todo.
     */
    @Test
    public void testGetHoursOfWork() {
        System.out.println("getHoursOfWork");
        Todo instance = null;
        int expResult = 0;
        int result = instance.getHoursOfWork();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHoursOfWork method, of class Todo.
     */
    @Test
    public void testSetHoursOfWork_String() throws Exception {
        System.out.println("setHoursOfWork");
        String HOW = "";
        Todo instance = null;
        instance.setHoursOfWork(HOW);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHoursOfWork method, of class Todo.
     */
    @Test
    public void testSetHoursOfWork_int() throws Exception {
        System.out.println("setHoursOfWork");
        int hoursOfWork = 0;
        Todo instance = null;
        instance.setHoursOfWork(hoursOfWork);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class Todo.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Todo instance = null;
        Todo.TaskStatus expResult = null;
        Todo.TaskStatus result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStatus method, of class Todo.
     */
    @Test
    public void testSetStatus() throws Exception {
        System.out.println("setStatus");
        String status = "";
        Todo instance = null;
        instance.setStatus(status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Todo.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Todo instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDataString method, of class Todo.
     */
    @Test
   
     public void testToDataString() throws ParseException, InvalidDataException{
       System.out.println("toDataString");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2001-10-20");
        Todo instance = new Todo("Run this test", date, 3);
        String expResult = "Run this test;2001-10-20;3;Pending";
        String result = instance.toDataString();
        assertEquals(expResult, result);
    }
    
}
