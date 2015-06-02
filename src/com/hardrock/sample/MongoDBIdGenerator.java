package com.hardrock.sample;

import java.net.UnknownHostException;

import org.bson.BasicBSONObject;

import com.hardrock.mongo.SingletonMongoClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBIdGenerator {
	/**
	 * Get the next unique ID for a named sequence.
	 * @param db Mongo database to work with
	 * @param seqId The name of your sequence (I name mine after my collections)
	 * @return The next ID
	 * @throws UnknownHostException 
	 */
	private static int getNextId(MongoClient mongoClient, String dbName, String sequenceCollection, String seqId){
		DB db = mongoClient.getDB(dbName);
		
//	    String sequenceCollection = "Seq"; // the name of the sequence collection
	    String sequenceField = "seq"; // the name of the field which holds the sequence

	    DBCollection seq = db.getCollection(sequenceCollection); // get the collection (this will create it if needed) 

	    // this object represents your "query", its analogous to a WHERE clause in SQL
	    DBObject query = new BasicDBObject();
	    query.put("id", seqId); // where _id = the input sequence name

	    // this object represents the "update" or the SET blah=blah in SQL
	    DBObject change = new BasicDBObject(sequenceField, 1);
	    DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment

	    // Atomically updates the sequence field and returns the value for you
	    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    
	    return ((BasicBSONObject)res).getInt(sequenceField);
	}
	
	private static int getCurrentId(String seqName){
		return 0;
	}
	
	public static int getNextId(){
		return getNextId(SingletonMongoClient.getDefaultLocalClient(), "isys", "Seq", "id");
	}
	
	public static int getCurrentId(){
		MongoClient mongoClient = SingletonMongoClient.getDefaultLocalClient();
		DB db = mongoClient.getDB("isys");
		
	    String sequence_collection = "Seq";

	    DBCollection seq = db.getCollection(sequence_collection);
	    DBObject bo = seq.findOne();
	    return (Integer) bo.get("seq");
	}
}
