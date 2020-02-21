package com.ss.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.ConnectionUtil;



@ExtendWith(MockitoExtension.class)
public class TestBorrowerService {
	@InjectMocks
	BorrowerService borrowerService;

	@InjectMocks
	Book book;

	@InjectMocks
	BookDAO bookDAO;
	
	@InjectMocks
	ConnectionUtil connUtil;
	


	@Test
	public void getAllBooksTest() throws ClassNotFoundException, SQLException {
		List<Book> list = new ArrayList<Book>();

		book.setBookId(1);
		list.add(book);
		
		
		//Mockito.when(bookDAO.readBooks(connUtil.connectDatabase())).thenReturn(list);

		// test
		//List<Book> empList = borrowerService.readBooks();

		//Assertions.assertEquals(1, empList.size());
		//Mockito.verify(bookDAO, Mockito.times(1)).readBooks(connUtil.connectDatabase());
	}

}
