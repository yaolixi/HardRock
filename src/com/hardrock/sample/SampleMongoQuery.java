package com.hardrock.sample;

import com.hardrock.mongo.DefaultMongoQuery;
import com.hardrock.mongo.util.MongoUtil;

public class SampleMongoQuery<T> extends DefaultMongoQuery<T> {

	private static String dbName = "Sample";

	public SampleMongoQuery(Class<T> clazz) {
		super(dbName, MongoUtil.getCollectionName(clazz));
		this.clazz = clazz;
	}
}
