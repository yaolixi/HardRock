package com.hardrock.mongo;

import com.mongodb.DBObject;

public interface BSONBeanConverter {
	public <T> Object convert(DBObject bo, Class<T> beanClass);
}
