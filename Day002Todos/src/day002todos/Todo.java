package day002todos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Todo {

    private String task;
    private Date dueDate;
    private int hoursOfWork;
    private TaskStatus status;

    static enum TaskStatus {
        Pending, Done
    }

    // a constructor for new created todo
    public Todo(String task, String dueDate, int hoursOfWork) throws InvalidDataException {
        this.setTask(task);
        this.setDueDate(dueDate);
        this.setHoursOfWork(hoursOfWork);
        //for new create Todo, the status will be set to Pending by default
        this.setStatus("pending");
    }

    // a constructor for modified todo
    public Todo(String task, String dueDate, int hoursOfWork, String status) throws InvalidDataException {
        this.setTask(task);
        this.setDueDate(dueDate);
        this.setHoursOfWork(hoursOfWork);
        this.setStatus(status);
    }
    
        // a constructor to splite dataline and build a new todo
    public Todo(String dataLine) throws InvalidDataException {
        String[] data = dataLine.split(";");
        this.setTask(data[0]);
        this.setDueDate(data[1]);
        this.setHoursOfWork(data[2]);
        if(data[3].equals("0")  || data[3].isEmpty()){
            this.setStatus("pending");
        }else if(data[3].equals("1") ){
            this.setStatus("done");
        }else{
            throw new InvalidDataException("Failed to create new todo, the status ordinal data in dataLine must be 0 or 1");
        }
        
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) throws InvalidDataException {
        // 2-50 characters long, must NOT contain a semicolon or | or ` (reverse single quote) characters
        if (task.length() <= 1 || task.length() > 50 || task.contains(";") || task.contains("|") || task.contains("`")) {
            throw new InvalidDataException("task must contain 2 - 50 characters and must NOT contain a ; (semicolon) or |  or ` (reverse single quote)");
        }
        this.task = task;
    }

    public String getDueDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd") ;
        String formattedDate = df.format(this.dueDate);
        return formattedDate;
    }

    public void setDueDate(String dueDate) throws InvalidDataException {
        // Use SimpleDateFormat to parse String to Date
        DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
        Date tempDate = null;
        try {
            tempDate = df.parse(dueDate);
            // Date between year 1900 and 2100
            int yearOfDue = Integer.parseInt(dueDate.substring(0, 4));
            if (yearOfDue < 1900 || yearOfDue > 2100) {
                throw new InvalidDataException("The year of dueDate must between year 1900 and 2100");
            }
            this.dueDate = tempDate;
        } catch (ParseException e) {
            throw new InvalidDataException("Incorrect date format, the dueDate must be entered in the format 'yyyy/mm/dd'");
        }

    }

    public int getHoursOfWork() {
        return hoursOfWork;
    }

    public void setHoursOfWork(String HOW) throws InvalidDataException {
        try {
            int hoursOfWork = Integer.parseInt(HOW);
            // 0 or greater number
            if (hoursOfWork < 0) {
                throw new InvalidDataException("The hours of work must be 0 or greater.");
            }
            this.hoursOfWork = hoursOfWork;
        } catch (NumberFormatException e) {
            System.out.println(e.toString());
        }

    }
    
        public void setHoursOfWork(int hoursOfWork) throws InvalidDataException {
        try {
            // 0 or greater number
            if (hoursOfWork < 0) {
                throw new InvalidDataException("The hours of work must be 0 or greater.");
            }
            this.hoursOfWork = hoursOfWork;
        } catch (NumberFormatException e) {
            System.out.println(e.toString());
        }

    }
    

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(String status) throws InvalidDataException {
        TaskStatus ts;

        switch (status.toUpperCase()) {

            case "DONE":
                ts = TaskStatus.Done;
                break;
            case "PENDING":
                ts = TaskStatus.Pending;
                break;
            default:
                throw new InvalidDataException("The task satatus must be 'Pending' or 'Done'");
        }
        this.status = ts;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, will take %d hour(s) of work, %s\n", this.getTask(),this.getDueDate(), this.getHoursOfWork(), this.status.toString());
    }
    
        public String toDataString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd") ;
        String formattedDate = df.format(this.dueDate);
        return String.join(";",this.getTask(),this.getDueDate(),String.valueOf(this.getHoursOfWork()),String.valueOf(this.getStatus().ordinal()));
    }

}
