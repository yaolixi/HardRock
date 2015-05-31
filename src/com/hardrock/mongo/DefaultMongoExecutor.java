package com.hardrock.mongo;

import java.util.Collection;

import com.hardrock.mongo.util.MongoUtil;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DefaultMongoExecutor extends MongoExecutor{
	
	private String dbName;
	
	private DefaultMongoExecutor(){}
	
	private DefaultMongoExecutor(String dbName){
		this.dbName = dbName;
	}
	
	private DefaultMongoExecutor(String dbName, MongoClient client){
		super(client);
		this.dbName = dbName;
	}
	
	public static DefaultMongoExecutor getInstance(String dbName){
		return new DefaultMongoExecutor(dbName);
	}
	
	public static DefaultMongoExecutor getInstance(String dbName, MongoClient client){
		return new DefaultMongoExecutor(dbName, client);
	}
	
	public void insert(Object obj) {
		insert(dbName, obj);
	}

	public <T> void insert(Class<T> clazz, DBObject doc) {
		insert(dbName, MongoUtil.getCollectionName(clazz), doc);
	}

	public void insert(String collection, String json) {
		insert(dbName, collection, json);
	}
	
	public void insert(Collection<?> objs) {
		insert(dbName, objs);
	}
	
	public <T> void update(Class<T> clazz, DBObject criteria, DBObject doc){
		update(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public <T> void multiUpdate(Class<T> clazz, DBObject criteria, DBObject doc){
		multiUpdate(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public <T> void upsert(Class<T> clazz, DBObject criteria, DBObject doc){
		upsert(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public void upsert(Object obj, DBObject criteria){
		upsert(dbName, obj, criteria);
	}
	
	public <T> void upsert(Class<T> clazz, DBObject criteria, String json){
		upsert(dbName, MongoUtil.getCollectionName(clazz), criteria, json);
	}
	
	public <T> void upsert(String collectionName, DBObject criteria, String json){
		upsert(dbName, collectionName, criteria, json);
	}
	
	public <T> void upsert(String collectionName, DBObject criteria, DBObject doc){
		upsert(dbName, collectionName, criteria, doc);
	}
	
	public <T> void remove(Class<T> clazz, DBObject criteria){
		remove(dbName, MongoUtil.getCollectionName(clazz), criteria);
	}
	
	public void dropCollection(String collection){
		dropCollection(dbName, collection);
	}
	
	public void deleteAll(String collection){
		deleteAll(dbName, collection);
	}
}
