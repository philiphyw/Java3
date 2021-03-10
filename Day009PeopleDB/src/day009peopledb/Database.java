/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day009peopledb;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author phili
 */
public class Database {

    private static final String dbURL = "jdbc:mysql://localhost:3306/day008people";
    private static final String username = "root";
    private static final String password = "root";

    private Connection conn;

    public Database() throws SQLException {
        conn = DriverManager.getConnection(dbURL, username, password);
    }

    public ArrayList<Person> getAllPerople() throws SQLException {
        ArrayList<Person> returnList = new ArrayList<>();
        String sql = "SELECT * FROM people";
        PreparedStatement statement = conn.prepareStatement(sql);
        //it's a good practice to use try-with-resources for ResultSet so it will be freed up when the process ends, either sucessfully or fail
        try (ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                //get the result by column name, can also by column no#. like  String name = result.getString(2);
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                returnList.add(new Person(id, name, age));
            }

        }
        return returnList;
    }

    public void AddPerson(Person person)throws SQLException{
         // always use prepareStatement, don't assgin sql to a string like Stirng sql = "SELECT * FROM employee WHERE id=" + inputID; which may expose to injection hacking
            String sql = "INSERT INTO People VALUE(NULL,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,person.name); // Set the 1st parameter(the ? question mark) for the like "INSERT INTO People VALUE(NULL,?,?)"
            statement.setInt(2,person.age);// Set the 2nd parameter(the ? question mark) for the like "INSERT INTO People VALUE(NULL,?,?)"
            statement.executeUpdate();           
        
    }
    
    
        public void UpdatePerson(Person person)throws SQLException{
         //Update , ALWAYS includes a WHERE on udpate or delete statement
            String sql = "UPDATE people SET name = ?, age =? WHERE id =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, person.name);
            statement.setInt(2, person.age);
            statement.setInt(3, person.id);
            statement.executeUpdate();     
        
    }
    
    
}
