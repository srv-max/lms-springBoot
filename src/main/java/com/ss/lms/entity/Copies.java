package com.ss.lms.entity;


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

@Entity
@Table(name = "tbl_book_copies")
@Component
public class Copies {



	@EmbeddedId
	private CopiesId copiesId;
	
	@Column(name="noOfCopies")
	private Long noOfCopies;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId", insertable=false, updatable=false)
    private Book book;
	
	//@ManyToOne
	//private Branch libraryBranch;
	/*
	 * public Book getBook() { return book; }
	 * 
	 * public void setBook(Book book) { this.book = book; }
	 * 
	 * public Branch getLibraryBranch() { return libraryBranch; }
	 * 
	 * public void setLibraryBranch(Branch branch) { this.libraryBranch = branch; }
	 */

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}