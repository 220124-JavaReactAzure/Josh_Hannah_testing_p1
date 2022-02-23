package com.revature.weddingDreams.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="asset_types")
public class AssetType {
	
//Variables	
	@Id
	@Column(name = "asset_type_id")
	private int assetTypeID;
	
	@Column(name = "asset_type")
	private String assetType;
	
//Constructors
	public AssetType() { super(); }

	public AssetType(int assetTypeID, String assetType) {
		this.assetTypeID = assetTypeID;
		this.assetType = assetType;
	}

//Getters and Setters
	public int getAssetTypeID() {
		return assetTypeID;
	}
	public void setAssetTypeID(int assetTypeID) {
		this.assetTypeID = assetTypeID;
	}

	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
}