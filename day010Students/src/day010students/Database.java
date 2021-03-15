/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phili
 */
public class Database {

    private static final String dbURL = "jdbc:mysql://localhost:3306/day010students";
    private static final String username = "root";
    private static final String password = "root";

    Connection conn;

    //initiate connection while creating a Database instance
    public Database() throws SQLException {
        conn = DriverManager.getConnection(dbURL, username, password);
    }

    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> returnList = new ArrayList<>();
        //Not fetch the image on purpose
        String sql = "SELECT id,name FROM students";
        PreparedStatement statement = conn.prepareStatement(sql);
        //it's a good practice to use try-with-resources for ResultSet so it will be freed up when the process ends, either sucessfully or fail
        try ( ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                //get the result by column name, can also by column no#. like  String name = result.getString(2);
                int id = result.getInt("id");
                String name = result.getString("name");

                //Not fetch the image on purpose
                returnList.add(new Student(id, name, null));
            }

        }
        return returnList;
    }

    //add 
    public void addStudent(Student student) throws SQLException {
        // always use prepareStatement, don't assgin sql to a string like Stirng sql = "SELECT * FROM employee WHERE id=" + inputID; which may expose to injection hacking
        String sql = "INSERT INTO students VALUE(NULL,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, student.name); // Set the 1st parameter(the ? question mark) for the like "INSERT INTO students VALUE(NULL,?,?)"
        statement.setBytes(2, student.image);
        statement.executeUpdate();

    }

    //edit
    public void updateStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students VALUES(NULL,?,?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, student.name);
        statement.setBytes(2, student.image);
        statement.executeUpdate();

        System.out.println("Record inserted");
    }

    //delete
}
