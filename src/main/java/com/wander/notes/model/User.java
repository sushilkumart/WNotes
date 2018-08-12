package com.wander.notes.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Class represents a user entity.
 * @author sushil
 *
 */
@Document(collection="user")
public class User {

	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private String userName;
	private String password;
	private Map<String,Notes> notes;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Notes> getNotes() {
		return notes;
	}

	public void setNotes(Map<String, Notes> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", notes=" + notes + "]";
	}

}
