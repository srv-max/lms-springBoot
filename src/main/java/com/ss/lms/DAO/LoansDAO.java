package com.ss.lms.DAO;

import java.sql.Date;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.ConnectionUtil;

@Component
public class LoansDAO extends BaseDAO {

	/*
	 * public LoansDAO(Connection conn) { super(conn); // TODO Auto-generated
	 * constructor stub }
	 */
	@Autowired
	BookDAO bdao;
	
	@Autowired
	BorrowerDAO rowdao;
	
	@Autowired
    BranchDAO branch;
    
	@Autowired
	ConnectionUtil connUtil;

	public void addLoans(Connection conn, Book book, Branch branch, Borrower borrower, Date dateOut, Date dueDate)
			throws ClassNotFoundException, SQLException {
		save(conn,"insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate)" + " values (?,?,?,?,?)",
				new Object[] { book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut, dueDate});
	}
	
	public void updateDateIn (Connection conn, Book book, Branch branch, Borrower borrower, Date dateIn) throws ClassNotFoundException, SQLException {
		save(conn, "update tbl_book_loans set dateIn" + "=? where bookId = ? and branchId = ? and cardNo = ?",
				new Object[]{dateIn, book.getBookId(), branch.getBranchId(), borrower.getCardNo()} );
	}

	public List<Loans> readLoans(Connection conn) throws ClassNotFoundException, SQLException {
		return read(conn, "select * from tbl_book_loans", null);
	}
	public Loans readLoansByBookIdBranchIDCardNo(Connection conn, Integer bookId,Integer branchId, Integer cardNo ) throws ClassNotFoundException, SQLException {
		return (Loans) read(conn, "select * from tbl_book_loans where bookId = ? and branchId = ? and cardNo =? ", new Object[] {bookId,branchId,cardNo}).get(0);
	}
	
	

	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Loans> loans = new ArrayList<>();
		Connection conn = connUtil.connectDatabase();
		// genre doa, branch dao
		while (rs.next()) {
			 Loans l = new Loans();
			
	            l.setBook(bdao.readBooksById(conn,rs.getInt("bookId")));
	            
	            l.setBranch(branch.readByBranchIdEssentialData(conn,rs.getInt("branchId")));
	            
	            l.setBorrower(rowdao.readByCardNoEssentialData(conn,rs.getInt("cardNo")));
	            l.setDateIn(rs.getDate("dateIn").toLocalDate());
	            l.setDateOut(rs.getDate("dateOut").toLocalDate());
	            l.setDueDate(rs.getDate("dueDate").toLocalDate());
	            loans.add(l);
		}
		return loans;
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return extractData(rs);

	}

}
