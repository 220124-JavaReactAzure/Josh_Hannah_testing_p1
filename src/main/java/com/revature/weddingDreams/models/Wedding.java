package com.revature.weddingDreams.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="weddings")
public class Wedding {
	
//Variables
	@Id
	@Column(name="wedding_id")
	private String weddingID;
	
	// as of right now, only one wedding can be booked for a particular date, for simplicity sake
	@Column(name="wedding_date") // format year-month-day
	private String weddingDate;
	
	@Column(name="wedding_budget")
	private double weddingBudget;
	
	@Column(name="venue_id")
	private String venueID;
	
	@Column(name="caterer_id")
	private String catererID;
	
	@Column(name="florist_id")
	private String floristID;
	
	@Column(name="photographer_id")
	private String photographerID;
	
	@Column(name="musician_id")
	private String musicianID;
	
//Constructors
	public Wedding() { super(); }
	
	public Wedding(String weddingID, String weddingDate, double weddingBudget, String venueID, String catererID, String floristID, String photographerID, String musicianID) {
		this.weddingID = weddingID;
		this.weddingDate = weddingDate;
		this.weddingBudget = weddingBudget;
		this.venueID = venueID;
		this.musicianID = musicianID;
		this.catererID = catererID;
		this.floristID = floristID;
		this.photographerID = photographerID;
	}

	//Getters and Setters
	public String getWeddingID() {
		return weddingID;
	}
	public void setWeddingID(String weddingID) {
		this.weddingID = weddingID;
	}

	public String getWeddingDate() {
		return weddingDate;
	}
	public void setWeddingDate(String weddingDate) {
		this.weddingDate = weddingDate;
	}

	public double getWeddingBudget() {
		return weddingBudget;
	}
	public void setWeddingBudget(double weddingBudget) {
		this.weddingBudget = weddingBudget;
	}	
	
	public String getVenueID() {
		return venueID;
	}
	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}
	
	public String getMusicianID() {
		return musicianID;
	}
	public void setMusicianID(String musicianID) {
		this.musicianID = musicianID;
	}
	
	public String getCatererID() {
		return catererID;
	}
	public void setCatererID(String catererID) {
		this.catererID = catererID;
	}
	
	public String getFloristID() {
		return floristID;
	}
	public void setFloristID(String floristID) {
		this.floristID = floristID;
	}
	
	public String getPhotographerID() {
		return photographerID;
	}
	public void setPhotographerID(String photographerID) {
		this.photographerID = photographerID;
	}
		
}