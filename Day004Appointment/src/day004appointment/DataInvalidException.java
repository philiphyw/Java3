/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day004appointment;

/**
 *
 * @author phili
 */
public class DataInvalidException extends Exception{
    public DataInvalidException(String msg) {
        super(msg);
    }
    
    public DataInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
