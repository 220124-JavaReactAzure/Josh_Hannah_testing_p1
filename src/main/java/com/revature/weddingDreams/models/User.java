package com.revature.weddingDreams.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

//Variables
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="pass_word")
	private String password;
	
	@Column(name="user_type")
	private int userType;
	
	@Column(name="meal_options_attendee")
	private String mealOptionsAttendee;

	@Column(name="plus_one")
	private boolean plusOne = false;
	
	@Column(name="meal_options_plus_one")
	private String mealOptionsPlusOne;
	
	@Column(name="wedding_id")
	private String wedding; 
//Constructors
	public User() { super(); }
	
	public User(String email, String password, int userType, String mealOptionsAttendee, boolean plusOne, String mealOptionsPlusOne, String wedding) {
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.mealOptionsAttendee = mealOptionsAttendee;
		this.plusOne = plusOne;
		this.mealOptionsPlusOne = mealOptionsPlusOne;
		this.wedding= wedding; 
	}
	
//Getters and Setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getMealOptionsAttendee() {
		return mealOptionsAttendee;
	}
	public void setMealOptionsAttendee(String mealOptionsAttendee ) {
		this.mealOptionsAttendee = mealOptionsAttendee;
	}
	
	public boolean getPlusOne() {
		return plusOne;
	}
	public void setPlusOne(boolean plusOne) {
		this.plusOne = plusOne;
	}
	
	public String getMealOptionsPlusOne() {
		return mealOptionsPlusOne;
	}
	public void setMealOptionsPlusOne(String mealOptionsPlusOne) {
		this.mealOptionsPlusOne = mealOptionsPlusOne;
	}
	
	public String getWedding() {
		return wedding;
	}
	public void setWedding(String wedding) {
		this.wedding = wedding;
	}	
}