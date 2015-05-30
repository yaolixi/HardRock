package com.hardrock.mongo.object;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MongoObjectExclusionStrategy implements ExclusionStrategy{

	@Override
	public boolean shouldSkipField(FieldAttributes fa) {
		return fa.getName().equals("criteriaForQueryUsage") || fa.getName().equals("queryConditionForQueryUsage");
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

}
