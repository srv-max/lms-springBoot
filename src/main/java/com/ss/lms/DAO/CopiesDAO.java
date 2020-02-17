package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;

public class CopiesDAO extends BaseDAO <Copies> {

	public CopiesDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void updateCopies(Book book, Branch branch, Integer noOfCopies) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ? ",
				new Object[] { noOfCopies, book.getBookId(), branch.getBranchId()  });
	}
	
	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies", null);
	}
	
	public List<Copies> readCopyByBranchIDBookID (Book book, Branch branch) throws ClassNotFoundException, SQLException{
		return read("select * from tbl_book_copies where bookId = ? and branchId = ?;", new Object[] {
		book.getBookId(),branch.getBranchId()});
		
		
	}
	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Copies> copies = new ArrayList<>();
		BookDAO bDAO = new BookDAO(conn);
		BranchDAO brDAO = new BranchDAO(conn);
		// genre doa, branch dao
		while (rs.next()) {
			Copies c = new Copies();
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			c.setBookId(rs.getInt("bookId"));
			c.setBranchId(rs.getInt("branchId"));
			c.setBook((Book)(bDAO.readFirstLevel("select * from tbl_book where bookId IN "
					+ "(select bookId from tbl_book_copies where bookId = ?)",
					new Object[]{c.getBookId()}).get(0)));
			c.setLibraryBranch((Branch)(brDAO.readFirstLevel("select * from tbl_library_branch where branchId IN "
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
