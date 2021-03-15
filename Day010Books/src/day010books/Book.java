/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010books;

/**
 *
 * @author phili
 */
public class Book {

    public Book(int id, String isbn, String titleAndAuthor, byte[] coverImage) {
        this.id = id;
        this.isbn = isbn;
        this.titleAndAuthor = titleAndAuthor;
        this.coverImage = coverImage;
    }
    
    
    
    int id;
    String isbn;
    String titleAndAuthor;
    byte[] coverImage;

    @Override
    public String toString() {
        return String.format("%d;%s;%s", id,isbn,titleAndAuthor);
    }
    
    
}
