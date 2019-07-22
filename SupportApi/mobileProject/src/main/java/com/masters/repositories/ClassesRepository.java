package com.masters.repositories;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.masters.models.Classes;

public interface ClassesRepository extends CrudRepository<Classes, Long>{


	 @Query(value = "SELECT * FROM Classes LEFT Join Rooms on Classes.room_id = Rooms.id WHERE subject_id=:subject_id",  nativeQuery = true)
	 Iterable<Classes> classesBySubject(@Param("subject_id") Long subject_id);
	 
	 @Query(value = "SELECT * FROM Classes LEFT Join Rooms on Classes.room_id = Rooms.id "+
	 "LEFT Join Subjects on Classes.subject_id = Subjects.id "+
	 "Left Join Courses on Subjects.course_id = Courses.id "+
	 " WHERE dateToCompare = :date And Courses.user_id = :user_id",  nativeQuery = true)
	 Iterable<Classes> classesByDate(@Param("date")String date, @Param("user_id")Long user_id );
	 
	 @Query(value = "SELECT * FROM Classes LEFT Join Rooms on Classes.room_id = Rooms.id "+
	 "LEFT Join Subjects on Classes.subject_id = Subjects.id "+
	 "Left Join Courses on Subjects.course_id = Courses.id "+
	 " WHERE dateToCompare = :date And Courses.user_id = :user_id and Subjects.id = :subject_id",  nativeQuery = true)
	 Iterable<Classes> classesByDateAndSubject(@Param("subject_id")Long subject_id ,@Param("date")String date, @Param("user_id")Long user_id );
	 
    @Modifying
    @Transactional
    @Query(value="delete from Classes where id = :id")
    void deleteClass(Long id);
}
