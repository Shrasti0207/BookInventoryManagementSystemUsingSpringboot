package com.example.BookInventoryManagementSystem.Controller;
import com.example.BookInventoryManagementSystem.Model.Book;
import com.example.BookInventoryManagementSystem.Service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController     // Marks this class as a REST controller
@RequestMapping("/books")       // Base URL for all endpoints in this controller
public class BookController {

    private final BookService bookService;
    // Constructor-based dependency injection
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    // Create a new book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book){
        return bookService.createBook(book);
    }

    // Retrieve all books
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    // Retrieve a book by its ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    // Search books by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    // Update book details by ISBN
    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(isbn, book));
    }

    // Delete a book by ISBN
    @DeleteMapping("{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<String>("Book Deleted successfully", HttpStatus.OK);
    }

    // Purchase a book by ISBN, reducing stock quantity
    @PostMapping("/{isbn}/purchase")
    public ResponseEntity<String> purchaseBook(@PathVariable String isbn, @RequestParam int quantity) {
        bookService.purchaseBook(isbn, quantity);
        return ResponseEntity.ok("Purchase successful");
    }
}
