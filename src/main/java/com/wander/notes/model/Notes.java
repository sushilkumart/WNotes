package com.wander.notes.model;

/**
 * Class represents the Notes entity.
 * @author sushil
 *
 */
public class Notes {

	private String title;
	private String message;
	private String creationDate;
	private String updationDate;
	
	
	public Notes() {
		// TODO Auto-generated constructor stub
	}


	public Notes(String title, String message, String creationDate, String updationDate) {
		super();
		this.title = title;
		this.message = message;
		this.creationDate = creationDate;
		this.updationDate = updationDate;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getUpdationDate() {
		return updationDate;
	}


	public void setUpdationDate(String updationDate) {
		this.updationDate = updationDate;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public String toString() {
		return "Notes [title=" + title + ", message=" + message + ", creationDate=" + creationDate + ", updationDate="
				+ updationDate + "]";
	}


}
