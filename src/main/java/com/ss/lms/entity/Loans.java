package com.ss.lms.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Loans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8268329077429523053L;

	private Branch branch;

	private Book book;
	private Borrower borrower;
	private LocalDate dateOut;
	private LocalDate dueDate;
	private LocalDate dateIn;

	

	public LocalDate getDateOut() {
		return dateOut;
	}

	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getDateIn() {
		return dateIn;
	}

	public void setDateIn(LocalDate dateIn) {
		this.dateIn = dateIn;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}


}
