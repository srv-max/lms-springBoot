package com.ss.lms.entity;

import java.io.Serializable;

public class Branch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1118815487251491971L;
	private Integer branchId;
	private String branchName;
	private String branchAddress;
	
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
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
}
