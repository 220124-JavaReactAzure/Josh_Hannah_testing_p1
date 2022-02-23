package com.revature.weddingDreams.services;

import java.util.List;

import com.revature.weddingDreams.daos.EmployeeDAO;
import com.revature.weddingDreams.models.Asset;
import com.revature.weddingDreams.models.AssetType;


public class EmployeeService {

	private final EmployeeDAO employeeDAO;
		
	public EmployeeService(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public boolean addAsset(Asset asset) {
		return employeeDAO.addAsset(asset);
	}
	
	public List getAllAssets() {
		return employeeDAO.getAllAssets();
	}

	public List<Asset> getAssetsByType(int type) {
		return employeeDAO.getAssetsByType(type);
	}
	
	
	public Asset getAssetByID(String id) {
		return employeeDAO.getAssetByID(id);
	}
	
	/*
	public boolean deleteAsset(Asset asset) {
		return employeeDAO.deleteAsset(asset);
	}
	*/
	public List<AssetType> getAssetTypes() {
		return employeeDAO.getAssetTypes();
	}
}