//Techs: Java connect to database, db, MySQL, PreparedStatement, prepareStatement, RecordSet
package day008firstdb;

import java.sql.*;
import java.util.Random;

/**
 *
 * @author phili
 */
public class Day008FirstDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String dbURL = "jdbc:mysql://localhost:3306/day008people";
        String username = "root";
        String password = "root";
        
        
        Random random = new Random();
        
        Connection conn = null;
        //Open database connection
        try {

            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        
        //Insert
        try{
            // always use prepareStatement, don't assgin sql to a string like Stirng sql = "SELECT * FROM employee WHERE id=" + inputID; which may expose to injection hacking
            String sql = "INSERT INTO People VALUE(NULL,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,"Bill "+ random.nextInt(30)); // Set the 1st parameter(the ? question mark) for the like "INSERT INTO People VALUE(NULL,?,?)"
            statement.setString(2,""+ random.nextInt(100));// Set the 2nd parameter(the ? question mark) for the like "INSERT INTO People VALUE(NULL,?,?)"
            statement.executeUpdate();
            System.out.println("Record Inserted");
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        //Select
        try{
            String sql = "SELECT * FROM people";
            PreparedStatement statement = conn.prepareStatement(sql);
            //it's a good practice to use try-with-resources for ResultSet so it will be freed up when the process ends, either sucessfully or fail
            try(ResultSet result = statement.executeQuery(sql)){
                while (result.next()){
                    //get the result by column name, can also by column no#. like  String name = result.getString(2);
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");
                            System.out.printf("%d:%s is %d y/o\n",id,name,age);
                }
            
            }
       
        }catch(SQLException e){
            e.printStackTrace();
        }

        //Update , ALWAYS includes a WHERE on udpate or delete statement
        try{
            int id=random.nextInt(7);
            int newAge = random.nextInt(100) + 100;
            String sql = "UPDATE people SET age =? WHERE id =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, newAge);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Record update id="+id);     
        }catch(SQLException e){
            e.printStackTrace();
        }
        
                //DELETE , ALWAYS includes a WHERE on udpate or delete statement
        try{
            int id=random.nextInt(7);
            String sql = "DELETE from people WHERE id =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Record deleted id="+id);     
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

}
