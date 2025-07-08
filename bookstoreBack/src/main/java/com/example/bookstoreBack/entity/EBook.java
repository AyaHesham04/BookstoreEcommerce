package com.example.bookstoreBack.entity;

import com.example.bookstoreBack.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import com.example.bookstoreBack.service.ServiceLocator;

@Entity
@DiscriminatorValue("EBOOK")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EBook extends Book {

    @Column(nullable = true)
    private String fileType;

    @Transient
    @Autowired
    private MailService mailService;

    public EBook() {
        super();
    }

    public EBook(String isbn, String title, String authorName, Integer publishYear,
                 BigDecimal price, String fileType) {
        super(isbn, title, authorName, publishYear, price);
        this.fileType = fileType;
    }

    @Override
    public String getBookType() {
        return "EBook";
    }

    @Override
    public boolean isAvailableForSale() {
        return true;
    }

    @Override
    public void processPurchase(String email, String address, Integer quantity) {
        System.out.println(String.format("ebook purchase: %s, Quantity: %d",
                getTitle(), quantity));

        ServiceLocator.getMailService().sendEBook(this, email, quantity);

    }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    @Override
    public String toString() {
        return String.format("EBook: %s by %s (%d) - $%.2f [Format: %s]",
                getTitle(), getAuthorName(), getPublishYear(), getPrice(), fileType);
    }
}
