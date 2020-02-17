package com.ss.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Borrower implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6928430757502927949L;
	private Integer cardNo;
	private String name;
	private String address;
	private String phone;
	private List<Book> availableBooks;
	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Book> getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(List<Book> availableBooks) {
		this.availableBooks = availableBooks;
	}
}
