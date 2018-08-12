package com.wander.notes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wander.notes.model.Notes;
import com.wander.notes.model.User;
import com.wander.notes.repository.UserRepo;

/**
 * Class which implements the CRUD operations related to notes.
 * 
 * @author sushil
 *
 */
@Component
public class NotesDAO {

	Logger logger = LoggerFactory.getLogger(NotesDAO.class);

	@Autowired
	private UserRepo userRepo;

	/**
	 * Method to save notes in DB.
	 * 
	 * @param note
	 * @param name
	 * @return
	 */
	public boolean saveNotes(Notes note, String name) {
		logger.debug("Save notes function - start");
		try {
			User user = userRepo.findByUserName(name);
			Map<String, Notes> notesList = user.getNotes();
			if (notesList == null)
				notesList = new HashMap<String, Notes>();
			note.setCreationDate(LocalDateTime.now().toString());
			note.setUpdationDate(LocalDateTime.now().toString());
			notesList.put(note.getTitle(), note);
			user.setNotes(notesList);
			userRepo.save(user);
		} catch (Exception e) {
			logger.error("Save notes Funtion exception raised" + e);
			e.printStackTrace();
			return false;
		}
		logger.debug("Save notes function - end");
		return true;
	}

	/**
	 * Method to retrieve list all the user notes.
	 * 
	 * @param name
	 * @return
	 */
	public List<Notes> getAllNotes(String name) {
		List<Notes> noteList = null;
		try {
			logger.debug("Get All notes funtion - start");
			noteList = new ArrayList<Notes>();
			User user = userRepo.findByUserName(name);
			Map<String, Notes> note = user.getNotes();
			if (!note.isEmpty()) {
				Iterator<Notes> itr = note.values().iterator();
				while (itr.hasNext()) {
					Notes enote = itr.next();
					noteList.add(enote);
				}
			}
		} catch (Exception e) {
			logger.error("Get all notes funtion exception raised." + e);
			e.printStackTrace();
		}
		logger.debug("Get All notes funtion - end");
		return noteList;
	}

	/**
	 * Method to get notes to be edited.
	 * 
	 * @param name
	 * @param title
	 * @return
	 */
	public Notes editNotes(String name, String title) {
		Notes note = null;
		try {
			logger.debug("Edit notes function - start");
			User user = userRepo.findByUserName(name);
			note = user.getNotes().get(title);
		} catch (Exception e) {
			logger.error("Edit Notes function raised exception");
			e.printStackTrace();
		}
		logger.debug("Edit notes function - start");
		return note;

	}

	/**
	 * Method to save edited notes.
	 * 
	 * @param name
	 * @param note
	 */
	public void editAndSaveNotes(String name, Notes note) {
		try {
			logger.debug("Edit and Save Note function - start");
			User user = userRepo.findByUserName(name);
			Notes notes = user.getNotes().get(note.getTitle());
			notes.setMessage(note.getMessage());
			notes.setUpdationDate(LocalDateTime.now().toString());
			user.getNotes().put(note.getTitle(), notes);
			userRepo.save(user);
			logger.debug("Edit and Save Note function - start");
		} catch (Exception e) {
			logger.error("Edit and Save Note function raised exception" + e);
			e.printStackTrace();
		}
	}

	/**
	 * Method to Delete notes.
	 * 
	 * @param name
	 * @param title
	 */
	public void deleteNote(String name, String title) {
		try {
			logger.debug("Delete note function - start");
			User user = userRepo.findByUserName(name);
			user.getNotes().remove(title);
			userRepo.save(user);
			logger.debug("Delete note function - end");
		} catch (Exception e) {
			logger.error("Delete note function raised exception" + e);
			e.printStackTrace();
		}
	}

}
