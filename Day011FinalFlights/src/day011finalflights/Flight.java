/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day011finalflights;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author phili
 */
public class Flight {

    public Flight(int id, Date onDate, String fromCode, String toCode, Type type, int passengers) {
        this.id = id;
        this.onDate = onDate;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.type = type;
        this.passengers = passengers;
    }

    
    int id;
    Date onDate;
    String fromCode;
    String toCode;
    Type type;
    int passengers;

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(onDate);
        return String.format("%d;%s;%s;%s;%s;%d", id,date,fromCode,toCode,type.toString(),passengers);    
    }


    
    
}

enum Type {
    Domestic,International,Private
};
