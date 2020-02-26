package com.ss.lms.DAO;



import com.ss.lms.entity.Author;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorDAO extends JpaRepository<Author, Long> {

}
