package com.hardrock.mongo.aggregate;

import com.google.gson.Gson;
import com.hardrock.mongo.MongoQueryCondition;
import com.hardrock.mongo.SingletonMongoClient;
import com.hardrock.mongo.util.GsonTypeAdapter;
import com.hardrock.mongo.util.GsonTypeAdapter.GsonAdapterType;
import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoAggregation {

private final static Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
	
	private MongoClient mongoClient = SingletonMongoClient.getDefaultLocalClient();
	
	//数据库的标识
	private String dbName;
	//collection的标识
	private String collection;
	
	private MongoQueryCondition queryCondition;
	
	public MongoQueryCondition getQueryCondition() {
		if(queryCondition == null){
			queryCondition = new MongoQueryCondition();
		}
		return queryCondition;
	}

	public void setQueryCondition(MongoQueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	public MongoAggregation(MongoClient mongoClient, String dbName, String collection){
		this.dbName = dbName;
		this.collection = collection;
		this.mongoClient = mongoClient;
	}
	
	public MongoAggregation(String dbName, String collection){
		this.dbName = dbName;
		this.collection = collection;
	}
	
	
	
	public void aggregate(DBObject firstOp, DBObject ...ops){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		AggregationOutput output = coll.aggregate(firstOp, ops);
	}
	
}
