package com.hardrock.sample;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.beanutils.PropertyUtils;

import com.hardrock.mongo.MongoObject;

public class MongoObjectProxy {
	
	public static <T extends MongoObject> T create(final Class<T> clazz){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MethodInterceptor() {
			
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
				return proxy.invokeSuper(obj, args);
			}
		});
		
		T mo = (T) enhancer.create(); 
		mo.assignCglibInheritClass(clazz);
		return mo;
	}
	
	public static void main(String[] args) {
//		MongoObject sample = ProxyClass.createProxy(MongoObject.class);
//		
//		sample.setCollection("Acct");
//		sample.setName("lixi");
//		System.out.println("bo:" + sample.getBo());
	}
}
