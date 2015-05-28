package com.hardrock.sample;

import java.util.Collection;

import com.hardrock.mongo.MongoQuery;
import com.hardrock.mongo.MongoUtil;
import com.hardrock.mongo.SingletonMongoClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class SampleMongoQuery<T> extends MongoQuery{

	private Class<T> clazz;
	
	private static String dbName = "Sample";
	
	public SampleMongoQuery(Class<T> clazz) {
		super(dbName, MongoUtil.getCollectionName(clazz));
		this.clazz = clazz;
	}
	
	/**
	 * �������м�¼
	 * @return
	 */
	public Collection<T> findAll() {
		return super.findAll(clazz);
	}
	
	/**
	 * ����һ����¼
	 * @return
	 */
	public T findOne() {
		return super.findOne(clazz);
	}
	
	/**
	 * Ĭ�ϸ���Id������
	 * @param id
	 * @return
	 */
	public T findById(int id){
		return this.findOneBy("id", id);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public T findOneBy(String key, Object value){
		super.getQueryCondition().setCriteria(new BasicDBObject(key,value));
		return super.findOne(clazz);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Collection<T> findAllBy(String key, Object value){
		super.getQueryCondition().setCriteria(new BasicDBObject(key,value));
		return super.findAll(clazz);
	}
	
	@Override
	public int getCount() {
		MongoClient mongoClient = SingletonMongoClient.getInstance();
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(MongoUtil.getCollectionName(clazz));
		return coll.find(getQueryCondition().getCriteria()).count();
	}
}
