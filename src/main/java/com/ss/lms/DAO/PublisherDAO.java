package com.ss.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Publisher;

@Component
public class PublisherDAO extends BaseDAO<Publisher> {

	/*
	 * public PublisherDAO(Connection conn) { super(conn); // TODO Auto-generated
	 * constructor stub }
	 */

	public Publisher readPublisherById(Connection conn, Integer publisherId) throws ClassNotFoundException, SQLException {
		List<Publisher> publisher = read(conn, "select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });
		if (publisher.isEmpty()) {
			return null;
		} else {
			return publisher.get(0);
		}

	}

	@Override
	List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return extractDataFirstLevel(rs);
	}

	@Override
	List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Publisher> publishers = new ArrayList<>();

		// genre doa, branch dao
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
