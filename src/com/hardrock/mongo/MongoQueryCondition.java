package com.hardrock.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoQueryCondition {
	public MongoQueryCondition(){}
	
	public MongoQueryCondition(DBObject criteria){
		this.criteria = criteria;
	}
	
	public MongoQueryCondition(DBObject criteria, DBObject orderBy){
		this.criteria = criteria;
		this.orderBy = orderBy;
	}
	
	public MongoQueryCondition(DBObject criteria, DBObject orderBy, int skip, int limit){
		this.criteria = criteria;
		this.orderBy = orderBy;
		this.skip = skip;
		this.limit = limit;
	}
	
	public MongoQueryCondition(DBObject criteria, DBObject orderBy, BasicDBObject fields, int skip, int limit){
		this.criteria = criteria;
		this.orderBy = orderBy;
		this.fields = fields;
		this.skip = skip;
		this.limit = limit;
	}
	
	// 查询条件
	private DBObject criteria;
	// order by的条件
	private DBObject orderBy;
	// 需要跳过的记录，分页时需要使用，0标识不跳过
	private int skip = 0;
	// 限制返回的记录数目，默认为最大值，即不限制
	private int limit = Integer.MAX_VALUE;
	//返回的fields, 默认不返回_id
	private BasicDBObject fields = new BasicDBObject("_id",0);
	public DBObject getCriteria() {
		return criteria;
	}
	public void setCriteria(DBObject criteria) {
		this.criteria = criteria;
	}
	public DBObject getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(DBObject orderBy) {
		this.orderBy = orderBy;
	}
	public int getSkip() {
		return skip;
	}
	public void setSkip(int skip) {
		this.skip = skip;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public BasicDBObject getFields() {
		return fields;
	}
	public void setFields(BasicDBObject fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "MongoQueryCondition [criteria=" + criteria + ", orderBy=" + orderBy + ", skip=" + skip + ", limit=" + limit + ", fields=" + fields + "]";
	}
}
