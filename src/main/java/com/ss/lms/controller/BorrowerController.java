package com.ss.lms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;

@RestController
public class BorrowerController {

	@Autowired
	BorrowerService borrowerService;

	@Autowired
	Optional<Branch> branch;

	@RequestMapping(path = "borrower/{cardNo}/library/{branchId}/books/{bookId}:checkout", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> checkOutBook(@PathVariable("cardNo") Integer cardNo,
			@PathVariable("branchId") Integer branchId, @PathVariable("bookId") Integer bookId) throws SQLException {

		Optional<Borrower> borrower = null;
		Optional<Book> book = null;
		Optional<Branch> branch = null;

		try {

			borrower = borrowerService.readBorrowerByCardNo(cardNo);
			book = borrowerService.readBooksById(bookId);
			branch = borrowerService.readBranchsById(branchId);

			if (!(borrower.isPresent() && book.isPresent() && branch.isPresent())) {
				return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
			}

			Optional<Copies> copy = borrowerService.readCopyByBranchIDBookID(bookId, branchId);

			if (copy.isPresent()) {
				Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;
				System.out.println(noOfCopies);
				if (noOfCopies <= 0) {
					return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			return new ResponseEntity<Loans>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!(borrower == null || book == null || branch == null)) {
			Loans l = null;
			try {
				System.out.println("Not null- borrowerController- Checkout");
				l = borrowerService.checkOutBook(branchId, bookId, cardNo);
				return new ResponseEntity<Loans>(l, HttpStatus.ACCEPTED);
			} catch (Exception e) {
				return new ResponseEntity<Loans>(l, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		return new ResponseEntity<Loans>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(path = "borrower/{cardNo}/library/{branchId}/books/{bookId}:return", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> returnBook(@PathVariable("cardNo") Integer cardNo,
			@PathVariable("branchId") Integer branchId, @PathVariable("bookId") Integer bookId) throws SQLException {

		try {

			Optional<Loans> readLoan = borrowerService.readLoansByBookIdBranchIDCardNo(bookId, branchId, cardNo);
			if (readLoan.isPresent()) {
				Loans l = borrowerService.returnBook(branchId, bookId, cardNo);
				return new ResponseEntity<Loans>(l, HttpStatus.ACCEPTED);

			} else {
				return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Loans>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(path = "/borrower/branches/{branchId}/books", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooksByBranchID(@PathVariable Integer branchId) {

		Optional<List<Book>> books = borrowerService.getAvailableBooksByBranch(branchId);

		// branch does not exist
		if (books != null) {
			
			// checking if library has books
			if (!books.get().isEmpty()) {
				return new ResponseEntity<List<Book>>(books.get(), HttpStatus.OK);
			}

		}

		return new ResponseEntity<List<Book>>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(path = "/borrower/branches", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranches() {

		List<Branch> branches = borrowerService.readBranch();
		return new ResponseEntity<List<Branch>>(branches, HttpStatus.OK);

	}

	@RequestMapping(path = "/borrower/books", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {

		List<Book> books = borrowerService.readBooks();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);

	}

}
