package com.ss.lms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;

@RestController
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;

	@RequestMapping(path = "/borrower/books/checkout", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> checkOutBook(@RequestBody Loans loan) throws SQLException {

		Integer cardNo = null, bookId = null, branchId = null;

		try {
			cardNo = loan.getBorrower().getCardNo();
			bookId = loan.getBook().getBookId();
			branchId = loan.getBranch().getBranchId();
		} catch (Exception e) {
			return new ResponseEntity<Loans>(loan, HttpStatus.BAD_REQUEST);
		}

		if (!(cardNo == null || bookId == null || branchId == null)) {
			Loans l = null;
			try {
				l = borrowerService.checkOutBook(loan);
				return new ResponseEntity<Loans>(l, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Loans>(l, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		return new ResponseEntity<Loans>(loan, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "/borrower/books/return", method = RequestMethod.POST,  produces = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> returnBook(@RequestBody Loans loan) throws SQLException {

		Integer cardNo = null, bookId = null, branchId = null;

		try {
			cardNo = loan.getBorrower().getCardNo();
			bookId = loan.getBook().getBookId();
			branchId = loan.getBranch().getBranchId();
		} catch (Exception e) {
			return new ResponseEntity<Loans>(loan, HttpStatus.BAD_REQUEST);
		}

		if (!(cardNo == null || bookId == null || branchId == null)) {
			Loans l = null;
			try {
				l = borrowerService.returnBook(loan);
				return new ResponseEntity<Loans>(l, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Loans>(l, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		return new ResponseEntity<Loans>(loan, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "/borrower/branches",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranches() {
		try {
			List<Branch> branches = borrowerService.readBranch();
			return new ResponseEntity<List<Branch>>(branches, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity<List<Book>>(new ArrayList<Book>(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(path = "/borrower/books/{branchId}",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooksByBranchID(@PathVariable Integer branchId) {
		try {
			Branch branch = new Branch();
			branch.setBranchId(branchId);
			List<Book> books = borrowerService.getAvailableBooksByBranch(branch);
			if (books.isEmpty()) {
				return new ResponseEntity<List<Book>>(books, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity<List<Book>>(new ArrayList<Book>(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
