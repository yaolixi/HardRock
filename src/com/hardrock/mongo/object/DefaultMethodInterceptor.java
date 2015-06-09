package com.hardrock.mongo.object;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.beanutils.PropertyUtils;

import com.mongodb.MongoClient;

public class DefaultMethodInterceptor <T extends MongoObject> implements MethodInterceptor{
	
	private Class<T> clazz;
	private String dbName;
	private MongoClient client;
	
	public DefaultMethodInterceptor(Class<T> clazz, String dbName, MongoClient client){
		this.clazz = clazz;
		this.dbName = dbName;
		this.client = client;
	}
	
	public DefaultMethodInterceptor(Class<T> clazz, String dbName){
		this.clazz = clazz;
		this.dbName = dbName;
	}
	
	public DefaultMethodInterceptor(Class<T> clazz){
		this.clazz = clazz;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(method.getName().indexOf("set") == 0){
			
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
			for (int i = 0; i < pds.length; i++) {
				Method writeMethod = PropertyUtils.getWriteMethod(pds[i]);
				//这里增加判断，如果是有外键关联， 则可以按照外键关联进行深入查询
				if(writeMethod != null && writeMethod.getName().equals(method.getName())){
					((MongoObject)obj).buildCriteria().put(pds[i].getName(), args[0]);
					break;
				}
			}
		}
		if(dbName != null && method.getName().equals("getDBName")){
			return dbName;
		}
		if(client != null && method.getName().equals("getMongoClient")){
			return client;
		}
		return proxy.invokeSuper(obj, args);
	}
}
