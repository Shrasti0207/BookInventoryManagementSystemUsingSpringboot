package com.example.BookInventoryManagementSystem.Repository;
import com.example.BookInventoryManagementSystem.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository Interface extends JPA Repository for CRUD Operations
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitle(String Title);
}
