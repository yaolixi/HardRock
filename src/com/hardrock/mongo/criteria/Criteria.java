package com.hardrock.mongo.criteria;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;

public class Criteria {
	
	private BasicDBObject criteria;
	
	public Criteria add(Criterion criterion){
		if(criteria == null){
			criteria = new BasicDBObject();
		}
		criteria.putAll((BSONObject)criterion.getBo());
		return this;
	}
	
	public BasicDBObject getCriteria(){
		return criteria;
	}
	
	public static void main(String[] args) {
		System.out.println(Restrictions.eq("a", 1).getBo());
		Collection<String> ss = new ArrayList<String>();
		ss.add("OK");
		System.out.println(new Criteria().add(Restrictions.eq("a", 1)).add(Restrictions.in("ss", ss)).getCriteria());
	}
}
