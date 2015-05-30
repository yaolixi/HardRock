package com.hardrock.mongo.object;

import net.sf.cglib.proxy.Enhancer;

import com.mongodb.MongoClient;

public class MongoObjectProxy {
	
	public static <T extends MongoObject> T create(Class<T> clazz){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new DefaultMethodInterceptor<T>(clazz));
		
		@SuppressWarnings("unchecked")
		T mo = (T) enhancer.create();
		mo.assignCglibInheritClass(clazz);
		return mo;
	}
	
	public static <T extends MongoObject> T create(Class<T> clazz, String dbName){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new DefaultMethodInterceptor<T>(clazz, dbName));
		
		@SuppressWarnings("unchecked")
		T mo = ((T) enhancer.create());
		mo.assignCglibInheritClass(clazz);
		return mo;
	}
	
	public static <T extends MongoObject> T create(Class<T> clazz, String dbName, MongoClient client){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new DefaultMethodInterceptor<T>(clazz, dbName, client));
		
		@SuppressWarnings("unchecked")
		T mo = ((T) enhancer.create());
		mo.assignCglibInheritClass(clazz);
		return mo;
	}
}
