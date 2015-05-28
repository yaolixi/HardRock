package com.hardrock.mongo;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

public class MongoUtil {
//	public static final String ISYS_DB_NAME = "isys";
	
	public static String getCollectionName(Object obj){
		String className = obj.getClass().getSimpleName();
		return className;
//		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}
	
	public static <T> String getCollectionName(Class<T> clazz){
		return clazz.getSimpleName();
	}
	
	public static MongoClient createMongoClient(String host, int port) throws UnknownHostException{
		Builder builder = MongoClientOptions.builder();
		builder.connectionsPerHost(2);
		builder.autoConnectRetry(true);
		builder.maxWaitTime(5000);
		MongoClientOptions options = builder.build();
		
		ServerAddress servAddr = new ServerAddress(host ,port);
		return new MongoClient(servAddr, options);
	}
}
