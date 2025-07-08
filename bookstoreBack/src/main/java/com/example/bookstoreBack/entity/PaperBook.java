package com.example.bookstoreBack.entity;

import com.example.bookstoreBack.service.ShippingService;
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
@DiscriminatorValue("PAPER")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PaperBook extends Book {

    @Column(nullable = true)
    private Integer stock;

    @Transient
    @Autowired
    private ShippingService shippingService;

    public PaperBook() {
        super();
    }

    public PaperBook(String isbn, String title, String authorName, Integer publishYear,
                     BigDecimal price, Integer stock) {
        super(isbn, title, authorName, publishYear, price);
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null for PaperBook");
        }
        this.stock = stock;
    }

    @Override
    public String getBookType() {
        return "Paper Book";
    }

    @Override
    public boolean isAvailableForSale() {
        return stock != null && stock > 0;
    }

    @Override
    public void processPurchase(String email, String address, Integer quantity) {
        if (stock < quantity) {
            throw new IllegalStateException(
                    String.format("insufficient stock. Available: %d, Wanted: %d",
                            stock, quantity));
        }

        stock -= quantity;
        System.out.println(String.format("paper book purchase: %s, Quantity: %d",
                getTitle(), quantity));


        ServiceLocator.getShippingService().shipBook(this, address, quantity);

    }

    public void reduceStock(Integer quantity) {
        if (stock < quantity) {
            throw new IllegalStateException(
                    String.format("Cannot reduce stock. Available: %d, Wanted: %d",
                            stock, quantity));
        }
        stock -= quantity;
    }

    public Integer getStock() { return stock != null ? stock : 0; }
    public void setStock(Integer stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("Paper Book: %s by %s (%d) - $%.2f [Stock: %d]",
                getTitle(), getAuthorName(), getPublishYear(), getPrice(), stock);
    }
}