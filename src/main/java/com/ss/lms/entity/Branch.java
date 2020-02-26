package com.ss.lms.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Component
@Entity
@Table(name = "tbl_library_branch")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "branchId")
public class Branch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7877691824229470895L;

	@Id
	@Column(name="branchId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	
	@Column(name="branchName", nullable = false)
	private String branchName;
	
	@Column(name="branchAddress", nullable = false)
	private String branchAddress;
	
	@ManyToMany
	@JoinTable(name = "tbl_book_copies",joinColumns = @JoinColumn(name = "branchId"), inverseJoinColumns = @JoinColumn(name = "bookId"))
	private List<Book> books;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
	private List<Copies> copies;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
	private List<Loans> loans;
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Copies> getCopies() {
		return copies;
	}

	public void setCopies(List<Copies> copies) {
		this.copies = copies;
	}

	public List<Loans> getLoans() {
		return loans;
	}

	public void setLoans(List<Loans> loans) {
		this.loans = loans;
	}
	
	
}
