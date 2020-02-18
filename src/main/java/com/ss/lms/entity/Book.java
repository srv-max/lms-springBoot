package com.ss.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5439836585770906208L;
	private Integer bookId;
	private String title;
	private Publisher publisher;
	private List<Author> authors;
	private List<Genre> genres;
	private List<Branch> libraryBranches;

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<Branch> getLibraryBranches() {
		return libraryBranches;
	}

	public void setLibraryBranches(List<Branch> libraryBranches) {
		this.libraryBranches = libraryBranches;
	}

	// list of genres, branches,
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
}
