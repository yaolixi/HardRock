package com.hardrock.mongo.criteria;

import java.util.Collection;
import java.util.Date;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class Restrictions {
	public static Criterion or(Criterion ...args){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for (int i = 0; i < args.length; i++) {
			dl.add(args[i].getBo());
		}
		criterion.getBo().append("$or", dl);
		return criterion;
	}
	
	public static Criterion and(Criterion ...args){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for (int i = 0; i < args.length; i++) {
			dl.add(args[i].getBo());
		}
		criterion.getBo().append("$and", dl);
		return criterion;
	}
	
	public static Criterion eq(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, obj);
		return criterion;
	}
	
	public static Criterion in(String property, Collection<? extends Object> objs){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for(Object obj : objs){
			dl.add(obj);
		}
		criterion.getBo().append(property, new BasicDBObject("$in", dl));
		return criterion;
	}
	
	public static Criterion lt(String property, Date date){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$lt", date));
		return criterion;
	}
	
	public static Criterion lte(String property, Date date){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$lte", date));
		return criterion;
	}
	
	public static Criterion gt(String property, Date date){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$gt", date));
		return criterion;
	}
	
	public static Criterion gte(String property, Date date){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$gte", date));
		return criterion;
	}
}
