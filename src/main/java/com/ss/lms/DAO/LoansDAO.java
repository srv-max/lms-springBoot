package com.ss.lms.DAO;

import com.ss.lms.entity.Loans;
import com.ss.lms.entity.LoansId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoansDAO extends JpaRepository<Loans, LoansId> {

}

