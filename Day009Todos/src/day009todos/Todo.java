/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day009todos;

import java.util.Date;

/**
 *
 * @author phili
 */
public class Todo {
    int id;
    String task;
    int difficulty;
    Date dueDate; //need a way to handle the conversion between java.util.Date and java.sql.Date in data transferring.
    Status status;
    
    
    
    
    
    enum Status{
    PENDING,DONE,DELEGATED;
    }
}
