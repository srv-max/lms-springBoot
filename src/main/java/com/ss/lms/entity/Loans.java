package com.ss.lms.entity;

import java.io.Serializable;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "tbl_book_loans")
@Component
public class Loans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8268329077429523053L;

	@EmbeddedId
	private LoansId loansId;

	@Column(name = "dateOut")
	private LocalDate dateOut;

	@Column(name = "dueDate")
	private LocalDate dueDate;

	@Column(name = "dateIn")
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

	public LoansId getLoansId() {
		return loansId;
	}

	public void setLoansId(LoansId loansId) {
		this.loansId = loansId;
	}
	

}
