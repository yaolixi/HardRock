package com.hardrock.mongo;

import com.google.gson.Gson;
import com.hardrock.util.GsonTypeAdapter;
import com.hardrock.util.GsonTypeAdapter.GsonAdapterType;
import com.mongodb.DBObject;

public class GSONConverter implements BSONBeanConverter{
	
	private final static Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
	
	@Override
	public <T> Object convert(DBObject bo, Class<T> beanClass) {
		return GSON.fromJson(bo.toString(), beanClass);
	}

}
