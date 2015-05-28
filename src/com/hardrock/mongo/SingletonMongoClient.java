package com.hardrock.mongo;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class SingletonMongoClient {
	private static MongoClient client;
	private static String hostAddr = null;
	public static MongoClient getInstance(){
		if (client == null){
			try {
				if(hostAddr == null){
					hostAddr = "127.0.0.1";
				}
				
				client = MongoUtil.createMongoClient(hostAddr, 27017);
			} catch (UnknownHostException e) {
				throw new MongoConnectionException(e);
			}
		}
		return client;
	}
	private SingletonMongoClient(){
		
	}
	
	/**
	 * ����һ�µ�ַ
	 * @param addr
	 */
	public static void setServerAddress(String addr){
		hostAddr = addr;
	}
}
