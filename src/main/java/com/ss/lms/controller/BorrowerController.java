package com.ss.lms.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Copies;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;

@RestController
@RequestMapping("borrower/")
public class BorrowerController {

	@Autowired
	private BorrowerService borrowerService;


	@PostMapping(path = "{cardNo}/branches/{branchId}/books/{bookId}:checkout", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> checkOutBook(@PathVariable("cardNo") Integer cardNo,
			@PathVariable("branchId") Integer branchId, @PathVariable("bookId") Integer bookId) {

		Optional<Borrower> borrower = borrowerService.readBorrowerByCardNo(cardNo);
		Optional<Book> book = borrowerService.readBooksById(bookId);
		Optional<Branch> branch = borrowerService.readBranchsById(branchId);

		if (!(borrower.isPresent() && book.isPresent() && branch.isPresent())) {
			return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
		}

		Optional<Copies> copy = borrowerService.readCopyByBranchIDBookID(bookId, branchId);

		if (copy.isPresent()) {
			Integer noOfCopies = copy.get().getNoOfCopies() != null ? copy.get().getNoOfCopies().intValue() : null;

			if (noOfCopies <= 0) {
				return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
			}

			Loans l = borrowerService.checkOutBook(branchId, bookId, cardNo);
			return new ResponseEntity<Loans>(l, HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "{cardNo}/branches/{branchId}/books/{bookId}:return", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Loans> returnBook(@PathVariable("cardNo") Integer cardNo,
			@PathVariable("branchId") Integer branchId, @PathVariable("bookId") Integer bookId) {

		Optional<Loans> readLoan = borrowerService.readLoansByBookIdBranchIDCardNo(bookId, branchId, cardNo);

		if (readLoan.isPresent()) {

			Loans l = borrowerService.returnBook(branchId, bookId, cardNo);
			return new ResponseEntity<Loans>(l, HttpStatus.ACCEPTED);

		}

		return new ResponseEntity<Loans>(HttpStatus.NOT_FOUND);

	}

	@GetMapping(path = "branches/{branchId}/books", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
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

	@GetMapping(path = "branches", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranches() {

		List<Branch> branches = borrowerService.readBranches();
		return new ResponseEntity<List<Branch>>(branches, HttpStatus.OK);

	}

	@GetMapping(path = "books", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {

		List<Book> books = borrowerService.readBooks();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);

	}

}
