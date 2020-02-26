package com.ss.lms.DAO;


import com.ss.lms.entity.Borrower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowerDAO extends JpaRepository<Borrower, Long> {

}

