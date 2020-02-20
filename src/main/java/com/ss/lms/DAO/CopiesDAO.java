package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;
import com.ss.lms.service.ConnectionUtil;

@Component
public class CopiesDAO extends BaseDAO <Copies> {

	/*
	 * public CopiesDAO(Connection conn) { super(conn); // TODO Auto-generated
	 * constructor stub }
	 */
	@Autowired
	ConnectionUtil connUtil;
	
	@Autowired
	BookDAO bDAO;
	@Autowired
	BranchDAO brDAO;
	public void updateCopies(Connection conn, Book book, Branch branch, Integer noOfCopies) throws ClassNotFoundException, SQLException {
		save(conn, "update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ? ",
				new Object[] { noOfCopies, book.getBookId(), branch.getBranchId()  });
	}
	
	public List<Copies> readCopies(Connection conn) throws ClassNotFoundException, SQLException {
		return read(conn,"select * from tbl_book_copies", null);
	}
	
	public List<Copies> readCopyByBranchIDBookID (Connection conn, Book book, Branch branch) throws ClassNotFoundException, SQLException{
		return read(conn, "select * from tbl_book_copies where bookId = ? and branchId = ?;", new Object[] {
		book.getBookId(),branch.getBranchId()});
		
		
	}
	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Copies> copies = new ArrayList<>();
		Connection conn = connUtil.connectDatabase();
		// genre doa, branch dao
		while (rs.next()) {
			Copies c = new Copies();
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			c.setBookId(rs.getInt("bookId"));
			c.setBranchId(rs.getInt("branchId"));
			c.setBook((Book)(bDAO.readFirstLevel(conn, "select * from tbl_book where bookId IN "
					+ "(select bookId from tbl_book_copies where bookId = ?)",
					new Object[]{c.getBookId()}).get(0)));
			c.setLibraryBranch((Branch)(brDAO.readFirstLevel(conn,"select * from tbl_library_branch where branchId IN "
					+ "(select branchId from tbl_book_copies where branchId = ?)",
					new Object[]{c.getBranchId()}).get(0)));
			copies.add(c);
		}
		return copies;
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Copies> copies = new ArrayList<>();

		// genre doa, branch dao
		while (rs.next()) {
			Copies c = new Copies();
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			c.setBookId(rs.getInt("bookId"));
			c.setBranchId(rs.getInt("branchId"));
			
			copies.add(c);
		}
		return copies;
	}

}
