/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day009todos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author phili
 */
public class Database {
      private static final String dbURL = "jdbc:mysql://localhost:3306/day009todos";
    private static final String username = "root";
    private static final String password = "root";

    private Connection conn;

    //initiate connection while creating a Database instance
    public Database() throws SQLException {
        conn = DriverManager.getConnection(dbURL, username, password);
    }

}
