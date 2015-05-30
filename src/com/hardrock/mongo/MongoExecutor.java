package com.hardrock.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.hardrock.mongo.object.MongoObjectExclusionStrategy;
import com.hardrock.mongo.util.GsonTypeAdapter;
import com.hardrock.mongo.util.MongoUtil;
import com.hardrock.mongo.util.GsonTypeAdapter.GsonAdapterType;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoExecutor {
	
	private static ExclusionStrategy strategy = new MongoObjectExclusionStrategy();
	
	private static Gson sgson = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.SERIALIZER).setExclusionStrategies(strategy).create();
	
	private static MongoClient mongoClient = SingletonMongoClient.getDefaultLocalClient();
	
	/**
	 * 
	 * @param dbName
	 * @param collectionName
	 * @param json
	 * @param unique
	 * @param dropDups
	 * @param background
	 * @param sparse
	 */
	public static void createIndex(String dbName, String collectionName, String json, boolean unique, boolean dropDups, boolean background, boolean sparse){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collectionName);
		DBObject keys = (DBObject)JSON.parse(json);
		BasicDBObject options = new BasicDBObject();
		options.append("unique", unique).append("dropDups", dropDups).append("background", background).append("sparse", sparse);
		coll.ensureIndex(keys, options);
	}
	
	/**
	 * ��������
	 * @param dbName
	 * @param collectionName
	 * @param json
	 * @param unique
	 */
	public static void createIndex(String dbName, String collectionName, String json, boolean unique){
		createIndex(dbName, collectionName, json, unique, false, false, false);
	}
	
	/**
	 * ����һ���µļ�¼
	 * Ӧ����insert��object��ĳЩ���Բ���Ҫ�־û������ݿ�����
	 * @param dbName
	 * @param collection
	 * @param doc
	 */
	public static void insert(String dbName, String collection, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		coll.insert(doc);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param collection
	 * @param json
	 */
	public static void insert(String dbName, String collection, String json){
		insert(dbName, collection, (DBObject)JSON.parse(json));
	}
	
	/**
	 * ��ֱ�Ӽ򵥵�insert��������ֱ�ӽ�object����ת��json����
	 * @param dbName
	 * @param obj
	 */
	public static void insert(String dbName, Object obj){
		insert(dbName, MongoUtil.getCollectionName(obj), sgson.toJson(obj));
	}
	
	/**
	 * ��������
	 * @param dbName
	 * @param collection
	 * @param docs
	 */
	public static void insert(String dbName, String collection, List<DBObject> docs){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		coll.insert(docs);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param collection
	 * @param jsons
	 */
	public static void insert(String dbName, String collection, Collection<String> jsons){
		List<DBObject> docs = new ArrayList<DBObject>();
		for(String json : jsons){
			docs.add((DBObject)JSON.parse(json));
		}
		insert(dbName, collection, docs);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param objs
	 */
	public static void insert(String dbName, Collection<Object> objs){
		Collection<String> jsons = new ArrayList<String>();
		String collection = null;
		for(Object obj : objs){
			jsons.add(sgson.toJson(obj));
			if(collection == null){
				collection = MongoUtil.getCollectionName(obj);
			}
			else if(!collection.equals(MongoUtil.getCollectionName(obj))){
				throw new IllegalArgumentException("all the collection object should be the same class");
			}
		}
		insert(dbName, collection, jsons);
	}
	
	
	/**
	 * ���·��������ļ�¼
	 * @param dbName
	 * @param collection
	 * @param criteria
	 * @param doc
	 */
	protected static void update(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		DBObject obj = (DBObject)coll.findOne(criteria);
		for(Object str : doc.toMap().keySet()){
			obj.put(str.toString(), doc.get(str.toString()));
		}
		coll.save(obj);
	}
	
	protected static void multiUpdate(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		coll.update(criteria, new BasicDBObject("$set",doc), false, true);
	}
	
	/**
	 * ����MongoDB��upsert�������Լ��Ȳ飬���û�о�insert���о�update��һ��ֻ�ܴ���һ����¼
	 * @param dbName
	 * @param collection
	 * @param criteria
	 * @param doc
	 */
	public static void upsert(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		coll.update(criteria, doc, true, false);
	}
	
	public static void upsert(String dbName, Object obj, DBObject criteria){
		DB db = mongoClient.getDB(dbName);
		
		String collectionName = MongoUtil.getCollectionName(obj);
		DBCollection coll = db.getCollection(collectionName);
		String json = sgson.toJson(obj);
		DBObject dbObject = (DBObject)JSON.parse(json);
		coll.update(criteria, dbObject, true, false);
	}
	
	public static void upsert(String dbName, String collection, DBObject criteria, String json){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		DBObject dbObject =(DBObject)JSON.parse(json);
		
		coll.update(criteria, dbObject, true, false);
	}
	
	/**
	 * ɾ�����������ļ�¼
	 * @param dbName
	 * @param collection
	 * @param criteria
	 */
	public static void remove(String dbName, String collection, DBObject criteria){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		
		coll.remove(criteria);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param clazz
	 * @param criteria
	 */
	public static <T> WriteResult remove(String dbName, Class<T> clazz, DBObject criteria){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(MongoUtil.getCollectionName(clazz));
		
		return coll.remove(criteria);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param collection
	 */
	public static void dropCollection(String dbName, String collection){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		coll.drop();
	}

	/**
	 * delete all documents of collection
	 * 
	 * @param collection
	 */
	public static void deleteAll(String dbName, String collection) {
		remove(dbName, collection, new BasicDBObject());
	}
}
