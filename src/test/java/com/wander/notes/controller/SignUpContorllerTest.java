package com.wander.notes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wander.notes.WNotesApplicationTests;
import com.wander.notes.model.User;
import com.wander.notes.repository.UserRepo;

import junit.framework.Assert;

public class SignUpContorllerTest extends WNotesApplicationTests {

	@Autowired
    private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Autowired
	private UserRepo userRepo;
	
	@Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	@Test
	public void testGetAddUserPage() throws Exception {
		mockMvc.perform(get("/addUserPage")).andExpect(status().isOk())
		.andExpect(view().name("addUser.html"));
	}
	
	@Test
	public void testLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk())
		.andExpect(view().name("login.html"));
	}
	
	
	@Test
	public void testAddUser() throws Exception {
		User user = new User("junit","junit");
		mockMvc.perform(post("/add").param("userName", user.getUserName()).param("password", user.getPassword())).
		andExpect(redirectedUrl("/?message=User+Added+Succesfully"));
		User dbUser = userRepo.findByUserName("junit");
		Assert.assertEquals(user.getUserName(), dbUser.getUserName());
		userRepo.deleteById(dbUser.getId());
		
	}
}
