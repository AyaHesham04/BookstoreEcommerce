package com.example.bookstoreBack.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "bookType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaperBook.class, name = "PAPER"),
        @JsonSubTypes.Type(value = EBook.class, name = "EBOOK"),
        @JsonSubTypes.Type(value = ShowcaseBook.class, name = "SHOWCASE")

})
@Entity
@Table(name = "books")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Book {

    @Id
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private Integer publishYear;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate createdDate;

//    @Column(nullable = true)
//    private Integer stock;

    protected Book() {
        this.createdDate = LocalDate.now();
    }

    public Book(String isbn, String title, String authorName, Integer publishYear, BigDecimal price) {
        this();
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.publishYear = publishYear;
        this.price = price;
    }

    public abstract String getBookType();
    public abstract boolean isAvailableForSale();
    public abstract void processPurchase(String email, String address, Integer quantity);

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Integer getPublishYear() { return publishYear; }
    public void setPublishYear(Integer publishYear) { this.publishYear = publishYear; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }


    @Override
    public String toString() {
        return String.format("%s: %s by %s (%d) - $%.2f",
                getBookType(), title, authorName, publishYear, price);
    }
}