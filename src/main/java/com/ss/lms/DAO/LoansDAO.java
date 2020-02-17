package com.ss.lms.DAO;

import java.sql.Date;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Loans;

public class LoansDAO extends BaseDAO {

	public LoansDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addLoans(Book book, Branch branch, Borrower borrower, Date dateOut, Date dueDate)
			throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate)" + " values (?,?,?,?,?)",
				new Object[] { book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut, dueDate});
	}
	
	public void updateDateIn (Book book, Branch branch, Borrower borrower, Date dateIn) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dateIn" + "=? where bookId = ? and branchId = ? and cardNo = ?",
				new Object[]{dateIn, book.getBookId(), branch.getBranchId(), borrower.getCardNo()} );
	}

	public List<Loans> readLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans", null);
	}
	
	

	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Loans> loans = new ArrayList<>();

		// genre doa, branch dao
		while (rs.next()) {
			Loans l = new Loans();
			l.setBookId(rs.getInt("bookId"));
			l.setBranchId(rs.getInt("branchId"));
			l.setCardNo(rs.getInt("cardNo"));
			l.setDateOut(rs.getDate("dateOut").toLocalDate());
			l.setDueDate(rs.getDate("dueDate").toLocalDate());
			l.setDateIn(rs.getDate("dateIn").toLocalDate());
			loans.add(l);
		}
		return loans;
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Loans> loans = new ArrayList<>();

		// genre doa, branch dao
		while (rs.next()) {
			Loans l = new Loans();
			l.setBookId(rs.getInt("bookId"));
			l.setBranchId(rs.getInt("branchId"));
			l.setCardNo(rs.getInt("cardNo"));
			l.setDateOut(rs.getDate("dateOut").toLocalDate());
			l.setDueDate(rs.getDate("dueDate").toLocalDate());
			l.setDateIn(rs.getDate("dateIn").toLocalDate());
			loans.add(l);
		}
		return loans;

	}

}
