package com.example.BookInventoryManagementSystem.Service.Impl;
import com.example.BookInventoryManagementSystem.Exception.BookNotFoundException;
import com.example.BookInventoryManagementSystem.Exception.OutOfStockException;
import com.example.BookInventoryManagementSystem.Model.Book;
import com.example.BookInventoryManagementSystem.Repository.BookRepository;
import com.example.BookInventoryManagementSystem.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    //Constructor for injecting the BookRepository dependency
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    // Creates a new book and saves it in the database
    @Override
    public Book createBook(Book book){
        logger.info("Creating a New Book: {}", book);
        return bookRepository.save(book);
    }

    // Retrieves all books from the database.
    @Override
    public List<Book> getAllBooks(){
        logger.info("Fetching all books from the database");
        return bookRepository.findAll();
    }

    // Retrieves a book by its ISBN.
    @Override
    public Book getBookByIsbn(String isbn){
        logger.info("Fetching book with ISBN: {}", isbn);
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    // Searches for books by title.
    @Override
    public List<Book> searchBooksByTitle(String title){
        logger.info("Search Book By using title");
        return bookRepository.findByTitle(title);
    }

    // Updates the details of an existing book.
    @Override
    public Book updateBook(String isbn, Book updatedBook){
        logger.info("Updated the book details");
        Book existingBook = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException("book not found"));
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setQuantityInStock(updatedBook.getQuantityInStock());
        existingBook.setPublicationDate(updatedBook.getPublicationDate());

        bookRepository.save(existingBook);
        return existingBook;
    }

    // Delete a book by its ISBN.
    @Override
    public void deleteBook(String isbn){
        logger.info("Delete the book");
        bookRepository.deleteById(isbn);
    }

    // Reduces the available stock if the requested quantity is available.
    @Override
    public void purchaseBook(String isbn, int quantity){
        logger.info("Attempting to purchase copies of book with ISBN");
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException("book not found"));
        if(book.getQuantityInStock() < quantity){
            throw new OutOfStockException("Not enough stock available");
        }
        book.setQuantityInStock(book.getQuantityInStock() - quantity);
        bookRepository.save(book);
        logger.info("Purchase successful");
    }

}
