package com.hardrock.mongo;

import com.mongodb.DBObject;

public interface MongoCursorHandler {
	void handle(DBObject object) throws MongoExecutionException;
}
