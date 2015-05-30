package com.hardrock.mongo;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.MongoClient;

public class SingletonMongoClient {
	
	private static Map<String, MongoClient> clients = new HashMap<String, MongoClient>();
	
	private static String getConnStr(String hostAddr, int port){
		return hostAddr + ":" + port;
	} 
	
	public static MongoClient getInstance(String hostAddr, int port){
		String connStr = getConnStr(hostAddr, port);
		
		if(clients.get(connStr) == null){
			MongoClient client = MongoUtil.createMongoClient(hostAddr, port);
			clients.put(connStr, client);
			return client;
		}
		else{
			return clients.get(connStr);
		}
	}
	
	private SingletonMongoClient(){
		
	}
	
	public static MongoClient getDefaultLocalClient(){
		return getInstance("127.0.0.1", 27017);
	}
}
