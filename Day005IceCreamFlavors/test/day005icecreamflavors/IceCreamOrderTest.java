/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day005icecreamflavors;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class IceCreamOrderTest {
    
    @Test
    public void testConstructorDataLine() throws DataInvalidException {
        String dataLine = "2021-05-01 09:00;Tommya));STRAWBERRY";
        IceCreamOrder instance = new IceCreamOrder(dataLine);
        String expResult = String.format("%s|%s|%s", "Tommya))", "2021-05-01 09:00", "STRAWBERRY");
        String dateTimeStr = IceCreamOrder.dateFormat.format(instance.getDeliveryDate());
        String result = String.format("%s|%s|%s", instance.getCustomerName(),dateTimeStr,instance.flavListToString());
        assertEquals(expResult, result);
    }

    @Test
    public void testSetDeliveryDate1() throws Exception {
        System.out.println("setDeliveryDate1:current date/time + 10 minutes");
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date();
        cal.setTime(curDate);
        cal.add(Calendar.MINUTE, 10);
        Date deliveryDate = cal.getTime();
        IceCreamOrder instance = new IceCreamOrder();
        instance.setDeliveryDate(deliveryDate);

    }

    @Test
    public void testSetDeliveryDate2() throws Exception {
        System.out.println("setDeliveryDate2:current date/time + 99 days");
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date();
        cal.setTime(curDate);
        cal.add(Calendar.DATE, 99);
        Date deliveryDate = cal.getTime();
        IceCreamOrder instance = new IceCreamOrder();
        instance.setDeliveryDate(deliveryDate);

    }

    @Test
    public void testSetDeliveryDate3() throws Exception {
        System.out.println("setDeliveryDate3:current date/time - 5 minutes");
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date();
        cal.setTime(curDate);
        cal.add(Calendar.MINUTE, -5);
        Date deliveryDate = cal.getTime();
        IceCreamOrder instance = new IceCreamOrder();
        instance.setDeliveryDate(deliveryDate);
    }

    @Test
    public void testSetDeliveryDate4() throws Exception {
        System.out.println("setDeliveryDate4:current date/time + 101 days");
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date();
        cal.setTime(curDate);
        cal.add(Calendar.DATE, 101);
        Date deliveryDate = cal.getTime();
        IceCreamOrder instance = new IceCreamOrder();
        instance.setDeliveryDate(deliveryDate);
    }

    @Test
    public void testSetCustomerName1() throws Exception {
        System.out.println("setCustomerName1");
        String customerName = "Tom Curse";
        IceCreamOrder instance = new IceCreamOrder();
        instance.setCustomerName(customerName);

    }

    @Test
    public void testSetCustomerName2() throws Exception {
        System.out.println("setCustomerName2");
        String customerName = "K-121";
        IceCreamOrder instance = new IceCreamOrder();
        instance.setCustomerName(customerName);

    }

    @Test
    public void testSetCustomerName3() throws Exception {
        System.out.println("setCustomerName3");
        String customerName = "asbdckasdlkfjasdklfjasdklfjaskdlfj";
        IceCreamOrder instance = new IceCreamOrder();
        instance.setCustomerName(customerName);

    }

    @Test
    public void testSetCustomerName4() throws Exception {
        System.out.println("setCustomerName4");
        String customerName = "adsf?";
        IceCreamOrder instance = new IceCreamOrder();
        instance.setCustomerName(customerName);

    }

    @Test
    public void testToDataString() throws DataInvalidException, ParseException {
        System.out.println("toDataString");
        IceCreamOrder instance = new IceCreamOrder();
        instance.setCustomerName("Tommya))");
        Date deliveryDate = IceCreamOrder.dateFormat.parse("2021-05-01 09:00");
        instance.setDeliveryDate(deliveryDate);
        IceCreamOrder.Flavour fl = IceCreamOrder.Flavour.STRAWBERRY;
        ArrayList<IceCreamOrder.Flavour> flavList = new ArrayList<>();
        flavList.add(fl);
        instance.setFlavList(flavList);
        String expResult = "2021-05-01 09:00;Tommya));STRAWBERRY";
        String result = instance.toDataString();
        assertEquals(expResult, result);

    }

}
