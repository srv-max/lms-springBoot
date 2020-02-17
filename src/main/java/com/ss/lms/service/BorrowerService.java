package com.ss.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.DAO.BorrowerDAO;
import com.ss.lms.DAO.BranchDAO;
import com.ss.lms.DAO.CopiesDAO;
import com.ss.lms.DAO.LoansDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;

//                              /api/book/checkout /api/bookCheckout
@Component
public class BorrowerService {
	ConnectionUtil conn;

	public BorrowerService() throws ClassNotFoundException {
		conn = new ConnectionUtil();

	}

	public void checkOut(Integer cardNo, Integer bookId, Integer branchId) throws SQLException {
		Connection c = null;

		try {
			c = conn.connectDatabase();
			Borrower borrower = new BorrowerDAO(c).readBorrowersById(cardNo).get(0) ;
			Book book = new BookDAO(c).readBooksById(bookId).get(0);
			Branch branch = new BranchDAO(c).readBranchsById(branchId).get(0);

			checkOutBook(borrower, book, branch);
		} catch (Exception e) {

			c.rollback();

			e.printStackTrace();
		} finally {
			c.close();
		}

	}

	public void checkOutBook(Borrower borrower, Book book, Branch branch) throws SQLException {
		Connection c = null;

		try {
			c = conn.connectDatabase();
			Copies copy = new CopiesDAO(c).readCopyByBranchIDBookID(book, branch).get(0);
			Integer noOfCopies = copy.getNoOfCopies();

			// updating tbl_book_copies
			new CopiesDAO(c).updateCopies(book, branch, noOfCopies - 1);

			LocalDate localDate = LocalDate.now();
			LocalDate dueDate = localDate.plusDays(7);

			// updating tbl_book_loans
			new LoansDAO(c).addLoans(book, branch, borrower, Date.valueOf(localDate), Date.valueOf(dueDate));
			c.commit();
			System.out.println("Checked out : " + book.getTitle() + " by " + borrower.getName() + " from "
					+ branch.getBranchName());
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
			System.err.println("Could not checkout Book" + book.toString());
		} finally {
			c.close();
		}

	}
	public void returnProcess (Integer cardNo, Integer bookId, Integer branchId) throws SQLException {
		Connection c = null;

		try {
			c = conn.connectDatabase();
			Borrower borrower = new BorrowerDAO(c).readBorrowersById(cardNo).get(0) ;
			Book book = new BookDAO(c).readBooksById(bookId).get(0);
			Branch branch = new BranchDAO(c).readBranchsById(branchId).get(0);

			returnBook(borrower, book, branch);
		} catch (Exception e) {

			c.rollback();

			e.printStackTrace();
		} finally {
			c.close();
		}
	}
	public void returnBook(Borrower borrower, Book book, Branch branch) throws SQLException {
		Connection c = null;
		try {
			c = conn.connectDatabase();
			Copies copy = new CopiesDAO(c).readCopyByBranchIDBookID(book, branch).get(0);
			Integer noOfCopies = copy.getNoOfCopies();
			
			// updating tbl_book_copies
			new CopiesDAO(c).updateCopies(book, branch, noOfCopies + 1);
			LocalDate localDate = LocalDate.now();
			//updating tbl_book_loans
			new LoansDAO(c).updateDateIn(book, branch, borrower, Date.valueOf(localDate));
			
			c.commit();
			System.out.println(localDate.toString() + " Returned " + book.getTitle() + " to "+ branch.getBranchName());
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
			System.err.println("Could not return Book" + book.toString());
			
		}
	}
}
