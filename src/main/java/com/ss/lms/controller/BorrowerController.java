package com.ss.lms.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Loans;
import com.ss.lms.service.BorrowerService;

@RestController
public class BorrowerController {
	@Autowired
	BorrowerService borrowerService;

	@RequestMapping(path = "/api/books/checkout", method = RequestMethod.POST, headers = {
			"Accept=application/json,application/xml" }, produces = { "application/json", "application/xml" })
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

	@RequestMapping(path = "/api/books/return", method = RequestMethod.POST, headers = {
			"Accept=application/json,application/xml" }, produces = { "application/json", "application/xml" })
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

}
