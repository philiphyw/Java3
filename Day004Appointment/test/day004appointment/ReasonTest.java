/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day004appointment;

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
public class ReasonTest {
    
    public ReasonTest() {
    }
    

    public void testValues() {
        System.out.println("enum Reason class values");
        Reason[] expResult = new Reason[]{Reason.CHECKUP,Reason.TESTS};
        Reason[] result = new Reason[]{Reason.CHECKUP,Reason.TESTS};
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of valueOf method, of class Reason.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String string = "CHECKUP";
        Reason expResult = Reason.CHECKUP;
        Reason result = Reason.valueOf(string);
        assertEquals(expResult, result);
        
    }
    
}
