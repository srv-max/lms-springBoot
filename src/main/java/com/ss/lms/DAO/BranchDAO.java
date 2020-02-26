package com.ss.lms.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Branch;

@Repository
public interface BranchDAO extends JpaRepository<Branch, Long> {

}
