package com.example.bookstoreBack.service;

import com.example.bookstoreBack.entity.EBook;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public void sendEBook(EBook book, String email, Integer quantity) {
        System.out.println(String.format("Sending %d copies of '%s' (%s) to email: %s",
                quantity, book.getTitle(), book.getFileType(), email));

        // without implementation as mentioned in task
    }
}