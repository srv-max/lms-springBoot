package com.ss.lms.entity;

import java.io.Serializable;

public class Genre implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7442442946403625978L;
	private Integer genre_id;
	private String genre_name;

	public Integer getGenre_id() {
		return genre_id;
	}

	public void setGenre_id(Integer genre_id) {
		this.genre_id = genre_id;
	}

	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
}
