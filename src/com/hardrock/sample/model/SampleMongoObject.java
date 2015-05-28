package com.hardrock.sample.model;

import com.hardrock.mongo.MongoObject;

public abstract class SampleMongoObject extends MongoObject {
	
	public SampleMongoObject(){}
	
	@Override
	public String getDBName() {
		return "Sample";
	}
	
//	protected static Collection<? extends MongoObject> findAll(Class<? extends MongoObject> clazz, MongoQueryCondition queryCondition){
//		return MongoObject.findAll("Sample", clazz, queryCondition);
//	}
}
