package com.revature.weddingDreams.services;

import java.util.List;

import com.revature.weddingDreams.daos.UserDAO;
import com.revature.weddingDreams.exceptions.AuthenticationException;
import com.revature.weddingDreams.exceptions.InvalidRequestException;
import com.revature.weddingDreams.models.User;

public class UserService {

// Data
	private final UserDAO userDAO;

// Constructor
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

// Methods
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}

	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}
	

	public boolean deleteUser(User user) {
		return userDAO.deleteUser(user);
	}
	
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
		
	public User authenticateUser(String email, String password ) {
		
		if(email == null || email.trim().equals("") || password == null || password.trim().equals("")) {
			
			throw new InvalidRequestException("Username or Password Invalid, please login again.");
		}
		
		User authenticatedUser = userDAO.getUserByEmailandPassword(email, password);
		
		if(authenticatedUser == null) {
			throw new AuthenticationException("Unable to authenticate user, Infomration provided not found.");
		}
		return authenticatedUser;	
	}
	
}
