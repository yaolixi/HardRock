package com.hardrock.sample;

import java.util.Collection;

import com.hardrock.mongo.MongoExecutor;
import com.hardrock.mongo.MongoUtil;
import com.mongodb.DBObject;

public class SampleMongoExecutor extends MongoExecutor{
	
	private static String dbName = "Sample";
	
	public static void insert(Object obj) {
		insert(dbName, obj);
	}

	public static <T> void insert(Class<T> clazz, DBObject doc) {
		insert(dbName, MongoUtil.getCollectionName(clazz), doc);
	}

	public static void insert(String collection, String json) {
		insert(dbName, collection, json);
	}
	
	public static void insert(Collection<Object> objs) {
		insert(dbName, objs);
	}
	
	public static <T> void update(Class<T> clazz, DBObject criteria, DBObject doc){
		update(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public static <T> void multiUpdate(Class<T> clazz, DBObject criteria, DBObject doc){
		multiUpdate(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public static <T> void upsert(Class<T> clazz, DBObject criteria, DBObject doc){
		upsert(dbName, MongoUtil.getCollectionName(clazz), criteria, doc);
	}
	
	public static void upsert(Object obj, DBObject criteria){
		upsert(dbName, obj, criteria);
	}
	
	public static <T> void upsert(Class<T> clazz, DBObject criteria, String json){
		upsert(dbName, MongoUtil.getCollectionName(clazz), criteria, json);
	}
	
	public static <T> void upsert(String collectionName, DBObject criteria, String json){
		upsert(dbName, collectionName, criteria, json);
	}
	
	public static <T> void upsert(String collectionName, DBObject criteria, DBObject doc){
		upsert(dbName, collectionName, criteria, doc);
	}
	
	public static <T> void remove(Class<T> clazz, DBObject criteria){
		remove(dbName, MongoUtil.getCollectionName(clazz), criteria);
	}
	
	public static void dropIsysCollection(String collection){
		dropCollection(dbName, collection);
	}
	
	public static void deleteAll(String collection){
		deleteAll(dbName, collection);
	}
}
