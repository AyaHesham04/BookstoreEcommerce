package com.example.bookstoreBack.service;

import com.example.bookstoreBack.entity.PaperBook;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    public void shipBook(PaperBook book, String address, Integer quantity) {
        System.out.println(String.format("Shipping %d copies of '%s' to address: %s",
                quantity, book.getTitle(), address));

        // without implementation as mentioned in task
    }
}
