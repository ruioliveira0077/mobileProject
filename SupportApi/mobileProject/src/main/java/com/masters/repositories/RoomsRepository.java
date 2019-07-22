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

import com.masters.models.Rooms;

public interface RoomsRepository extends CrudRepository<Rooms, Long>{


	 @Query(value = "SELECT * FROM Rooms",  nativeQuery = true)
	 Iterable<Rooms> allRooms();
}