/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day004appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phili
 */
public class AppointmentTest {
   
    @Test
    public void testGetDate() throws ParseException {
        System.out.println("getDate");
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2021-03-15 11:00");
        int durMin = 20;
        String name = "Teddy";
        Reason r1 = Reason.CHECKUP;
        Reason r2 = Reason.TESTS;
        LinkedHashSet<Reason> reasonList = new LinkedHashSet<>();
        reasonList.add(r1);
        reasonList.add(r2);
        Appointment instance = new Appointment(date, durMin, name, reasonList);
        Date expResult = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2021-03-15 11:00");
        Date result = instance.getDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of setDate method, of class Appointment.
     */
    @Test
    public void testSetDate() throws Exception {
        System.out.println("setDate");
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2021-03-15 11:00");;
        Appointment instance = new Appointment();
        instance.setDate(date);
       
    }

    /**
     * Test of getDurMin method, of class Appointment.
     */
    @Test
    public void testGetDurMin() {
        System.out.println("getDurMin");
        Appointment instance = new Appointment();
        int expResult = 0;
        int result = instance.getDurMin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDurMin method, of class Appointment.
     */
    @Test
    public void testSetDurMin() {
        System.out.println("setDurMin");
        int durMin = 0;
        Appointment instance = new Appointment();
        instance.setDurMin(durMin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Appointment.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Appointment instance = new Appointment();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Appointment.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Appointment instance = new Appointment();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReasonList method, of class Appointment.
     */
    @Test
    public void testGetReasonList() {
        System.out.println("getReasonList");
        Appointment instance = new Appointment();
        LinkedHashSet<Reason> expResult = null;
        LinkedHashSet<Reason> result = instance.getReasonList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReasonList method, of class Appointment.
     */
    @Test
    public void testSetReasonList() {
        System.out.println("setReasonList");
        LinkedHashSet<Reason> reasonList = null;
        Appointment instance = new Appointment();
        instance.setReasonList(reasonList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Appointment.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Appointment instance = new Appointment();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDataString method, of class Appointment.
     */
    @Test
    public void testToDataString() {
        System.out.println("toDataString");
        Appointment instance = new Appointment();
        String expResult = "";
        String result = instance.toDataString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
