package com.example.bookstoreBack.controller;

import com.example.bookstoreBack.entity.Book;
import com.example.bookstoreBack.entity.EBook;
import com.example.bookstoreBack.entity.PaperBook;
import com.example.bookstoreBack.entity.ShowcaseBook;
import com.example.bookstoreBack.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired
    private BookstoreService bookstoreService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookstoreService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> book = bookstoreService.findBookByIsbn(isbn);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/paper")
    public Book addPaperBook(@RequestBody Map<String, Object> bookData) {
        if (bookData.get("stock") == null) {
            throw new IllegalArgumentException("Stock cannot be null.");
        }
        PaperBook book = new PaperBook(
                (String) bookData.get("isbn"),
                (String) bookData.get("title"),
                (String) bookData.get("authorName"),
                (Integer) bookData.get("publishYear"),
                new BigDecimal(bookData.get("price").toString()),
                (Integer) bookData.get("stock")
        );
        return bookstoreService.addBook(book);
    }

    @PostMapping("/ebook")
    public Book addEBook(@RequestBody Map<String, Object> bookData) {
        EBook book = new EBook(
                (String) bookData.get("isbn"),
                (String) bookData.get("title"),
                (String) bookData.get("authorName"),
                (Integer) bookData.get("publishYear"),
                new BigDecimal(bookData.get("price").toString()),
                (String) bookData.get("fileType")
        );
        return bookstoreService.addBook(book);
    }

    @PostMapping("/showcase")
    public Book addShowcaseBook(@RequestBody Map<String, Object> bookData) {
        ShowcaseBook book = new ShowcaseBook(
                (String) bookData.get("isbn"),
                (String) bookData.get("title"),
                (String) bookData.get("authorName"),
                (Integer) bookData.get("publishYear"),
                new BigDecimal(bookData.get("price").toString())
        );
        return bookstoreService.addBook(book);
    }

    @DeleteMapping("/outdated/{years}")
    public ResponseEntity<List<Book>> removeOutdatedBooks(@PathVariable int years) {
        List<Book> removed = bookstoreService.removeOutdatedBooks(years);
        return ResponseEntity.ok(removed);
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> buyBook(@RequestBody Map<String, Object> purchaseData) {
        try {
            String isbn = (String) purchaseData.get("isbn");
            Integer quantity = (Integer) purchaseData.get("quantity");
            String email = (String) purchaseData.get("email");
            String address = (String) purchaseData.get("address");

            BigDecimal totalAmount = bookstoreService.buyBook(isbn, quantity, email, address);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "totalAmount", totalAmount,
                    "message", "Purchase completed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/search/title/{title}")
    public List<Book> searchByTitle(@PathVariable String title) {
        return bookstoreService.searchByTitle(title);
    }

    @GetMapping("/search/author/{author}")
    public List<Book> searchByAuthor(@PathVariable String author) {
        return bookstoreService.searchByAuthor(author);
    }
}
