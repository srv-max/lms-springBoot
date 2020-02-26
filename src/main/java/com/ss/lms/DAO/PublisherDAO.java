package com.ss.lms.DAO;



import com.ss.lms.entity.Publisher;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PublisherDAO extends JpaRepository<Publisher, Long> {

}
