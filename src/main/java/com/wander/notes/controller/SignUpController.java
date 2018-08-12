package com.wander.notes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wander.notes.model.User;
import com.wander.notes.services.UserDAO;

/**
 * 
 * @author sushil Controller to define operations like addition of user.
 */

@RestController
public class SignUpController {

	Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Autowired
	private UserDAO userDAO;

	/**
	 * Method to define model and view of user addition.
	 * 
	 * @return
	 */

	@GetMapping("/addUserPage")
	public ModelAndView getAddUserPage() {
		logger.debug("Add user View - start");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addUser.html");
		modelAndView.addObject(new User());
		logger.debug("Add user View - end");
		return modelAndView;
	}

	/**
	 * Method to define to model and view of login page.
	 * 
	 * @return
	 */

	@GetMapping("/login")
	public ModelAndView getLoginPage() {
		logger.debug("Login View function - Start");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login.html");
		logger.debug("Login View function - end");
		return modelAndView;
	}

	/**
	 * Method to serve user add operation request.
	 * 
	 * @param user
	 * @param response
	 * @throws IOException
	 */

	@PostMapping("/add")
	public ModelAndView addUser(@ModelAttribute User user) throws IOException {
		logger.debug("User add function - start");
		if(!(user.getUserName().isEmpty() && user.getPassword().isEmpty())) {
			if(userDAO.addUser(user)) {
				logger.debug("User add function - end");
				ModelAndView modelAndView = new ModelAndView("redirect:/");
				modelAndView.addObject("message", "User Added Succesfully");
				return modelAndView;
			}
			else {
				ModelAndView modelAndView = new ModelAndView("redirect:/addUserPage");
				modelAndView.addObject("message", "User Already Exists");
				return modelAndView;
			}
		}else {
			ModelAndView modelAndView = new ModelAndView("redirect:/addUserPage");
			modelAndView.addObject("message", "User or Password cannot be empty");
			return modelAndView;
		}
	}

}
