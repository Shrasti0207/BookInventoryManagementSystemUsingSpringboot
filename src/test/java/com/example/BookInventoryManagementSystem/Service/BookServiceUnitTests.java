package com.example.BookInventoryManagementSystem.Service;
import com.example.BookInventoryManagementSystem.Exception.BookNotFoundException;
import com.example.BookInventoryManagementSystem.Exception.OutOfStockException;
import com.example.BookInventoryManagementSystem.Model.Book;
import com.example.BookInventoryManagementSystem.Repository.BookRepository;
import com.example.BookInventoryManagementSystem.Service.Impl.BookServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceUnitTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    private Book book;

    @BeforeEach
    public void setup(){
        book = new Book("123456789", "Test Book", "Test Author", "Fiction",
                109, 10, LocalDate.of(2023, 1, 1));
    }

    @Test
    @Order(1)
    public void createBookTest(){
        given(bookRepository.save(book)).willReturn(book);
        Book savedBook = bookService.createBook(book);
        assertThat(savedBook).isNotNull();
    }

    @Test
    @Order(2)
    public void testGetAllBooks(){
        Book book2 = new Book();
        book2.setIsbn("978-3-16-148410-2");
        book2.setTitle("Python Programming");
        book2.setAuthor("Jane Doe");
        book2.setGenre("Programming");
        book2.setPrice(109);
        book2.setQuantityInStock(5);
        book2.setPublicationDate(LocalDate.of(2022, 8, 10));
        given(bookRepository.findAll()).willReturn(List.of(book, book2));
        List<Book> books = bookService.getAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @Order(3)
    public void testGetBookByIsbn() {
        given(bookRepository.findById(book.getIsbn())).willReturn(Optional.of(book));
        Book foundBook = bookService.getBookByIsbn(book.getIsbn());
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getIsbn()).isEqualTo(book.getIsbn());
    }

    @Test
    @Order(4)
    public void testSearchBooksByTitle() {
        given(bookRepository.findByTitle("Java Programming")).willReturn(List.of(book));
        List<Book> foundBooks = bookService.searchBooksByTitle("Java Programming");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.size()).isEqualTo(1);
    }

    @Test
    @Order(5)
    public void testUpdateBook(){
        given(bookRepository.findById(book.getIsbn())).willReturn(Optional.of(book));
        book.setAuthor("John Lui");
        book.setPrice(200);
        given(bookRepository.save(book)).willReturn(book);
        Book updatedBook = bookService.updateBook(book.getIsbn(), book);
        assertThat(updatedBook.getPrice()).isEqualTo(200);
        assertThat(updatedBook.getAuthor()).isEqualTo("John Lui");
    }

    @Test
    @Order(6)
    public void testDeleteBook(){
        willDoNothing().given(bookRepository).deleteById(book.getIsbn());
        bookService.deleteBook(book.getIsbn());
        verify(bookRepository, times(1)).deleteById(book.getIsbn());
    }

    @Test
    @Order(7)
    public void testPurchaseBook_Success(){
        given(bookRepository.findById(book.getIsbn())).willReturn(Optional.of(book));
        bookService.purchaseBook(book.getIsbn(), 5);
        assertThat(book.getQuantityInStock()).isEqualTo(5);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @Order(8)
    void testGetBookByIsbnBookNotFound() {
        given(bookRepository.findById("999")).willReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("999"));
    }

    @Test
    @Order(9)
    void testOutofStockBookPurchase(){
        given(bookRepository.findById(book.getIsbn())).willReturn(Optional.of(book));
        bookService.purchaseBook(book.getIsbn(), 5);
        assertThrows(OutOfStockException.class, () -> bookService.purchaseBook(book.getIsbn(), 8));
    }
}
