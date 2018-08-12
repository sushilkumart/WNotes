package com.wander.notes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author sushil Login controller to manage operations like default landing
 *         page and logout operation.
 */
@RestController
public class LoginController {
	
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * Define model and view on logout operation.
	 * 
	 * @return
	 */
	@PostMapping("/logout")
	public ModelAndView getLogOutPage() {
		logger.debug("Getting logout view");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login.html");
		return modelAndView;
	}

	/**
	 * To define the landing page of application.
	 * 
	 * @param response
	 * @throws IOException
	 */

	@GetMapping("/")
	public void getLoginPage(HttpServletResponse response) throws IOException {
		logger.debug("Getting login view.");
		response.sendRedirect("/login");
	}

}
