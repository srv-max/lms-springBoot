package com.ss.lms.DAO;



import com.ss.lms.entity.Genre;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenresDAO extends JpaRepository<Genre, Long> {

}

