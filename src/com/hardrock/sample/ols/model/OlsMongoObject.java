package com.hardrock.sample.ols.model;

import com.hardrock.mongo.object.MongoObject;

public class OlsMongoObject extends MongoObject{

	@Override
	public String getDBName() {
		return "ols";
	}

}
