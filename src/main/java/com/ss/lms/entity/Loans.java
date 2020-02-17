package com.ss.lms.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Loans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8268329077429523053L;

	private Integer branchId;

	private Integer bookId;
	private Integer cardNo;
	private LocalDate dateOut;
	private LocalDate dueDate;
	private LocalDate dateIn;

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

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

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

}
