package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.lms.entity.Branch;

@Component
public class BranchDAO extends BaseDAO<Branch> {
	/*
	 * public BranchDAO(Connection conn) { super(conn); // TODO Auto-generated
	 * constructor stub }
	 */

	
	public List<Branch> readBranchs(Connection conn) throws ClassNotFoundException, SQLException {
		return read(conn,"select * from tbl_library_branch", null);
	}
	
	public Branch readBranchsById(Connection conn,Integer branchId) throws ClassNotFoundException, SQLException {
		return read(conn,"select * from tbl_library_branch where branchId = ?", new Object[] {branchId}).get(0);
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
	
	 public Branch readByBranchIdEssentialData(Connection conn, int branchId) throws SQLException, ClassNotFoundException {
	        List<Branch> b = readFirstLevel(conn, "select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
	       
	        
	        if (b.isEmpty()) {
				return null;
			} else {
				return b.get(0);
			}
	    }

}
