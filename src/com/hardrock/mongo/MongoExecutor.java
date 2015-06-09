package com.hardrock.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.hardrock.mongo.object.MongoObjectExclusionStrategy;
import com.hardrock.mongo.util.GsonTypeAdapter;
import com.hardrock.mongo.util.GsonTypeAdapter.GsonAdapterType;
import com.hardrock.mongo.util.MongoUtil;
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
	
	private MongoClient mongoClient = SingletonMongoClient.getDefaultLocalClient();
	
	protected MongoExecutor(){}
	
	protected MongoExecutor(MongoClient mongoClient){
		this.mongoClient = mongoClient;
	}
	
	public static MongoExecutor getInstance(){
		return new MongoExecutor();
	}
	
	public static MongoExecutor getInstance(MongoClient mongoClient){
		return new MongoExecutor(mongoClient);
	}
	
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
	public void ensureIndex(String dbName, String collectionName, String json, boolean unique, boolean dropDups, boolean background, boolean sparse){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collectionName);
		DBObject keys = (DBObject)JSON.parse(json);
		BasicDBObject options = new BasicDBObject();
		options.append("unique", unique).append("dropDups", dropDups).append("background", background).append("sparse", sparse);
		coll.ensureIndex(keys, options);
	}
	
	/**
	 * 创建索引
	 * @param dbName
	 * @param collectionName
	 * @param json
	 * @param unique
	 */
	public void createIndex(String dbName, String collectionName, String json, boolean unique){
		ensureIndex(dbName, collectionName, json, unique, false, false, false);
	}
	
	/**
	 * 插入一条新的记录
	 * 应用于insert的object的某些属性不需要持久化到数据库的情况
	 * @param dbName
	 * @param collection
	 * @param doc
	 */
	public WriteResult insert(String dbName, String collection, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		return coll.insert(doc);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param collection
	 * @param json
	 */
	public void insert(String dbName, String collection, String json){
		insert(dbName, collection, (DBObject)JSON.parse(json));
	}
	
	/**
	 * 最直接简单的insert方法，会直接将object整个转成json保存
	 * @param dbName
	 * @param obj
	 */
	public void insert(String dbName, Object obj){
		insert(dbName, MongoUtil.getCollectionName(obj), sgson.toJson(obj));
	}
	
	/**
	 * 批量插入
	 * @param dbName
	 * @param collection
	 * @param docs
	 */
	public void insert(String dbName, String collection, List<DBObject> docs){
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
	public void insert(String dbName, String collection, Collection<String> jsons){
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
	public void insert(String dbName, Collection<?> objs){
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
	 * 更新符合条件的记录
	 * @param dbName
	 * @param collection
	 * @param criteria
	 * @param doc
	 */
	public void update(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		DBObject obj = (DBObject)coll.findOne(criteria);
		for(Object str : doc.toMap().keySet()){
			obj.put(str.toString(), doc.get(str.toString()));
		}
		coll.save(obj);
	}
	
	public void multiUpdate(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		coll.update(criteria, new BasicDBObject("$set",doc), false, true);
	}
	
	/**
	 * 采用MongoDB的upsert，采用自己先查，如果没有就insert，有就update，一次只能处理一条记录
	 * @param dbName
	 * @param collection
	 * @param criteria
	 * @param doc
	 */
	public void upsert(String dbName, String collection, DBObject criteria, DBObject doc){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		coll.update(criteria, doc, true, false);
	}
	
	public void upsert(String dbName, Object obj, DBObject criteria){
		DB db = mongoClient.getDB(dbName);
		
		String collectionName = MongoUtil.getCollectionName(obj);
		DBCollection coll = db.getCollection(collectionName);
		String json = sgson.toJson(obj);
		DBObject dbObject = (DBObject)JSON.parse(json);
		coll.update(criteria, dbObject, true, false);
	}
	
	public void upsert(String dbName, String collection, DBObject criteria, String json){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		DBObject dbObject =(DBObject)JSON.parse(json);
		
		coll.update(criteria, dbObject, true, false);
	}
	
	/**
	 * 删除符合条件的记录
	 * @param dbName
	 * @param collection
	 * @param criteria
	 */
	public void remove(String dbName, String collection, DBObject criteria){
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
	public <T> WriteResult remove(String dbName, Class<T> clazz, DBObject criteria){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(MongoUtil.getCollectionName(clazz));
		
		return coll.remove(criteria);
	}
	
	/**
	 * 
	 * @param dbName
	 * @param collection
	 */
	public void dropCollection(String dbName, String collection){
		DB db = mongoClient.getDB(dbName);
		DBCollection coll = db.getCollection(collection);
		coll.drop();
	}

	/**
	 * delete all documents of collection
	 * 
	 * @param collection
	 */
	public void deleteAll(String dbName, String collection) {
		remove(dbName, collection, new BasicDBObject());
	}
}
