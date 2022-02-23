package com.revature.weddingDreams.daos;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.weddingDreams.models.AssetType;
import com.revature.weddingDreams.models.Asset;
import com.revature.weddingDreams.models.User;
import com.revature.weddingDreams.models.Wedding;
import com.revature.weddingDreams.util.HibernateUtil;


public class EmployeeDAO {
	
	public boolean addAsset(Asset asset) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(asset);
			session.getTransaction().commit();
			session.close();
			return true;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}	
	}	
	
	public List<Asset> getAllAssets() {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Asset> criteria = builder.createQuery(Asset.class);
			criteria.from(Asset.class);
			List<Asset> data = session.createQuery(criteria).getResultList();
			return data;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	

	 public Asset getAssetByID(String id) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Asset asset = session.get(Asset.class, id);
			session.getTransaction().commit();
			session.close();
			return asset;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	 }

	public List<Asset> getAssetsByType(int type) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			
			String hql="from Asset a where a.assetTypeID =:type";
						
			Query query = session.createQuery(hql);
			query.setParameter("type", type);
			List assets = query.list();							
			session.getTransaction().commit();
			session.close();
			return assets;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
/*
	public boolean deleteAsset(Asset asset) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.delete(asset);
			session.getTransaction().commit();
			return true;
		}
		catch (HibernateException | IOException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			HibernateUtil.closeSession();
		}
	}
 */
/***************** This method gets list of asset types, not assets ******/
	public List<AssetType> getAssetTypes() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AssetType> criteria = builder.createQuery(AssetType.class);
		criteria.from(AssetType.class);
		List<AssetType> data = session.createQuery(criteria).getResultList();
		return data;
	}
	
	
	
}
