package com.ss.lms.DAO;



import com.ss.lms.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookDAO extends JpaRepository<Book, Long> {

}


