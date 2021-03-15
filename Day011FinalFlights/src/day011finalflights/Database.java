/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day011finalflights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author phili
 */
public class Database {

    private static final String dbURL = "jdbc:mysql://localhost:3306/finaldb";
    private static final String username = "root";
    private static final String password = "root";

    private Connection conn;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public Database() throws SQLException {
        conn = DriverManager.getConnection(dbURL, username, password);
    }

    //get a list of all records which is WITHOUT the blob field
    public ArrayList<Flight> getAllFlights() throws SQLException, ParseException {

        ArrayList<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flights ORDER BY onDate";
        PreparedStatement statement = conn.prepareStatement(sql);
        // it is a good practice to use try-with-resources for ResultSet so it is freed up as soon as possible
        try ( ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) { // has next row to read
                int id = resultSet.getInt("id");
                Date onDate = df.parse(resultSet.getString("onDate"));
                String fromCode = resultSet.getString("fromCode");
                String toCode = resultSet.getString("toCode");
                Type type = Type.valueOf(resultSet.getString("type"));
                int passengers = resultSet.getInt("passengers");
                // we do not fetch the image here, on purpose

                list.add(new Flight(id, onDate, fromCode, toCode, type, passengers));
            }
        }
        return list;
    }

    //get one single record with the BLOB field
    Flight getFlightById(int targetId) throws SQLException, ParseException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM flights WHERE id=?");
        statement.setInt(1, targetId);
        try ( ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date onDate = df.parse(resultSet.getString("onDate"));
                String fromCode = resultSet.getString("fromCode");
                String toCode = resultSet.getString("toCode");
                Type type = Type.valueOf(resultSet.getString("type"));
                int passengers = resultSet.getInt("passengers");
                return new Flight(id, onDate, fromCode, toCode, type, passengers);
            } else {
                throw new SQLException("Record not found");
            }
        }
    }

    //Add
    public void addFlight(Flight flight) throws SQLException {
        String sql = "INSERT INTO flights VALUES (NULL,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, df.format(flight.onDate));
        statement.setString(2, flight.fromCode);
        statement.setString(3, flight.toCode);
        statement.setString(4, flight.type.toString());
        statement.setInt(5, flight.passengers);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }

    //Update always includes a WHERE condition
    public void updateFlight(Flight flight) throws SQLException {
        String sql = "UPDATE flights SET onDate=?, fromCode=?,toCode=?, type=?,passengers=? WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, df.format(flight.onDate));
        statement.setString(2, flight.fromCode);
        statement.setString(3, flight.toCode);
        statement.setString(4, flight.type.toString());
        statement.setInt(5, flight.passengers);
        statement.setInt(6, flight.id);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }

    //Delete always includes a WHERE condition
    public void deleteFlight(Flight flight) throws SQLException {
        String sql = "DELETE FROM flights WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, flight.id);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }
}
