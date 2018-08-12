package com.wander.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wander.notes.model.User;

/**
 * Repository connects with mongoRepository and manages CRUD operations.
 * @author sushil
 *
 */
public interface UserRepo extends MongoRepository<User, String> {

	User findByUserName(String name);
	
	User findByUserNameAndNotesTitle(String name,String title);
}
