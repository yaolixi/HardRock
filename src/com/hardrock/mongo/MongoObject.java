package com.hardrock.mongo;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.BSONObject;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hardrock.mongo.annotation.ForeignKey;
import com.hardrock.mongo.annotation.PrimaryKey;
import com.hardrock.mongo.criteria.Criteria;
import com.hardrock.sample.SampleMongoQuery;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public abstract class MongoObject implements MongoObjectInterface{
	
	public abstract String getDBName();
	
	protected void save(DBObject doc){
		String[] pk = null;
		PrimaryKey primaryKey = this.getClass().getAnnotation(PrimaryKey.class);
		if(primaryKey != null){
			pk = primaryKey.PK();
		}
		
		if(pk == null){
			MongoExecutor.insert(getDBName(), this.getClass().getSimpleName() , doc);
		}
		else{
			BasicDBObject criteria = new BasicDBObject();
			for (int i = 0; i < pk.length; i++) {
				try {
					criteria.append(pk[i], PropertyUtils.getProperty(this, pk[i]));
				} catch (Exception e) {
					throw new MongoExecutionException(e);
				} 
			}
			MongoExecutor.upsert(getDBName(), this.getClass().getSimpleName() , criteria, doc);
		}
	}
	
	@Override
	public void save() {
		String[] pk = null;
		PrimaryKey primaryKey = this.getClass().getAnnotation(PrimaryKey.class);
		if(primaryKey != null){
			pk = primaryKey.PK();
		}
		
		if(pk == null){
			MongoExecutor.insert(getDBName(), this);
		}
		else{
			BasicDBObject criteria = new BasicDBObject();
			for (int i = 0; i < pk.length; i++) {
				try {
					criteria.append(pk[i], PropertyUtils.getProperty(this, pk[i]));
				} catch (Exception e) {
					throw new MongoExecutionException(e);
				} 
			}
			MongoExecutor.upsert(getDBName(), this, criteria);
		}
	}

	@Override
	public void delete() {
		String[] pk = null;
		PrimaryKey primaryKey = this.getClass().getAnnotation(PrimaryKey.class);
		if(primaryKey != null){
			pk = primaryKey.PK();
		}
		if(pk != null){
			BasicDBObject criteria = new BasicDBObject();
			for (int i = 0; i < pk.length; i++) {
				try {
					criteria.append(pk[i], PropertyUtils.getProperty(this, pk[i]));
				} catch (Exception e) {
					throw new MongoExecutionException(e);
				} 
			}
			WriteResult writeResult = MongoExecutor.remove(this.getDBName(), this.getClass(), criteria);
//			System.out.println(writeResult);
		}
	}
	
	private Class actualClass(){
		if(cglibInheritClass == null){
			return this.getClass();
		}
		else{
			return cglibInheritClass;
		}
	}
	
	public Collection<MongoObject> find(){
		SampleMongoQuery<MongoObject> query = new SampleMongoQuery<MongoObject>(this.actualClass());
		queryConditionForQueryUsage.setCriteria(criteriaForQueryUsage);
		query.setQueryCondition(queryConditionForQueryUsage);
		return query.findAll();
	}
	
	public MongoObject sort(DBObject orderBy){
		queryConditionForQueryUsage.setOrderBy(orderBy);
		return this;
	}
	
	public MongoObject sortAsc(String property){
		DBObject orderBy = queryConditionForQueryUsage.getOrderBy();
		if(orderBy == null){
			orderBy = new BasicDBObject(property, 1);
			queryConditionForQueryUsage.setOrderBy(orderBy);
		}
		else{
			orderBy.put(property, 1);
		}
		return this;
	}
	
	public MongoObject sortAsc(String[] properties){
		DBObject orderBy = queryConditionForQueryUsage.getOrderBy();
		if(orderBy == null){
			orderBy = new BasicDBObject();
			for (int i = 0; i < properties.length; i++) {
				orderBy.put(properties[i], 1);
			}
			queryConditionForQueryUsage.setOrderBy(orderBy);
		}
		else{
			for (int i = 0; i < properties.length; i++) {
				orderBy.put(properties[i], 1);
			}
		}
		return this;
	}
	
	public MongoObject sortDesc(String property){
		DBObject orderBy = queryConditionForQueryUsage.getOrderBy();
		if(orderBy == null){
			orderBy = new BasicDBObject(property, -1);
			queryConditionForQueryUsage.setOrderBy(orderBy);
		}
		else{
			orderBy.put(property, -1);
		}
		return this;
	}
	
	public MongoObject sortDesc(String[] properties){
		DBObject orderBy = queryConditionForQueryUsage.getOrderBy();
		if(orderBy == null){
			orderBy = new BasicDBObject();
			for (int i = 0; i < properties.length; i++) {
				orderBy.put(properties[i], -1);
			}
			queryConditionForQueryUsage.setOrderBy(orderBy);
		}
		else{
			for (int i = 0; i < properties.length; i++) {
				orderBy.put(properties[i], -1);
			}
		}
		return this;
	}
	
	public MongoObject limit(int limit){
		queryConditionForQueryUsage.setLimit(limit);
		return this;
	}
	
	public MongoObject skip(int skip){
		queryConditionForQueryUsage.setSkip(skip);
		return this;
	}
	
	public MongoObject fields(BasicDBObject fields){
		queryConditionForQueryUsage.setFields(fields);
		return this;
	}
	
	public MongoObject removeFields(String[] fields){
		BasicDBObject bo = queryConditionForQueryUsage.getFields();
		for (int i = 0; i < fields.length; i++) {
			bo.append(fields[i], 0);
		}
		queryConditionForQueryUsage.setFields(bo);
		return this;
	}
	
	public MongoObject retainFields(String[] fields){
		BasicDBObject bo = queryConditionForQueryUsage.getFields();
		for (int i = 0; i < fields.length; i++) {
			bo.append(fields[i], 1);
		}
		queryConditionForQueryUsage.setFields(bo);
		return this;
	}
	
	private ExclusionStrategy strategy = new ExclusionStrategy() {
		
		@Override
		public boolean shouldSkipField(FieldAttributes fa) {
			return fa.getName().equals("criteriaForQueryUsage") || fa.getName().equals("queryConditionForQueryUsage");
		}
		
		@Override
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}
	};
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setExclusionStrategies(strategy).create();
		return this.getClass().getSimpleName() + "=" + gson.toJson(this);
	}
	
	public boolean isEditableInProduction(){
		return true;
	}
	
	private BasicDBObject criteriaForQueryUsage = new BasicDBObject();
	private MongoQueryCondition queryConditionForQueryUsage = new MongoQueryCondition();
	
	public BasicDBObject buildCriteria(){
		return criteriaForQueryUsage;
	}
	
	public void addCriteria(Criteria criteria){
		if(criteria != null){
			criteriaForQueryUsage.putAll((BSONObject)criteria.getCriteria());
		}
	}
	
	private Class<? extends MongoObject> cglibInheritClass;
	
	public <T extends MongoObject> void assignCglibInheritClass(Class<T> cglibInheritClass){
		this.cglibInheritClass = cglibInheritClass;
	}
	
	public void bindForeignKey(String property, MongoObject obj){
		Field[] fields = this.actualClass().getDeclaredFields();
		
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].getName().equals(property)){
				ForeignKey foreignKey = fields[i].getAnnotation(ForeignKey.class);
				if(foreignKey != null){
					MongoQuery query = new MongoQuery(getDBName(), foreignKey.foreignClass().getSimpleName());
					query.setQueryCondition(new MongoQueryCondition(obj.buildCriteria()));
					Collection<?> fkResult = query.findAll(foreignKey.foreignClass());
					
					BasicDBList dl = new BasicDBList();
					
					for(Object mo : fkResult){
						try {
							dl.add(PropertyUtils.getProperty(mo, foreignKey.foreignKey()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					criteriaForQueryUsage.append(property, new BasicDBObject("$in", dl));
				}
			}
		}
	}
	
//	protected static Collection<? extends MongoObject> findAll(String dbName, Class<? extends MongoObject> clazz, MongoQueryCondition queryCondition){
//		MongoQuery query = new MongoQuery(dbName, clazz.getSimpleName());
//		query.setQueryCondition(queryCondition);
//		return query.findAll(clazz);
//	}
}
