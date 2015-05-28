package com.hardrock.mongo;

public class MongoConnectionException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1472670295129078471L;

	public MongoConnectionException(String msg){
		super(msg);
	}
	
	public MongoConnectionException(Throwable e){
		super(e);
	}
	
	public MongoConnectionException(String msg, Throwable e){
		super(msg, e);
	}
}
