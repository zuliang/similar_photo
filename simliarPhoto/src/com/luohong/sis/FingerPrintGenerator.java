package com.luohong.sis;

import java.io.File;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class FingerPrintGenerator {
	public static final String path = "E:/WorkSpaces/faceData/";
	public static final String testImagePath = path + "testPic/";
	public static void main(String[] args) {
		File file = new File(testImagePath);
		String subPaths[];
		subPaths = file.list();
		MongoDB mdb = new MongoDB();
		mdb.dropFingerprint();
		for (String subPath : subPaths) {
			try {
				String fingerPrint = SimilarImageSearch.produceFingerPrint(testImagePath+subPath);
				System.out.println(subPath+" : "+fingerPrint);
				mdb.insertFingerprint(subPath, fingerPrint);
			} catch (RuntimeException e) {
				System.out.println(subPath +" Exception");
				e.printStackTrace();
			}
		}
	}
	
	public static void testMongoDB() {
		try {
			Mongo mongo = new Mongo();
			DB db = mongo.getDB("photo"); 
			DBCollection fingerPrints = db.getCollection("fingerPrint");
			fingerPrints.drop();
			BasicDBObject photoFp = new BasicDBObject("photoName", "MongoDB").append("fingerPrint", "12345");
			fingerPrints.insert(photoFp);
			DBCursor cur = fingerPrints.find();
			while (cur.hasNext()) {
				System.out.println(cur.next());
			}
			mongo.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
}
