package com.ss.lms.entity;



import java.lang.annotation.Repeatable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "bookId")
@Entity
@Table(name = "tbl_book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Long bookId;
	
	@Column(name = "title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "pubId")
	private Publisher publisher;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_book_authors",
            joinColumns = @JoinColumn(name = "bookId"),
            		
            inverseJoinColumns = @JoinColumn(name = "authorId")
    )
	private List<Author> authors;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_book_genres",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
	private List<Genre> genres;
	
	/*
	 * @OneToMany private List<Author> authors;
	 * 
	 * @OneToMany private List<Genre> genres;
	 */

	/*
	 * public List<Genre> getGenres() { return genres; }
	 * 
	 * public void setGenres(List<Genre> genres) { this.genres = genres; }
	 */

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
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

	/*
	 * public List<Author> getAuthors() { return authors; }
	 * 
	 * public void setAuthors(List<Author> authors) { this.authors = authors; }
	 */
}
