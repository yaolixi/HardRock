package com.hardrock.mongo;

public class MongoExecutionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -470082650672673009L;
	
	public MongoExecutionException(String msg){
		super(msg);
	}
	
	public MongoExecutionException(Throwable e){
		super(e);
	}
	
	public MongoExecutionException(String msg, Throwable e){
		super(msg, e);
	}
}
