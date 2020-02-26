package com.ss.lms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	private BorrowerDAO borrowerDAO;

	@Autowired
	private CopiesDAO copiesDAO;

	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private BranchDAO branchDAO;

	@Autowired
	private LoansDAO loanDAO;

	@Autowired
	private Loans loan;

	@Autowired
	private CopiesId copiesId;

	@Autowired
	private LoansId loansId;

	public BorrowerService() {

	}
	
	@Transactional
	public Loans checkOutBook(Integer branchId, Integer bookId, Integer cardNo) {

			// updating copies_table
			Optional<Copies> copy = readCopyByBranchIDBookID(bookId, branchId);
			Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;
			
			Copies updateCopy = copy.get();
			updateCopy.setNoOfCopies(Long.valueOf(noOfCopies - 1));
			copiesDAO.save(updateCopy);
			
			// updating tbl_book_loans
			Long lBranchId = Long.valueOf(branchId);
			Long lBookId = Long.valueOf(bookId);
			Long lCardNo = Long.valueOf(cardNo);

			LocalDate todayDate = LocalDate.now();
			LocalDate dueDate = todayDate.plusDays(7);
			
			//setting up loans id
			loansId.setBookId(lBookId);
			loansId.setBranchId(lBranchId);
			loansId.setCardNo(lCardNo);

			loan.setLoansId(loansId);
			loan.setDateOut(todayDate);
			loan.setDueDate(dueDate);
			
			loanDAO.save(loan);

		return loan;
	}
	
	@Transactional
	public Loans returnBook(Integer branchId, Integer bookId, Integer cardNo)  {
		
			// updating copies_table
			Optional<Copies> copy = readCopyByBranchIDBookID(bookId, branchId);
			Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;

			Copies updateCopy = copy.get();
			updateCopy.setNoOfCopies(Long.valueOf(noOfCopies + 1));
			
			/// updating tbl_book_loans
			LocalDate returnDate = LocalDate.now();
			loan = readLoansByBookIdBranchIDCardNo(bookId, branchId, cardNo).get();
			loan.setDateIn(returnDate);

			loanDAO.save(loan);

		return loan;
	}

	public List<Branch> readBranches()  {

		return branchDAO.findAll();
	}

	public Optional<List<Book>> getAvailableBooksByBranch(Integer branchId) {
		Optional<Branch> branch = branchDAO.findById(Long.valueOf(branchId));
		
		if (branch.isPresent()) {
			return Optional.of(branch.get().getBooks());
		}
		
		return null;
	}

	public Optional<Book> readBooksById(Integer bookId) {

		Optional<Book> book = null;

		try {

			book = bookDAO.findById(Long.valueOf(bookId));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

		}
		return book;
	}

	public Optional<Borrower> readBorrowerByCardNo(Integer cardNo)  {

		return borrowerDAO.findById(Long.valueOf(cardNo));

	}

	public Optional<Branch> readBranchsById(Integer branchId) {

		return branchDAO.findById(Long.valueOf(branchId));

	}

	public Optional<Loans> readLoansByBookIdBranchIDCardNo(Integer bookId, Integer branchId, Integer cardNo) {

		loansId.setBookId(Long.valueOf(bookId));
		loansId.setBranchId((Long.valueOf(branchId)));
		loansId.setCardNo((Long.valueOf(cardNo)));

		return loanDAO.findById(loansId);

	}

	public Optional<Copies> readCopyByBranchIDBookID(Integer bookId, Integer branchId) {

		copiesId.setBookId(Long.valueOf(bookId));
		copiesId.setBranchId((Long.valueOf(branchId)));

		return copiesDAO.findById(copiesId);

	}

	public List<Book> readBooks() {

		return bookDAO.findAll();
	}

}
