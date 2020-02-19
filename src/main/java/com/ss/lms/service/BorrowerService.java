package com.ss.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import com.ss.lms.entity.Loans;

//                              /api/book/checkout /api/bookCheckout
@Component
public class BorrowerService {
	ConnectionUtil conn;

	public BorrowerService() throws ClassNotFoundException {
		conn = new ConnectionUtil();

	}

	public Loans checkOutBook(Loans loan) throws Exception {
		Connection c = null;

		try {
			c = conn.connectDatabase();

			Borrower borrower = new BorrowerDAO(c).readByCardNoEssentialData(loan.getBorrower().getCardNo());
			loan.setBorrower(borrower);

			Book book = new BookDAO(c).readBooksById(loan.getBook().getBookId());
			loan.setBook(book);

			Branch branch = new BranchDAO(c).readBranchsById(loan.getBranch().getBranchId());
			loan.setBranch(branch);

			Copies copy = new CopiesDAO(c).readCopyByBranchIDBookID(book, branch).get(0);
			Integer noOfCopies = copy.getNoOfCopies();

			// updating tbl_book_copies
			new CopiesDAO(c).updateCopies(book, branch, noOfCopies - 1);

			LocalDate localDate = LocalDate.now();
			LocalDate dueDate = localDate.plusDays(7);
			loan.setDateOut(localDate);
			loan.setDueDate(dueDate);
			// updating tbl_book_loans
			new LoansDAO(c).addLoans(book, branch, borrower, Date.valueOf(localDate), Date.valueOf(dueDate));
			c.commit();
			//System.out.println("Checked out : " + book.getTitle() + " by " + borrower.getName() + " from "
			//		+ branch.getBranchName());
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
			System.err.println("Could not checkout Book");
			throw e;

		} finally {
			c.close();
		}
		return loan;

	}

	public Loans returnBook(Loans loan) throws Exception {
		Connection c = null;
		try {
			c = conn.connectDatabase();
			Borrower borrower = new BorrowerDAO(c).readByCardNoEssentialData(loan.getBorrower().getCardNo());
			loan.setBorrower(borrower);

			Book book = new BookDAO(c).readBooksById(loan.getBook().getBookId());
			loan.setBook(book);

			Branch branch = new BranchDAO(c).readBranchsById(loan.getBranch().getBranchId());
			loan.setBranch(branch);

			Copies copy = new CopiesDAO(c).readCopyByBranchIDBookID(book, branch).get(0);
			Integer noOfCopies = copy.getNoOfCopies();

			// updating tbl_book_copies
			new CopiesDAO(c).updateCopies(book, branch, noOfCopies + 1);
			LocalDate localDate = LocalDate.now();
			// updating tbl_book_loans
			new LoansDAO(c).updateDateIn(book, branch, borrower, Date.valueOf(localDate));
			loan.setDateIn(localDate);
			Loans getDate = new LoansDAO(c).readLoansByBookIdBranchIDCardNo(loan.getBook().getBookId(),
					loan.getBranch().getBranchId(), loan.getBorrower().getCardNo());
			loan.setDateOut(getDate.getDateOut());
			loan.setDueDate(getDate.getDueDate());

			c.commit();
			//System.out.println(localDate.toString() + " Returned " + book.getTitle() + " to " + branch.getBranchName());
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
			System.err.println("Could not return Book");
			throw e;

		}
		return loan;
	}

	public List<Branch> readBranch() throws Exception {

		Connection c = null;
		List<Branch> listOfBranchs = null;
		// System.out.println("The size of branch " + listOfBranchs.size());
		try {
			c = conn.connectDatabase();
			listOfBranchs = new BranchDAO(c).readBranchs();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not get branchs.");
			throw e;
		} finally {
			c.close();
		}

		return listOfBranchs;
	}
	
	public List<Book> getAvailableBooksByBranch(Branch branch) throws SQLException {
		Connection c = null;
		List<Book> listOfBooks = new ArrayList<>();

		try {
			c = conn.connectDatabase();
			List<Copies> listOfCopies = readCopies();
			for (int i = 0; i < listOfCopies.size(); i++) {
				boolean copiesAvailable = listOfCopies.get(i).getNoOfCopies() > 0;
				boolean branchIdMatches = listOfCopies.get(i).getBranchId() == branch.getBranchId();
				if (branchIdMatches && copiesAvailable) {
					listOfBooks.add(listOfCopies.get(i).getBook());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not get borrowers.");
		} finally {
			c.close();
		}
		return listOfBooks;

	}
	
	private List<Copies> readCopies() throws SQLException {
		Connection c = null;
		List<Copies> listOfCopies = null;
		// System.out.println("The size of branch " + listOfBranchs.size());
		try {
			c = conn.connectDatabase();
			listOfCopies = new CopiesDAO(c).readCopies();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not get tbl_book_copies.");
		} finally {
			c.close();
		}

		return listOfCopies;
	}
	
	public List<Book> readBooks() throws SQLException {

		Connection c = null;
		List<Book> listOfBooks = null;

		try {
			c = conn.connectDatabase();
			listOfBooks = new BookDAO(c).readBooks();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not get books.");
		} finally {
			c.close();
		}
		return listOfBooks;
	}

}
