package com.example.BookInventoryManagementSystem;

import com.example.BookInventoryManagementSystem.Controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BookInventoryManagementSystemApplicationTests {

	@Autowired
	private BookController bookController;

	@Test
	void contextLoads() {
		assertThat(bookController).isNotNull();
	}

}
