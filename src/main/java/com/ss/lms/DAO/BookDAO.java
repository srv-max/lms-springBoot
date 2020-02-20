package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.Book;
import com.ss.lms.service.ConnectionUtil;

@Component
public class BookDAO extends BaseDAO<Book> {
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	GenresDAO gdao;
	
	@Autowired
	ConnectionUtil connUtil;
	
	
	/*
	 * public BookDAO(Connection conn) { super(conn); }
	 */

	public void addBook(Connection conn, Book book) throws ClassNotFoundException, SQLException {
		save(conn, "insert into tbl_book (title,pubID) values (?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public Integer addBookReturnPK(Connection conn, Book book) throws ClassNotFoundException, SQLException {
		return saveReturnPk(conn, "insert into tbl_book (title,pubID) values (?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public void updateBook(Connection conn, Book book) throws SQLException, ClassNotFoundException {
		save(conn, "update tbl_book set bookName" + "=? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Connection conn, Book book) throws ClassNotFoundException, SQLException {
		save(conn, "delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readBooks(Connection conn) throws ClassNotFoundException, SQLException {
		return read(conn,"select * from tbl_book", null);
	}

	public Book readBooksById(Connection conn, Integer bookId) throws ClassNotFoundException, SQLException {
		return read(conn,"select * from tbl_book where bookId = ?", new Object[] { bookId }).get(0);
	}

	public List<Book> readBooksByName(Connection conn, String title) throws ClassNotFoundException, SQLException {
		return read(conn, "select * from tbl_book where title = ?", new Object[] { title });
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		Connection conn = connUtil.connectDatabase();
		// genre doa, branch dao
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthors(adao.readFirstLevel(conn,
					"select * from tbl_author where authorId IN "
							+ "(select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { b.getBookId() }));
			
			Integer publisherId = rs.getInt("pubID");
			b.setPublisher(pdao.readPublisherById(conn,publisherId));
			
			b.setGenres(gdao.readFirstLevel(conn,
					"select tbl_genre.genre_id as genre_id, tbl_genre.genre_name as genre_name  from tbl_genre inner join tbl_book_genres on tbl_genre.genre_id = tbl_book_genres.genre_id where bookId = ?; ",
					new Object [] {b.getBookId() }));
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		Connection conn = connUtil.connectDatabase();
		// genre doa, branch dao
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			
			Integer publisherId = rs.getInt("pubID");
			b.setPublisher(pdao.readPublisherById(conn,publisherId));
           
			books.add(b);
		}
		return books;
	}

}
