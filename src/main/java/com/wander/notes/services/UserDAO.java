package com.wander.notes.services;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.wander.notes.model.User;
import com.wander.notes.repository.UserRepo;

/**
 * Class to manage user related operations.
 * 
 * @author sushil
 *
 */
@Component
public class UserDAO {

	Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	BCryptPasswordEncoder passEncoder;

	/**
	 * Method to add user.
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user) {
		boolean result = false;
		try {
			logger.debug("Add user function - start");
			User exUser = userRepo.findByUserName(user.getUserName());
			if (exUser == null) {
				user.setPassword(passEncoder.encode(user.getPassword()));
				user.setNotes(new HashMap<>());
				userRepo.save(user);
				result = true;
			}
			logger.debug("Add user function - end");

		} catch (Exception e) {
			logger.error("Add user raised an exception" + e);
			e.printStackTrace();
			return false;
		}
		return result;
	}

}
