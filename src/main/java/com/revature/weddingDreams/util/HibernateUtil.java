package com.revature.weddingDreams.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.weddingDreams.models.Asset;
import com.revature.weddingDreams.models.AssetType;
import com.revature.weddingDreams.models.User;
import com.revature.weddingDreams.models.Wedding;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create session factory from hibernate.cfg.xml, might not need hibernate.properties
			Configuration cfg = new Configuration().configure();
			cfg.addAnnotatedClass(User.class);
			cfg.addAnnotatedClass(Asset.class);
			cfg.addAnnotatedClass(Wedding.class);
			cfg.addAnnotatedClass(AssetType.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			return cfg.buildSessionFactory(serviceRegistry);
		} 
		catch (Exception e) {
			System.out.println("*** Error building Session Factory ***");
		}	
		return null;
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
}