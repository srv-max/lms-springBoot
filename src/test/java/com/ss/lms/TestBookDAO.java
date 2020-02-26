package com.ss.lms;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.entity.Book;

public class TestBookDAO {
	@Autowired
	BookDAO bDAO;
	
	public void testReadBoook() {
		List<Book> listOfBooks = bDAO.findAll();
		Assertions.assertEquals(false,listOfBooks.isEmpty());
	}
	
	
}
