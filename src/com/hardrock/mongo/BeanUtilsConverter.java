package com.hardrock.mongo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BeanUtilsConverter implements BSONBeanConverter{

	@Override
	public <T> Object convert(DBObject bo, Class<T> beanClass) {
		Object obj = null;
		try {
			obj = beanClass.newInstance();
			BeanUtilsConverter.copyProperties(obj, (BasicDBObject) bo);
		} catch (Exception e) {
			throw new MongoExecutionException(e);
		}
		return obj;
	}
	
	public static void copyProperties(Object dest, Map orig) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Iterator names = ((Map) orig).keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			Object value = ((Map) orig).get(name);
			if (value instanceof Map) {
				PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(dest, name);
				if(descriptor.getPropertyType().getName().equals("java.lang.Object")){
					setSimpleProperty(dest, name, value);
				}
				else{
					Object property = descriptor.getPropertyType().newInstance();
					copyProperties(property, (Map)value);
					setSimpleProperty(dest, name, property);
				}
			}
			else if(value instanceof ObjectId){
				
			}
			else {
				setSimpleProperty(dest, name, value);
			}
		}
	}

	public static void setSimpleProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		// Retrieve the property setter method for the specified property
		PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, name);
		if (descriptor == null) {
			System.out.println("Unknown property '" + name + "'");
			return;
		}
		
		Method writeMethod = PropertyUtils.getWriteMethod(descriptor);
//		Method writeMethod = descriptor.getWriteMethod();
		if (writeMethod == null) {
			throw new NoSuchMethodException("Property '" + name + "' has no setter method");
		}

		// Call the property setter method
		Object values[] = new Object[1];
		values[0] = value;
		try {
			writeMethod.invoke(bean, values);
		} catch (IllegalArgumentException e) {
			System.out.println("warning on property name:" + name + "..." + e.getMessage());
		}
	}
}
