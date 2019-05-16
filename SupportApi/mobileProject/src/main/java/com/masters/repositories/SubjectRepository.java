/**
 * 
 */
package com.masters.repositories;

import org.springframework.data.repository.CrudRepository;

import com.masters.models.Subjects;
/**
 * @author Rui Oliveira
 *
 */
public interface SubjectRepository extends CrudRepository<Subjects, Long>{
	
	Iterable<Subjects> findAll();
}
