package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;

public class BorrowerDAO  extends BaseDAO {
	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public List<Book> getAvailableBooksByBranch(Branch branch) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return read(
				"select * from tbl_book inner join tbl_book_copies on tbl_book.bookID = tbl_book_copies.bookID   where branchID = ?;",
				new Object[] { branch.getBranchId() });
	}

	public List<Borrower> readBorrowers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower", null);
	}
	
	public Borrower readBorrowersById(Integer cardNo) throws ClassNotFoundException, SQLException {
		return (Borrower) read("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo}).get(0);
	}

	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Borrower> borrowers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			// borrower.setAvailableBooks(bdao.readFirstLevel("select * from tbl_book inner
			// join "
			// + "tbl_book_copies on tbl_book.bookID = tbl_book_copies.bookID where
			// noOfCopies > 0;", null));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Borrower> borrowers = new ArrayList<>();

		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));

			borrowers.add(borrower);
		}
		return borrowers;

	}

	public Borrower readByCardNoEssentialData(Integer cardNo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Borrower> b = readFirstLevel("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
        return b.get(0);
	}
}
