package day005icecreamflavors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

public class IceCreamOrder {

    private Date deliveryDate;
    private String customerName;
    private ArrayList<Flavour> flavList;

    static final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//MM represent monthes, mm represent minutes. HH represent the military hour(17:00), hh represent the normal hour(5:00)
        dateFormat.setLenient(false); //prevent incorrect date data like Feb 30;
    }

    //default constructor
    public IceCreamOrder() {
    }

    ;
    
    //constructor with paras
    public IceCreamOrder(Date deliveryDate, String customerName, ArrayList<Flavour> flavList) throws DataInvalidException {
        this.setCustomerName(customerName);
        this.setDeliveryDate(deliveryDate);
        this.setFlavList(flavList);
    }

    ;
    
    //constructor with dataString
     public IceCreamOrder(String dataString) throws DataInvalidException {
        String[] dataStrs = dataString.split(";");
        try {
            Date deliveryDate = dateFormat.parse(dataStrs[0]);//ParseException
            this.setDeliveryDate(deliveryDate);

            String customerName = dataStrs[1];
            this.setCustomerName(customerName);

            try {
                String[] inputList = dataStrs[2].split(",");//IndexOutOfBounds Exception
                ArrayList<Flavour> flavList = new ArrayList<>();

                for (int i = 0; i < inputList.length; i++) {
                    String flavourStr = inputList[i];
                    flavourStr = flavourStr.toUpperCase();
                    Flavour f = Flavour.valueOf(flavourStr);//IllegalArgumentException, user inputted a reason which is not on the Reason enum list

                    flavList.add(f);
                }
                this.setFlavList(flavList);

            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DataInvalidException("Incorrect flavour list in source file: " + dataString);
            }

        } catch (ParseException | IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new DataInvalidException("Incorrect flavour list in source file: " + dataString);
        }
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) throws DataInvalidException {
        // 'y-m-d h:m' must be right now or later, and no further than 100 days ahead - get current date/time from new Date()
        Date currentDate = new Date();
        Calendar calDel = Calendar.getInstance();
        Calendar calCur = Calendar.getInstance();
        Calendar calMax = Calendar.getInstance();

        calDel.setTime(deliveryDate);
        calCur.setTime(currentDate);
        calMax.setTime(currentDate);
        calMax.add(Calendar.DATE, 100);

        if (calDel.before(calCur) || calDel.after(calMax)) {
            throw new DataInvalidException("Delivery data must be right now or later, and no further than 100 days ahead");
        }

        this.deliveryDate = deliveryDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) throws DataInvalidException {
        // 2-20 characters, regex to allow only uppercase, lowercase, digits, space, -,.()'"
        if (customerName.matches("^[a-zA-Z 0-9\\-\\.\\(\\)\\'\"]{2,20}$")) {
            this.customerName = customerName;
        } else {
            throw new DataInvalidException("Customer Name only accept 2-20 characters in uppercase, lowercase, digits, space, -,.()'\"");
        }
    }

    public ArrayList<Flavour> getFlavList() {
        return flavList;
    }

    public void setFlavList(ArrayList<Flavour> flavList) throws DataInvalidException {
        // duplicates allowed, but list must not be empty (at least 1 item in it)
        if (flavList.size() <= 0) {
            throw new DataInvalidException("The FlavList must not be empty (at least 1 item in it)");
        }

        this.flavList = flavList;
    }

    public String flavListToString() {
        String result = "";
        if (this.getFlavList().size()>=1) {
            for (Flavour flavour : this.getFlavList()) {
                result += flavour.name().toString() + ",";
            }

            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    @Override
    public String toString() {
        //For Tommy B. Smith, delivery on 2021-03-17 at 11:30, flavours: VANILLA, CHOCOLATE, VANILLA
        String dateTimeStr = dateFormat.format(this.getDeliveryDate()).toString();
        String dateStr = dateTimeStr.split(" ")[0].toString();
        String timeStr = dateTimeStr.split(" ")[1].toString();
        String flavStr = this.flavListToString();

        String result = String.format("For %s, delivery on %s at %s, flavours: %s", this.getCustomerName(), dateStr, timeStr, flavStr);
        return result;
    }

    
    public String toDataString() {


         String flavStr = this.flavListToString();

        //get the date string
        String dateTimeStr = dateFormat.format(this.getDeliveryDate());

        return String.format("%s;%s;%s", dateTimeStr, this.getCustomerName(), flavStr);

    }
    
    
    enum Flavour {
        VANILLA, CHOCOLATE, STRAWBERRY, ROCKYROAD;

        public static boolean isFlavour(String flavStr){
            flavStr= flavStr.toUpperCase();
            int foundKey = -999;
            
            for (Flavour flavour : Flavour.values()) {
                if (flavour.name().equals(flavStr)) {
                     foundKey= flavour.ordinal();
                }
               
            }
            if (foundKey == -999) {
                return false;
            }else{
                return true;
            }
        }
        
        public static String listFlavours() {
            String result = "";

            for (Flavour flavour : Flavour.values()) {
                result += flavour + ",";
            }

            result = result.substring(0, result.length() - 1);
            return result;
        }
    }
}
