package com.ss.lms.DAO;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Copies;
import com.ss.lms.entity.CopiesId;


@Repository
public interface CopiesDAO extends JpaRepository<Copies, CopiesId> {

}
