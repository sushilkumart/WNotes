package com.wander.notes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sushil
 */
@SpringBootApplication
public class WNotesApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(WNotesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WNotesApplication.class, args);
		logger.info("WNotes Application Started");
		logger.debug("WNotes Application Started");
	}
}
