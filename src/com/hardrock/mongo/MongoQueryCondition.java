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
	
	// ��ѯ����
	private DBObject criteria;
	// order by������
	private DBObject orderBy;
	// ��Ҫ�����ļ�¼����ҳʱ��Ҫʹ�ã�0��ʶ������
	private int skip = 0;
	// ���Ʒ��صļ�¼��Ŀ��Ĭ��Ϊ���ֵ����������
	private int limit = Integer.MAX_VALUE;
	//���ص�fields, Ĭ�ϲ�����_id
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
