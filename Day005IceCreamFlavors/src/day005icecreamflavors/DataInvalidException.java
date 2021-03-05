
package day005icecreamflavors;


public class DataInvalidException extends Exception{
    public DataInvalidException(String msg) {
        super(msg);
    }
    
    public DataInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
