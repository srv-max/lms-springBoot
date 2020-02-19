package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;

public class BookDAO extends BaseDAO<Book> {
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book (title,pubID) values (?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public Integer addBookReturnPK(Book book) throws ClassNotFoundException, SQLException {
		return saveReturnPk("insert into tbl_book (title,pubID) values (?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("update tbl_book set bookName" + "=? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}

	public Book readBooksById(Integer bookId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where bookId = ?", new Object[] { bookId }).get(0);
	}

	public List<Book> readBooksByName(String title) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where title = ?", new Object[] { title });
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenresDAO gdao = new GenresDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		// genre doa, branch dao
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthors(adao.readFirstLevel(
					"select * from tbl_author where authorId IN "
							+ "(select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { b.getBookId() }));
			
			Integer publisherId = rs.getInt("pubID");
			b.setPublisher(pdao.readPublisherById(publisherId));
			
			b.setGenres(gdao.readFirstLevel(
					"select tbl_genre.genre_id as genre_id, tbl_genre.genre_name as genre_name  from tbl_genre inner join tbl_book_genres on tbl_genre.genre_id = tbl_book_genres.genre_id where bookId = ?; ",
					new Object [] {b.getBookId() }));
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		PublisherDAO pdao = new PublisherDAO(conn);
		// genre doa, branch dao
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			
			Integer publisherId = rs.getInt("pubID");
			b.setPublisher(pdao.readPublisherById(publisherId));
           
			books.add(b);
		}
		return books;
	}

}
