package com.revature.weddingDreams.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assets")
public class Asset {
	
//Variables	
	@Id
	@Column(name = "asset_id")
	private String assetID;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name="price")
	private double price;
	
	@Column(name="asset_type_id")
	private int assetTypeID;

//Constructors
	public Asset() { super(); }

	public Asset(String assetID, String companyName, String address, double price, int assetTypeID) {
		this.assetID = assetID;
		this.companyName = companyName;
		this.address = address;
		this.price = price;
		this.assetTypeID = assetTypeID;
	}

//Getters and Setters
	public String getAssetID() {
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getAssetTypeID() {
		return assetTypeID;
	}
	public void setAssetTypeID(int assetTypeID) {
		this.assetTypeID = assetTypeID;
	}
}