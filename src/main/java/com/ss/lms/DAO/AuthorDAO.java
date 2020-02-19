package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;

@Component
public class AuthorDAO extends BaseDAO<Author> {
	/*
	 * public AuthorDAO(Connection conn) { super(conn); }
	 */
	@Autowired
	BookDAO bdao;

	public void addAuthor(Connection conn, Author author) throws ClassNotFoundException, SQLException {
		save(conn,"insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}
	
	public Integer addAuthorReturnPK(Connection conn, Author author) throws ClassNotFoundException, SQLException {
		return saveReturnPk(conn,"insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Connection conn, Author author) throws SQLException, ClassNotFoundException {
		save(conn,"update tbl_author set authorName" + "=? where authorId = ?", new Object[]{author.getAuthorName(),author.getAuthorId()} );
	}

	public void deleteAuthor(Connection conn, Author author) throws ClassNotFoundException, SQLException {
		save(conn,"delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public void insertBookAuthors(Connection conn, Author author, Book book) throws ClassNotFoundException, SQLException{
		save(conn,"insert into tbl_book_authors (bookId,authorId) values (?, ?)", new Object[] {book.getBookId(),author.getAuthorId()});
	}
	
	public List<Author> readAuthors(Connection conn) throws ClassNotFoundException, SQLException {
		return read(conn, "select * from tbl_author", null);
	}
	
	public List<Author> readAuthorsByName(Connection conn, String authorName) throws ClassNotFoundException, SQLException {
		return read(conn, "select * from tbl_authors where authorName = ?", new Object[]{authorName});
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			/*
			 * a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN " +
			 * "(select bookId from tbl_book_authors where authorId = ?)", new
			 * Object[]{a.getAuthorId()}));
			 */
			authors.add(a);
		}
		return authors;
	}
	
	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}
}
