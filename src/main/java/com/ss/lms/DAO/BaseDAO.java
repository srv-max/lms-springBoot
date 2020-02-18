package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {
	
	protected static Connection conn = null;
	
	public BaseDAO(Connection conn){
		BaseDAO.conn = conn;
	}
	
	protected void save(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		pstmt.executeUpdate();
	}
	
	protected Integer saveReturnPk(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while(rs.next()){
			return rs.getInt(1);
		}
		
		return null;
	}
	
	protected List<T> read(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	abstract List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException;
	
	protected List<T> readFirstLevel(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}
	
	abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException;

}

