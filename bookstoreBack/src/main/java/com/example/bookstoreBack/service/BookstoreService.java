package com.example.bookstoreBack.service;

import com.example.bookstoreBack.entity.Book;
import com.example.bookstoreBack.entity.PaperBook;
import com.example.bookstoreBack.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookstoreService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        System.out.println("Adding book: " + book.toString());
        return bookRepository.save(book);
    }


    public List<Book> removeOutdatedBooks(int yearsSincePublication) {
        int currentYear = LocalDate.now().getYear();
        int maxYear = currentYear - yearsSincePublication;

        List<Book> outdatedBooks = bookRepository.findOutdatedBooks(maxYear);

        System.out.println(String.format("Removing %d outdated books (published before %d)",
                outdatedBooks.size(), maxYear + 1));

        for (Book book : outdatedBooks) {
            System.out.println("Removing: " + book.toString());
        }

        bookRepository.deleteAll(outdatedBooks);
        return outdatedBooks;
    }


    public BigDecimal buyBook(String isbn, Integer quantity, String email, String address) {
        System.out.println(String.format("Processing purchase request for ISBN: %s, Quantity: %d",
                isbn, quantity));

        Optional<Book> bookOpt = bookRepository.findById(isbn);
        if (!bookOpt.isPresent()) {
            throw new IllegalArgumentException("Book not found with ISBN: " + isbn);
        }

        Book book = bookOpt.get();

        if (!book.isAvailableForSale()) {
            throw new IllegalStateException("Book is not available for sale: " + book.getTitle());
        }

        // For paper books, check and reduce stock
        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            if (paperBook.getStock() < quantity) {
                throw new IllegalStateException(
                        String.format("insufficient stock. Available: %d, Requested: %d",
                                paperBook.getStock(), quantity));
            }
        }

        book.processPurchase(email, address, quantity);

        bookRepository.save(book);

        BigDecimal totalAmount = book.getPrice().multiply(new BigDecimal(quantity));

        System.out.println(String.format("purchase completed. Total amount: %.2fEGP",
                totalAmount.doubleValue()));

        return totalAmount;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }


    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }


    public List<Book> searchByAuthor(String authorName) {
        return bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
    }


    public List<Book> getBooksByType(Class<? extends Book> bookType) {
        return bookRepository.findByBookType(bookType);
    }


    public void printInventory() {
        List<Book> books = getAllBooks();
        System.out.println("Current Inventory:");
        System.out.println("=====================================");

        if (books.isEmpty()) {
            System.out.println("No books in inventory");
        } else {
            books.forEach(book -> System.out.println(book.toString()));
        }

        System.out.println("=====================================");
    }
}