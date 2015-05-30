package com.hardrock.sample;

import com.hardrock.mongo.DefaultMongoQuery;
import com.hardrock.mongo.MongoQueryCondition;
import com.hardrock.mongo.MongoUtil;
import com.hardrock.sample.model.Prod;
import com.mongodb.BasicDBObject;

public class DefaultMongoQueryImpl<T> extends DefaultMongoQuery<T>{
	
	private static String dbName = "isys";
	
	public DefaultMongoQueryImpl(Class<T> clazz) {
		super(MongoUtil.createMongoClient("121.41.43.200", 27017), dbName, MongoUtil.getCollectionName(clazz));
		this.clazz = clazz;
	}
	
	public static void main(String[] args) {
		DefaultMongoQueryImpl<Prod> query = new DefaultMongoQueryImpl<Prod>(Prod.class);
		query.setQueryCondition(new MongoQueryCondition(new BasicDBObject("code", "600117")));
		System.out.println(query.findAll());
	}
}
