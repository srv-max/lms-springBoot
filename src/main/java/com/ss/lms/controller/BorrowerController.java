package com.ss.lms.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.DAO.BookDAO;
import com.ss.lms.DAO.BorrowerDAO;
import com.ss.lms.DAO.BranchDAO;
import com.ss.lms.DAO.CopiesDAO;
import com.ss.lms.DAO.LoansDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.ConnectionUtil;



@RestController
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;
	
	@Autowired
	Branch branch;
	
	@Autowired
	ConnectionUtil connUtil;

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

	@RequestMapping(path = "borrower/{cardNo}/library/{branchId}/books/{bookId}/checkout", method = RequestMethod.POST, produces = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> checkOutBook(@PathVariable("cardNo") Integer cardNo,
            @PathVariable("branchId") Integer branchId,
            @PathVariable("bookId") Integer bookId) throws SQLException {
		
		Borrower borrower =null;
		Book book = null;
		Branch branch =null;
		
		Connection c = null;
		try {
			c = connUtil.connectDatabase();
			
			borrower = brDAO.readByCardNoEssentialData(c,cardNo);
			book = bDAO.readBooksById(c,bookId);
			branch = branchDAO.readBranchsById(c,branchId);
			
			Copies copy = cDAO.readCopyByBranchIDBookID(c,book, branch).get(0);
			Integer noOfCopies = copy.getNoOfCopies();
			
			if (noOfCopies == 0) {
				return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
			}
			
		}
		catch (Exception e) {
			return new ResponseEntity<Loans>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!(borrower == null || book == null || branch == null)) {
			Loans l = null;
			try {
				l = borrowerService.checkOutBook(branch,book,borrower);
				return new ResponseEntity<Loans>(l, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Loans>(l, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		return new ResponseEntity<Loans>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "borrower/{cardNo}/library/{branchId}/books/{bookId}/return", method = RequestMethod.POST,  produces = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> returnBook(@PathVariable("cardNo") Integer cardNo,
            @PathVariable("branchId") Integer branchId,
            @PathVariable("bookId") Integer bookId) throws SQLException {

		Borrower borrower =null;
		Book book = null;
		Branch branch =null;
		
		Connection c = null;
		try {
			c = connUtil.connectDatabase();
			
			borrower = brDAO.readByCardNoEssentialData(c,cardNo);
			book = bDAO.readBooksById(c,bookId);
			branch = branchDAO.readBranchsById(c,branchId);
			
			Loans readLoan = loanDAO.readLoansByBookIdBranchIDCardNo(c,book.getBookId(),
					branch.getBranchId(), borrower.getCardNo());
		
		}
		catch (Exception e) {
			return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
		}

		if (!(cardNo == null || bookId == null || branchId == null)) {
			Loans l = null;
			try {
				l = borrowerService.returnBook(branch, book, borrower);
				return new ResponseEntity<Loans>(l, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Loans>(l, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		return new ResponseEntity<Loans>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "/borrower/branches",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranches() {
		try {
			List<Branch> branches = borrowerService.readBranch();
			return new ResponseEntity<List<Branch>>(branches, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<List<Branch>>(new ArrayList<Branch>(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	@RequestMapping(path = "/borrower/books", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = borrowerService.readBooks();
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<List<Book>>(new ArrayList<Book>(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(path = "/borrower/{branchId}/books",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooksByBranchID(@PathVariable Integer branchId) {
		try {
			
			branch.setBranchId(branchId);
			List<Book> books = borrowerService.getAvailableBooksByBranch(branch);
			if (books.isEmpty()) {
				return new ResponseEntity<List<Book>>(books, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<List<Book>>(new ArrayList<Book>(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
