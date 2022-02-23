package com.revature.weddingDreams.services;

import com.revature.weddingDreams.daos.AttendeeDAO;
import com.revature.weddingDreams.models.User;

public class AttendeeService {

	private final AttendeeDAO attendeeDAO;
	
	public AttendeeService(AttendeeDAO attendeeDAO) {
		this.attendeeDAO = attendeeDAO;
	}
	
	public boolean updateAttendeeMealChoice(User user) {
		return attendeeDAO.updateAttendeeMealChoice(user);
	}
	
	
}
