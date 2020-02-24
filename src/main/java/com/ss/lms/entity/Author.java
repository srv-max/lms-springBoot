package com.ss.lms.entity;

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
import javax.persistence.Table;


@Entity
@Table(name = "tbl_authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer authorId;
	
	@Column(name = "authorName")
	private String authorName;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_book_authors",
            joinColumns = @JoinColumn(name = "authorId"),
            		
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
	private List<Author> authors;
	

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
