package com.ss.lms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.service.ConnectionUtil;

public class TestBookDAO {
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	ConnectionUtil connUtil;
	
	public void testReadBooks () throws SQLException {
		Connection c = null;
		List<Book> listOfBooks = null;

		try {
			c = connUtil.connectDatabase();
			listOfBooks = bDAO.readBooks(c);
		} catch (Exception e) {

		} finally {
			c.close();
		}
		Assertions.assertEquals(false, listOfBooks.isEmpty());
		
	}
}
