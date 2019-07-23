/**
 * 
 */
package com.masters.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.masters.models.Courses;
import com.masters.models.Subjects;
/**
 * @author Rui Oliveira
 *
 */
public interface SubjectRepository extends CrudRepository<Subjects, Long>{
	
	Iterable<Subjects> findAll();
	
	 @Query(value = "SELECT * FROM Subjects  WHERE course_id=:course_id",  nativeQuery = true)
	 Iterable<Subjects> subjectByCourse(@Param("course_id") Long course_id);
	 
	 @Query(value = "SELECT * FROM Subjects  WHERE title=:title",  nativeQuery = true)
	 Iterable<Subjects> subjectByTitle(@Param("title") String title);
	
	 
    @Modifying
    @Transactional
    @Query(value="delete from Subjects where id = :id")
    void deleteSubject(Long id);
    
    @Modifying
    @Transactional
    @Query(value = "Update Subjects set title = :title WHERE id=:subject_id",  nativeQuery = true)
    void editSubject(@Param("subject_id") Long subject_id,@Param("title") String title);
}
