package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Genre;


public class GenresDAO extends BaseDAO  {

	public GenresDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	List extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return extractDataFirstLevel(rs);
	}

	@Override
	List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Genre> genres = new ArrayList<>();

		
		while (rs.next()) {
			Genre g = new Genre();
			g.setGenre_id(rs.getInt("genre_id"));
			g.setGenre_name((rs.getString("genre_name")));
			
			genres.add(g);
		}
		return genres;
	}
		
	

}
