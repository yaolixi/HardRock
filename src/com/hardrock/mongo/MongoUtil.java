package com.hardrock.mongo;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

public class MongoUtil {
	
	public static String getCollectionName(Object obj){
		return obj.getClass().getSimpleName();
	}
	
	public static <T> String getCollectionName(Class<T> clazz){
		return clazz.getSimpleName();
	}
	
	public static MongoClient createMongoClient(String host, int port){
		try {
			Builder builder = MongoClientOptions.builder();
			builder.connectionsPerHost(2);
			builder.autoConnectRetry(true);
			builder.maxWaitTime(5000);
			MongoClientOptions options = builder.build();
			
			ServerAddress servAddr = new ServerAddress(host ,port);
			return new MongoClient(servAddr, options);
		} catch (UnknownHostException e) {
			throw new MongoConnectionException(e);
		}
	}
}
