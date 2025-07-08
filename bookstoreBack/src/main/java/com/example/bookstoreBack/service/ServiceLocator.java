package com.example.bookstoreBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceLocator {

    private static ShippingService shippingService;
    private static MailService mailService;

    @Autowired
    public ServiceLocator(ShippingService shippingService, MailService mailService) {
        ServiceLocator.shippingService = shippingService;
        ServiceLocator.mailService = mailService;
    }

    public static ShippingService getShippingService() {
        return shippingService;
    }

    public static MailService getMailService() {
        return mailService;
    }
}
