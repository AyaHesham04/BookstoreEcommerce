package com.example.bookstoreBack.entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SHOWCASE")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ShowcaseBook extends Book {

    public ShowcaseBook() {
        super();
    }

    public ShowcaseBook(String isbn, String title, String authorName, Integer publishYear,
                        BigDecimal price) {
        super(isbn, title, authorName, publishYear, price);
    }

    @Override
    public String getBookType() {
        return "Showcase Book";
    }

    @Override
    public boolean isAvailableForSale() {
        return false;
    }

    @Override
    public void processPurchase(String email, String address, Integer quantity) {
        throw new UnsupportedOperationException(
                "Quantum book store - Showcase books are not available for purchase");
    }

    @Override
    public String toString() {
        return String.format("Quantum book store - Showcase Book: %s by %s (%d) - $%.2f [NOT FOR SALE]",
                getTitle(), getAuthorName(), getPublishYear(), getPrice());
    }
}