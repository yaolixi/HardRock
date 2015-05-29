package com.hardrock.mongo.criteria;

import com.mongodb.BasicDBObject;

public class Criterion {
	
	private BasicDBObject bo;
	
	public BasicDBObject getBo(){
		if(bo == null){
			bo = new BasicDBObject();
		}
		return bo;
	}
}
