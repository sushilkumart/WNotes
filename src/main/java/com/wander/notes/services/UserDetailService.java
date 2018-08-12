package com.wander.notes.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wander.notes.model.User;
import com.wander.notes.repository.UserRepo;

/**
 * Class to define the UserDetail services.
 * 
 * @author sushil
 *
 */
@Component
public class UserDetailService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserDetailService.class);
	
	@Autowired
	private UserRepo userRepo;

	/**
	 * Method to validate the user at the time of login.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Load by User Name function - start");
		User user = null;
		List<SimpleGrantedAuthority> authorities = null;
		try {
			user = userRepo.findByUserName(username);

			if (user == null) {
				throw new UsernameNotFoundException("User not found");
			}

			authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
			logger.debug("Load by User Name function - end");
		} catch (Exception e) {
			logger.debug("Load by User Name function raised exception"+e);
			e.printStackTrace();
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);

	}

}
