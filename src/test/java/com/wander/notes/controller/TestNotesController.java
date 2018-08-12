package com.wander.notes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wander.notes.WNotesApplicationTests;
import com.wander.notes.model.Notes;
import com.wander.notes.model.User;
import com.wander.notes.repository.UserRepo;

public class TestNotesController extends WNotesApplicationTests {

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
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testGetAddPage() throws Exception {
		this.mockMvc.perform(get("/secured/addPage")).andExpect(status().isOk()).andExpect(view().name("add.html"));
	}
	
	@Test
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testAddNotes() throws Exception {
		final MockHttpSession mockHttpSession = new MockHttpSession();
		User user = new User("junitUser","junitUser");
		user.setNotes(new HashMap<>());
		String id = userRepo.save(user).getId();
		Notes note = new Notes();
		note.setTitle("Dummy Title");
		note.setMessage("Dummy Message");
		note.setCreationDate(LocalDateTime.now().toString());
		note.setUpdationDate(LocalDateTime.now().toString());
		this.mockMvc.perform(post("/secured/add/note").session(mockHttpSession)
				.param("title", note.getTitle()).param("message", note.getMessage())
				.param("creationDate", note.getCreationDate()).
				param("updationDate", note.getUpdationDate())).
		andExpect(redirectedUrl("/secured/notes"));
		userRepo.deleteById(id);
	}
	
	
	
	@Test
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testEditNotes() throws Exception {
		User user = new User("junitUser","junitUser");
		Notes note = new Notes();
		note.setTitle("Dummy Title");
		note.setMessage("Dummy Message");
		note.setCreationDate(LocalDateTime.now().toString());
		note.setUpdationDate(LocalDateTime.now().toString());
		HashMap<String, Notes> map = new HashMap<String,Notes>();
		map.put(note.getTitle(), note);
		user.setNotes(map);
		String id = userRepo.save(user).getId();
		this.mockMvc.perform(get("/secured/edit/{title}",note.getTitle()))
				.andExpect(view().name("edit"));
		userRepo.deleteById(id);
	}
	
	
	
	@Test
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testSubmitNotes() throws Exception {
		User user = new User("junitUser","junitUser");
		Notes note = new Notes();
		note.setTitle("Dummy Title");
		note.setMessage("Dummy Message");
		note.setCreationDate(LocalDateTime.now().toString());
		note.setUpdationDate(LocalDateTime.now().toString());
		HashMap<String, Notes> map = new HashMap<String,Notes>();
		map.put(note.getTitle(), note);
		user.setNotes(map);
		String id = userRepo.save(user).getId();
		this.mockMvc.perform(post("/secured/edit/note")
				.param("title",note.getTitle())
				.param("message",note.getMessage()+"edited")).
		andExpect(redirectedUrl("/secured/notes"));
		Optional<User> dbUser = userRepo.findById(id);
		assertEquals(dbUser.get().getNotes().get(note.getTitle()).getMessage(), note.getMessage()+"edited");
		userRepo.deleteById(id);
	}
	
	
	@Test
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testDeleteNotes() throws Exception {
		User user = new User("junitUser","junitUser");
		Notes note = new Notes();
		note.setTitle("Dummy Title");
		note.setMessage("Dummy Message");
		note.setCreationDate(LocalDateTime.now().toString());
		note.setUpdationDate(LocalDateTime.now().toString());
		HashMap<String, Notes> map = new HashMap<String,Notes>();
		map.put(note.getTitle(), note);
		user.setNotes(map);
		String id = userRepo.save(user).getId();
		this.mockMvc.perform(get("/secured/delete/{title}",note.getTitle())).
		andExpect(redirectedUrl("/secured/notes"));
		Optional<User> dbUser = userRepo.findById(id);
		Notes dbNote = dbUser.get().getNotes().get(note.getTitle());
		assertNull(dbNote);
		userRepo.deleteById(id);
	}
	
	
	
	@Test
	@WithMockUser(username = "junitUser", password = "junitUser")
	public void testGetNotes() throws Exception {
		User user = new User("junitUser","junitUser");
		Notes note = new Notes();
		note.setTitle("Dummy Title");
		note.setMessage("Dummy Message");
		note.setCreationDate(LocalDateTime.now().toString());
		note.setUpdationDate(LocalDateTime.now().toString());
		HashMap<String, Notes> map = new HashMap<String,Notes>();
		map.put(note.getTitle(), note);
		user.setNotes(map);
		String id = userRepo.save(user).getId();
		this.mockMvc.perform(get("/secured/notes"));
		Optional<User> dbUser = userRepo.findById(id);
		Notes dbNote = dbUser.get().getNotes().get(note.getTitle());
		assertNotNull(dbNote);
		userRepo.deleteById(id);
	}
	
	
	
	
	
	
	
	
	

}
