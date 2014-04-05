package com.luohong.sis;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDB {
	private Mongo mg = null;    
	private DB photo;    
	private DBCollection fingerprints;
	
	public void init() {        
		try {           
			mg = new Mongo();            
		} catch (UnknownHostException e) {            
			e.printStackTrace();        
		} catch (MongoException e) {            
			e.printStackTrace();        
		}        
		photo = mg.getDB("photo");        
		fingerprints = photo.getCollection("fingerprint");    
	}
	
	public void destory() {        
		if (mg != null)           
			mg.close();        
		mg = null;        
		photo = null;        
		fingerprints = null;        
		System.gc();    
	}
	
	public MongoDB() {
		super();
		init();
	}
	
	public void dropFingerprint() {
		fingerprints.drop();
	}
	
	public void insertFingerprint(String photo, String fingerprint) {
		BasicDBObject photoFp = new BasicDBObject("photo", photo).append("fingerprint", fingerprint);
		fingerprints.insert(photoFp);
	}
}
