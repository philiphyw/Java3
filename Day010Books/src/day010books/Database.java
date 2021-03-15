/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010books;

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

    private static final String dbURL = "jdbc:mysql://localhost:3306/day010books";
    private static final String username = "root";
    private static final String password = "root";

    private Connection conn;

    public Database() throws SQLException {
        conn = DriverManager.getConnection(dbURL, username, password);
    }

    //get a list of all records which is WITHOUT the blob field
    public ArrayList<Book> getAllBooks() throws SQLException {
        
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT id, isbn,titleAndAuthor FROM books";
        PreparedStatement statement = conn.prepareStatement(sql);
        // it is a good practice to use try-with-resources for ResultSet so it is freed up as soon as possible
        try ( ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) { // has next row to read
                int id = result.getInt("id");
                String isbn = result.getString("isbn");
                String titleAndAuthor = result.getString("titleAndAuthor");
                // we do not fetch the image here, on purpose
                
                list.add(new Book(id,isbn,titleAndAuthor,null));
            }
        }
        return list;
    }

    //get one single record with the BLOB field
    Book getBookById(int targetId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM books WHERE id=?");
        statement.setInt(1, targetId);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String titleAndAuthor = resultSet.getString("titleAndAuthor");
                byte[] coverImage = resultSet.getBytes("coverImage");
                return new Book(id, isbn,titleAndAuthor,coverImage);
            } else {
                throw new SQLException("Record not found");
            }
        }
    }
    
    
    
    //Add
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books VALUES (NULL, ?, ?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, book.isbn);
        statement.setString(2, book.titleAndAuthor);
        statement.setBytes(3, book.coverImage);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }
    
    
    //Update always includes a WHERE condition
     public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET isbn=?, titleAndAuthor=?,coverImage=? WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, book.isbn);
        statement.setString(2, book.titleAndAuthor);
        statement.setBytes(3, book.coverImage);
        statement.setInt(3, book.id);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }
    
    
    //Delete always includes a WHERE condition
      public void deleteBook(Book book) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, book.isbn);
        statement.setString(2, book.titleAndAuthor);
        statement.setBytes(3, book.coverImage);
        statement.setInt(4, book.id);
        // TODO: handle image
        statement.executeUpdate(); // for insert, update, delete
    }
}
