package com.example.bookstoreBack.test;

import com.example.bookstoreBack.entity.Book;
import com.example.bookstoreBack.entity.EBook;
import com.example.bookstoreBack.entity.PaperBook;
import com.example.bookstoreBack.entity.ShowcaseBook;
import com.example.bookstoreBack.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class QuantumBookstoreFullTest implements CommandLineRunner {

    @Autowired
    private BookstoreService bookstoreService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("starting full test");
        System.out.println("==============================================");

        // 1 adding books
        testAddingBooks();

        // 2 display current inventory
        bookstoreService.printInventory();

        // 3 buying books
        testBuyingBooks();

        // 4 display inventory after purchases
        bookstoreService.printInventory();

        // 5 removing outdated books
        testRemovingOutdatedBooks();

        // 6 final inventory
        bookstoreService.printInventory();

        System.out.println("test completed");
        System.out.println("=========================================");
    }

    private void testAddingBooks() {
        System.out.println("\ntesting add book");
        System.out.println("------------------------------------------");

//        PaperBook book1 = new PaperBook("978-0-123456-78-9", "Java Programming", "John Smith", 2020, new BigDecimal("45.99"), 10);
//        PaperBook book2 = new PaperBook("978-0-987654-32-1", "Spring Boot Guide", "Jane Doe", 2019, new BigDecimal("52.50"), 5);
//        PaperBook book3 = new PaperBook("978-0-111222-33-4", "Ancient History", "Bob Johnson", 2010, new BigDecimal("30.00"), 3);
//
//        bookstoreService.addBook(book1);
//        bookstoreService.addBook(book2);
//        bookstoreService.addBook(book3);


        EBook ebook1 = new EBook("978-0-555666-77-8", "Machine Learning Basics", "Alice Wilson", 2021, new BigDecimal("35.99"), "PDF");
        EBook ebook2 = new EBook("978-0-444333-22-1", "Web Development", "Charlie Brown", 2022, new BigDecimal("42.75"), "EPUB");

        bookstoreService.addBook(ebook1);
        bookstoreService.addBook(ebook2);

        ShowcaseBook showcase1 = new ShowcaseBook("978-0-888999-00-1", "Demo Book", "Demo Author", 2023, new BigDecimal("0.00"));
        ShowcaseBook showcase2 = new ShowcaseBook("978-0-777888-99-0", "Sample Title", "Sample Writer", 2023, new BigDecimal("0.00"));

        bookstoreService.addBook(showcase1);
        bookstoreService.addBook(showcase2);

        System.out.println("all books added successfully");
    }

    private void testBuyingBooks() {
        System.out.println("\ntesting book purchase");
        System.out.println("--------------------------------------------");

        try {
            BigDecimal amount1 = bookstoreService.buyBook("978-0-123456-78-9", 2, "customer@email.com", "123 Main St, City, State");
            System.out.println("Purchase amount: $" + amount1);

            BigDecimal amount2 = bookstoreService.buyBook("978-0-555666-77-8", 1, "customer@email.com", "Not applicable");
            System.out.println("Purchase amount: $" + amount2);

            BigDecimal amount3 = bookstoreService.buyBook("978-0-987654-32-1", 3, "customer@email.com", "456 Oak Ave, City, State");
            System.out.println("Purchase amount: $" + amount3);

            try {
                bookstoreService.buyBook("978-0-111222-33-4", 5, "customer@email.com", "789 Pine St, City, State");
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }

            try {
                bookstoreService.buyBook("978-0-888999-00-1", 1, "customer@email.com", "Not applicable");
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }

            try {
                bookstoreService.buyBook("978-0-000000-00-0", 1, "customer@email.com", "Not applicable");
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void testRemovingOutdatedBooks() {
        System.out.println("\ntesting outdated book remove");
        System.out.println("---------------------------------------------------");

        List<Book> removedBooks = bookstoreService.removeOutdatedBooks(10);

        System.out.println("Removed " + removedBooks.size() + " outdated books");

        List<Book> removedBooks2 = bookstoreService.removeOutdatedBooks(5);

        System.out.println("Removed " + removedBooks2.size() + " more outdated books");
    }
}