package day004appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Appointment {

    private Date date;
    private int durMin;
    private String name;
    private LinkedHashSet<Reason> reasonList;

    static final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//MM represent monthes, mm represent minutes. HH represent the military hour(17:00), hh represent the normal hour(5:00)
        dateFormat.setLenient(false); //prevent incorrect date data like Feb 30;
    }

    public Appointment() {
    }

    ;
  
    
    public Appointment(Date date, int durMin, String name, LinkedHashSet<Reason> reasons) {
        this.date = date;
        this.durMin = durMin;
        this.name = name;
        this.reasonList = reasons;
    }

    //constructor to create new instance by data string
    public Appointment(String dataString) throws DataInvalidException {
        String[] dataStrs = dataString.split(";");
        try {
            Date date = dateFormat.parse(dataStrs[0]);//ParseException
            this.setDate(date);

            int durMin = Integer.parseInt(dataStrs[1]);//ParseException
            this.setDurMin(durMin);

            String name = dataStrs[2];
            this.setName(name);

            try {
                String[] rsList = dataStrs[3].split(",");//IndexOutOfBounds Exception
                Set<Reason> rsSet = new LinkedHashSet<>();

                //convert string to enum value
                for (int i = 0; i < rsList.length; i++) {
                    String reasonStr = rsList[i];
                    reasonStr = reasonStr.toUpperCase();
                    Reason r = Reason.valueOf(reasonStr);//IllegalArgumentException, user inputted a reason which is not on the Reason enum list

                    rsSet.add(r);
                }
                this.reasonList = (LinkedHashSet<Reason>) rsSet;
            

        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new DataInvalidException("Incorrect reason list in source file: " + dataString);
        }

    }
    catch (ParseException | IndexOutOfBoundsException e) {
            throw new DataInvalidException("Incorrect date or int in source file: " + dataString);
    }
}

public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws DataInvalidException {
        // year, month, day, hours and minutes, appointment can only be made for tomorrow or a later date   
        this.date = date;
    }

    public int getDurMin() {
        return durMin;
    }

    public void setDurMin(int durMin) {
        // setters verify only 20,40,60 is allowed,
        this.durMin = durMin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // regex to allow only uppercase, lowercase, digits, space, -,.()'"
        this.name = name;
    }

    public LinkedHashSet<Reason> getReasonList() {
        return reasonList;
    }

    public void setReasonList(LinkedHashSet<Reason> reasonList) {
        // must not be empty when set (must have at least one reson to visit)
        this.reasonList = reasonList;
    }

    @Override
        public String toString() {
        // Expected outpout: Appointment on 2021-04-22 at 14:20 for 40 minutes for Jerry M. Boe-Eing, reasons: UNWELL,TESTS
        String result = "";

        String dateTimeStr = dateFormat.format(this.date).toString();
        String dateStr = dateTimeStr.split(" ")[0].toString();
        String timeStr = dateTimeStr.split(" ")[1].toString();

        //get the reasons from the LinkedHashSet reasonList to String by the method  private String reasonListToString()
        String reasons = reasonListToString();
        result = String.format("Appointment on %s at %s for %d minutes for %s, reasons:%s", dateStr, timeStr, this.durMin, this.name,reasons);

        return result;

    }

    

    public String toDataString() {
        //Expected output format: "2021-03-15 14:30;20;Jerry Boe;Checkup,Tests,FollowUp"

        //get the reasons from the LinkedHashSet reasonList to String by the method  private String reasonListToString()
        String reasons = reasonListToString();

        //get the date string
        String dateTimeStr = dateFormat.format(this.date);

        return String.format("%s;%d;%s;%s", dateTimeStr, this.getDurMin(), this.getName(), reasons);

    }

    
    
    private String reasonListToString() {

        //get the reasons from the LinkedHashSet reasonList
        String reasons = this.reasonList.toString();

        //replace the symbol [ and ] to match datastring format
        reasons = reasons.replaceAll("[\\[,\\]]", "");

        //replace the white space to comma match datastring format
        String reasonsStr = String.join(",",reasons.split(" "));

        //remove the undesired  space comma at the end of the string
    
                
        return reasonsStr;

    

}
    
}

enum Reason {
    CHECKUP,REFERRAL,TESTS,FOLLOWUP,UNWELL
} // put the enum values in uppercase
