package com.ss.lms.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Publisher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6753974971728375052L;
	private Integer publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	//private List<Book> books;

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherAddress() {
		return publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	/*
	 * public List<Book> getBooks() { return books; }
	 * 
	 * public void setBooks(List<Book> books) { this.books = books; }
	 */
}
