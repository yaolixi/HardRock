package com.hardrock.mongo;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.hardrock.mongo.util.GsonTypeAdapter;
import com.hardrock.mongo.util.GsonTypeAdapter.GsonAdapterType;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoQuery {
	
	private final static Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
	
	//by default connect to local database
	protected MongoClient mongoClient = SingletonMongoClient.getDefaultLocalClient();
	
	//���ݿ�ı�ʶ
	private String dbName;
	//collection�ı�ʶ
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
	
	public MongoQuery(MongoClient mongoClient, String dbName, String collection){
		this.dbName = dbName;
		this.collection = collection;
		this.mongoClient = mongoClient;
	}
	
	public MongoQuery(String dbName, String collection){
		this.dbName = dbName;
		this.collection = collection;
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void findAll(MongoCursorHandler handler) {
		DB db = mongoClient.getDB(dbName);

		DBCollection coll = db.getCollection(collection);
		DBCursor cursor = coll.find(getQueryCondition().getCriteria(), getQueryCondition().getFields())
				.sort(getQueryCondition().getOrderBy())
				.skip(getQueryCondition().getSkip())
				.limit(getQueryCondition().getLimit());

		try {
			while (cursor.hasNext()) {
				handler.handle(cursor.next());
			}
		}
		catch (Exception e) {
			throw new MongoExecutionException(e);
		}
		finally {
			cursor.close();
		}
	}
	
	/**
	 * �������õ�������ѯ���еļ�¼
	 * @param clazz  ��Ҫת����javabean����
	 * @return
	 */
	public <T> Collection<T> findAll(Class<T> clazz){
		Collection<T> beans = new ArrayList<T>();
		
		DB db = mongoClient.getDB(dbName);

		DBCollection coll = db.getCollection(collection);

		DBCursor cursor = coll.find(getQueryCondition().getCriteria(), getQueryCondition().getFields()).sort(getQueryCondition().getOrderBy()).skip(getQueryCondition().getSkip()).limit(getQueryCondition().getLimit());
		
		try {
			while (cursor.hasNext()) {
				beans.add(GSON.fromJson(cursor.next().toString(), clazz));
			}
		}
		catch (Exception e) {
			throw new MongoExecutionException(e);
		}
		finally {
			cursor.close();
		}
		return beans;
	}
	
	/**
	 * �������õ�������ѯ��һ����¼
	 * @param clazz
	 * @return
	 */
	protected <T> T findOne(Class<T> clazz){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		
		DBObject obj = (DBObject)coll.findOne(getQueryCondition().getCriteria(), getQueryCondition().getFields());
		
		if(obj == null){return null;}
		
		return GSON.fromJson(obj.toString(), clazz);
	}
	
	/**
	 * ��ü�¼��Ŀ
	 * @return
	 */
	public int count(){
		DB db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection(collection);
		return coll.find(getQueryCondition().getCriteria()).count();
	}
}
