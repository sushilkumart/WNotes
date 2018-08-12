package com.wander.notes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wander.notes.model.Notes;
import com.wander.notes.services.NotesDAO;

/**
 * Controller to server request for notes CRUD oeprations.
 * 
 * @author sushil
 *
 */
@RestController
@RequestMapping("/secured/")
public class NotesController {

	Logger logger = LoggerFactory.getLogger(NotesController.class);

	@Autowired
	NotesDAO notesDao;

	/**
	 * Methods defines model and view of notes addition page.
	 * 
	 * @return
	 */

	@GetMapping("/addPage")
	public ModelAndView getAddPage() {
		logger.debug("Getting add user page view");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("add.html");
		modelAndView.addObject(new Notes());
		return modelAndView;
	}

	/**
	 * Method to serve note addition request.
	 * 
	 * @param note
	 * @param response
	 * @throws IOException
	 */

	@PostMapping("/add/note")
	public ModelAndView addNotes(@ModelAttribute Notes note) throws IOException {
		ModelAndView model = new ModelAndView("redirect:/secured/notes");
		logger.debug("Addition of notes - start");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		notesDao.saveNotes(note, authentication.getName());
		logger.debug("Addition of notes - End");
		return model;
	}

	/**
	 * Method to create view and model for edit note page.
	 * 
	 * @param title
	 * @return
	 */

	@GetMapping("/edit/{title}")
	public ModelAndView editNotes(@PathVariable String title) {
		logger.debug("Edit Note View - start");
		ModelAndView modelAndView = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Notes note = notesDao.editNotes(authentication.getName(), title);
		modelAndView.setViewName("edit");
		modelAndView.addObject(note);
		logger.debug("Edit Note View - end");
		return modelAndView;

	}

	/**
	 * Method to server request of editing and then saving notes.
	 * 
	 * @param note
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/edit/note")
	public ModelAndView submitNote(@ModelAttribute Notes note) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/secured/notes");
		logger.debug("Edit Note function - start");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		notesDao.editAndSaveNotes(authentication.getName(), note);
		logger.debug("Edit Note function - end");
		return modelAndView;
	}

	/**
	 * Method to serve request to delete note.
	 * 
	 * @param title
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/delete/{title}")
	public void deleteNote(@PathVariable String title, HttpServletResponse response) throws IOException {
		logger.debug("Delete Note function - start");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		notesDao.deleteNote(authentication.getName(), title);
		logger.debug("Delete Note function - end");
		response.sendRedirect("/secured/notes");
	}

	/**
	 * Method to serve request of retrieving all notes of user.
	 * 
	 * @return
	 */
	@GetMapping("/notes")
	public ModelAndView getNotes() {
		logger.debug("Get All Notes function - start");
		ModelAndView modelAndView = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Notes> note = notesDao.getAllNotes(authentication.getName());
		modelAndView.setViewName("home");
		modelAndView.addObject("notes", note);
		logger.debug("Get All Notes function - end");
		return modelAndView;
	}

}
