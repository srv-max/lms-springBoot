package com.ss.lms.service;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.DAO.BorrowerDAO;
import com.ss.lms.DAO.BranchDAO;
import com.ss.lms.DAO.CopiesDAO;
import com.ss.lms.DAO.LoansDAO;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;
import com.ss.lms.entity.CopiesId;
import com.ss.lms.entity.Loans;
import com.ss.lms.entity.LoansId;

@Component
public class BorrowerService {

	

	@Autowired
	BorrowerDAO brDAO;

	@Autowired
	CopiesDAO cDAO;

	@Autowired
	BookDAO bDAO;

	@Autowired
	BranchDAO branchDAO;

	@Autowired
	LoansDAO loanDAO;

	@Autowired
	Loans loan;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Autowired
	CopiesId copiesId;

	@Autowired
	LoansId loansId;

	

	public BorrowerService() throws ClassNotFoundException {

	}

	public Loans checkOutBook(Integer branchId, Integer bookId, Integer cardNo) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			// updating copies_table
			Optional<Copies> copy = readCopyByBranchIDBookID(bookId, branchId);
			Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;
			entityManager.getTransaction().begin();

			Copies updateCopy = copy.get();
			updateCopy.setNoOfCopies(Long.valueOf(noOfCopies - 1));
			entityManager.merge(updateCopy);

			// updating tbl_book_loans
			Long lBranchId = Long.valueOf(branchId);
			Long lBookId = Long.valueOf(bookId);
			Long lCardNo = Long.valueOf(cardNo);

			LocalDate todayDate = LocalDate.now();
			LocalDate dueDate = todayDate.plusDays(7);

			loansId.setBookId(lBookId);
			loansId.setBranchId(lBranchId);
			loansId.setCardNo(lCardNo);

			loan.setLoansId(loansId);
			loan.setDateOut(todayDate);
			loan.setDueDate(dueDate);

			entityManager.persist(loan);

			entityManager.getTransaction().commit();

		} catch (Exception e) {

			throw e;

		} finally {
			entityManager.close();
		}
		return loan;
	}

	public Loans returnBook(Integer branchId, Integer bookId, Integer cardNo) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		try {
			
		
		// updating copies_table
		Optional<Copies> copy = readCopyByBranchIDBookID(bookId, branchId);
		Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;
		entityManager.getTransaction().begin();

		Copies updateCopy = copy.get();
		updateCopy.setNoOfCopies(Long.valueOf(noOfCopies + 1));
		entityManager.merge(updateCopy);

		/// updating tbl_book_loans
		
		LocalDate returnDate = LocalDate.now();
		loan = readLoansByBookIdBranchIDCardNo(bookId, branchId,cardNo).get();
		
		
		// loanDAO.updateDateIn(c, book, branch, borrower, Date.valueOf(localDate));
		loan.setDateIn(returnDate);
		
		entityManager.merge(loan);
		
		entityManager.getTransaction().commit();

		}
		catch (Exception e){
			throw e;
		}
		finally {
			entityManager.close();
		}
		return loan;

	}

	public List<Branch> readBranch() throws Exception {

		List<Branch> listOfBranchs = null;

		try {
			
			listOfBranchs = branchDAO.findAll();
		} catch (Exception e) {

			throw e;
		} finally {
		
		}

		return listOfBranchs;
	}



	
	public List<Book> getAvailableBooksByBranch(Integer branchId) {
		// TODO Auto-generated method stub
		List<Book> listOfBooks = new ArrayList<>();
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			
			  String sql = "SELECT book FROM Book book INNER JOIN Copies copies " +
			  "ON book.bookId = copies.copiesId.bookId " +
			  "WHERE copies.copiesId.branchId = :branchId and copies.noOfCopies > 0";
			 
			TypedQuery<Book> query
		      = entityManager.createQuery(sql, Book.class)
		      .setParameter("branchId", Long.valueOf(branchId));
			
		    listOfBooks = query.getResultList();
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		
		
		return listOfBooks;
	}

	

	public Optional<Book> readBooksById(Integer bookId) throws Exception {

		Optional<Book> book = null;

		try {

			book = bDAO.findById(Long.valueOf(bookId));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

		}
		return book;
	}

	public Optional<Borrower> readBorrowerByCardNo(Integer cardNo) throws Exception {

		Optional<Borrower> borrower = null;

		try {

			borrower = brDAO.findById(Long.valueOf(cardNo));
		} catch (Exception e) {

			throw e;
		} finally {

		}
		return borrower;

	}

	public Optional<Branch> readBranchsById(Integer branchId) throws Exception {

		Optional<Branch> branch = null;

		try {

			branch = branchDAO.findById(Long.valueOf(branchId));
		} catch (Exception e) {

			throw e;
		} finally {

		}
		return branch;

	}

	public Optional<Loans> readLoansByBookIdBranchIDCardNo(Integer bookId, Integer branchId, Integer cardNo) throws Exception {
		
		Optional<Loans> loans = null;

		try {
			loansId.setBookId(Long.valueOf(bookId));
			loansId.setBranchId((Long.valueOf(branchId)));
			loansId.setCardNo((Long.valueOf(cardNo)));

			loans = loanDAO.findById(loansId);
			// loans = loanDAO.readLoansByBookIdBranchIDCardNo(c,bookId, branchId,
			// cardNo).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			
		}
		return loans;
	}
	

	public Optional<Copies> readCopyByBranchIDBookID(Integer bookId, Integer branchId) {

		Optional<Copies> copies = null;

		try {
			copiesId.setBookId(Long.valueOf(bookId));
			copiesId.setBranchId((Long.valueOf(branchId)));

			copies = cDAO.findById(copiesId);

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		} finally {
			
		}
		return copies;
	}
	
	public List<Book> readBooks() throws SQLException {

		// Connection c = null;
		List<Book> listOfBooks = null;

		try {
			// c = connUtil.connectDatabase();
			listOfBooks = bDAO.findAll();
		} catch (Exception e) {

		} finally {
			// c.close();
		}
		return listOfBooks;
	}
	



	

}
