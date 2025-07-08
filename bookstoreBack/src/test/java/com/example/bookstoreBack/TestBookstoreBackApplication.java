package com.example.bookstoreBack;

import org.springframework.boot.SpringApplication;

public class TestBookstoreBackApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookstoreBackApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
