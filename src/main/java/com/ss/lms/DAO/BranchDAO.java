package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch> {
	public BranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {
		save("update tbl_library_branch set branchName =?" + ", branchAddress=? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public List<Branch> readBranchs() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public Branch readBranchsById(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch where branchId = ?", new Object[] {branchId}).get(0);
	}

	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Branch> branches = new ArrayList<>();

		while (rs.next()) {
			Branch b = new Branch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setBranchAddress(rs.getString("branchAddress"));
			branches.add(b);
		}
		return branches;
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Branch> branches = new ArrayList<>();

		while (rs.next()) {
			Branch b = new Branch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setBranchAddress(rs.getString("branchAddress"));
			branches.add(b);
		}
		return branches;

	}
	
	 public Branch readByBranchIdEssentialData(int branchId) throws SQLException, ClassNotFoundException {
	        List<Branch> b = readFirstLevel("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
	        return b.get(0);
	    }

}
