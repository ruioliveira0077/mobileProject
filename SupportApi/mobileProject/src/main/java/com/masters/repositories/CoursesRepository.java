package com.masters.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.masters.models.Courses;

public interface CoursesRepository extends CrudRepository<Courses, Long>{
	
	Iterable<Courses> findAll();

    @Query(value = "SELECT * FROM Courses  WHERE user_id=:user_id",  nativeQuery = true)
    Iterable<Courses> getCoursesById(@Param("user_id") Long user_id);
}
