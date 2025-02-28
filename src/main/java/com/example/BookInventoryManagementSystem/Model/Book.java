package com.example.BookInventoryManagementSystem.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

// Represents a Book entity in the inventory system.
@Entity
public class Book {

    @Id
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int price;
    private int quantityInStock;
    private LocalDate publicationDate;

    //Default constructor for JPA
    public Book() {}

    // Parameterized constructor to initialize a book object.
    public Book(String isbn, String title, String author, String genre, int price, int quantityInStock, LocalDate publicationDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.publicationDate = publicationDate;
    }

    // Getters and setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
