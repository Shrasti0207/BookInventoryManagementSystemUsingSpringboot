package com.example.BookInventoryManagementSystem.Service;
import com.example.BookInventoryManagementSystem.Model.Book;
import java.util.List;

// Defines the operations related to book management
public interface BookService {

    Book createBook(Book book);
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    List<Book> searchBooksByTitle(String title);
    Book updateBook(String isbn, Book updatedBook);
    void deleteBook(String isbn);
    void purchaseBook(String isbn, int quantity);
}
