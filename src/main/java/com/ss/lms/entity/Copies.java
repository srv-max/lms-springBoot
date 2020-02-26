package com.ss.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_book_copies")
@Component
public class Copies implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6483994900241520391L;

	@EmbeddedId
	private CopiesId copiesId;

	@Column(name = "noOfCopies")
	private Long noOfCopies;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId", insertable = false, updatable = false)
	private Book book;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId", insertable = false, updatable = false)
	private Branch branch;

	Copies (){
		
	}
	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	public CopiesId getCopiesId() {
		return copiesId;
	}
	public void setCopiesId(CopiesId copiesId) {
		this.copiesId = copiesId;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}


}