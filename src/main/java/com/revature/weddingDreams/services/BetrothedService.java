package com.revature.weddingDreams.services;

import java.util.List;

import com.revature.weddingDreams.daos.BetrothedDAO;
import com.revature.weddingDreams.models.Wedding;

public class BetrothedService {

	private final BetrothedDAO betrothedDAO;
	
	public BetrothedService(BetrothedDAO betrothedDAO) {
		this.betrothedDAO = betrothedDAO;
	}
	
	public boolean addWedding(Wedding wedding) {
		return betrothedDAO.addWedding(wedding);
	}
	
	public List getAllWeddings() {
		return betrothedDAO.getAllWeddings();
	}
	
	public Wedding getWeddingByID(String id) {
		return betrothedDAO.getWeddingByID(id);
	}
		
}
