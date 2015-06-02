package com.hardrock.sample.ols;

import com.hardrock.mongo.DefaultMongoQuery;
import com.hardrock.mongo.util.MongoUtil;

public class OlsMongoQuery<T> extends DefaultMongoQuery<T> {

	private static String dbName = "ols";

	public OlsMongoQuery(Class<T> clazz) {
		super(dbName, MongoUtil.getCollectionName(clazz));
		this.clazz = clazz;
	}
}
