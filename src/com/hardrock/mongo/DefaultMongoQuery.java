package com.hardrock.mongo;

import java.util.Collection;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public abstract class DefaultMongoQuery<T> extends MongoQuery{
	
	public DefaultMongoQuery(String dbName, String collection) {
		super(dbName, collection);
	}

	public DefaultMongoQuery(MongoClient mongoClient, String dbName, String collection) {
		super(mongoClient, dbName, collection);
	}

	protected Class<T> clazz;
	
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
}
