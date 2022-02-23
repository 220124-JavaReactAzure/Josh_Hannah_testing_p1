package com.revature.weddingDreams.daos;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.revature.weddingDreams.models.Wedding;
import com.revature.weddingDreams.util.HibernateUtil;

public class BetrothedDAO {

	
	public boolean addWedding(Wedding wedding) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(wedding);
			session.getTransaction().commit();
			session.close();
			return true;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}	
	}	
	
	public List<Wedding> getAllWeddings() {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Wedding> criteria = builder.createQuery(Wedding.class);
			criteria.from(Wedding.class);
			List<Wedding> data = session.createQuery(criteria).getResultList();
			return data;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 public Wedding getWeddingByID(String id) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Wedding wedding = session.get(Wedding.class, id);
			session.getTransaction().commit();
			session.close();
			return wedding;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	 }		
	
}
