package com.ss.lms.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;

@RestController
public class BorrowerController {
	@Autowired
	BorrowerService borrowerService;

	@RequestMapping(path = "/api/books/checkout", method = RequestMethod.POST, headers = {
			"Accept=application/json,application/xml" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<String> checkOutBook(@RequestBody Loans loan) throws SQLException {
		Integer cardNo = loan.getCardNo(), bookId = loan.getBookId(), branchId = loan.getBranchId();

		if (cardNo == null || bookId == null || branchId == null) {
			String message = "Bad Request: Please provide cardNo, bookId, branchId ";
			return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		} else {
			borrowerService.checkOut(cardNo, bookId, branchId);
		}

		String message = "Succesfully checked out book";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/books/return", method = RequestMethod.POST, headers = {
			"Accept=application/json,application/xml" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<String> returnBook(@RequestBody Loans loan) throws SQLException {

		borrowerService.checkOut(loan.getCardNo(), loan.getBookId(), loan.getBranchId());

		String message = "Succesfully returned book";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
