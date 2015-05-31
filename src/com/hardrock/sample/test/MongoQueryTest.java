package com.hardrock.sample.test;

import java.util.ArrayList;
import java.util.Collection;

import com.hardrock.mongo.DefaultMongoExecutor;
import com.hardrock.mongo.MongoQueryCondition;
import com.hardrock.mongo.SingletonMongoClient;
import com.hardrock.sample.SampleMongoQuery;
import com.hardrock.sample.model.Acct;
import com.hardrock.sample.model.Mkt;
import com.hardrock.sample.model.Prod;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoQueryTest {
	public static void main(String[] args) {
		Mkt mkt = new Mkt();
		mkt.setId(4);
		mkt.setCode("002");
		mkt.setName("Test");
		
		DefaultMongoExecutor.getInstance("isys", SingletonMongoClient.getDefaultLocalClient()).insert(mkt);
		
	}
	
	public static void findProdByMktCode(){
		SampleMongoQuery<Mkt> mktQuery = new SampleMongoQuery<Mkt>(Mkt.class);
		mktQuery.setQueryCondition(new MongoQueryCondition(new BasicDBObject("code","001")));
		Collection<Mkt> mkts = mktQuery.findAll();
		
		BasicDBObject criteria = new BasicDBObject();
		BasicDBList dl = new BasicDBList();
		for(Mkt mkt : mkts){
			dl.add(mkt.getId());
		}
		criteria.append("mktId", new BasicDBObject("$in", dl));
		
		SampleMongoQuery<Prod> query = new SampleMongoQuery<Prod>(Prod.class);
		query.setQueryCondition(new MongoQueryCondition(criteria));
		System.out.println(query.findAll());
	}
	
	public static void findOneRecord(){
		SampleMongoQuery<Prod> query = new SampleMongoQuery<Prod>(Prod.class);
		query.setQueryCondition(new MongoQueryCondition(new BasicDBObject("id", 2)));
		System.out.println(query.findOne());
	}
	
	public static void findAllRecord(){
		SampleMongoQuery<Prod> query = new SampleMongoQuery<Prod>(Prod.class);
		query.setQueryCondition(new MongoQueryCondition(new BasicDBObject()));
		System.out.println(query.findAll());
	}
	
	public static void upsertOneRecord(){
		Prod prod = new Prod();
		prod.setId(1);
		prod.setCode("600100");
		prod.setMktId(1);
		prod.setIsAtomic(true);
		prod.setName("≤‚ ‘¥˙¬Î");
		prod.setProdType(Prod.PROD_TYPE_STOCK_A);
		prod.save();
	}
	
	public static void deleteOneRecord(){
		Prod prod = new Prod();
		prod.setId(1);
		prod.delete();
	}
	
	public static void deleteAllRecords(){
		DefaultMongoExecutor.getInstance("Sample").deleteAll(Prod.class.getSimpleName());
	}
	
	public static void insertAllProds(){
		Collection<Object> prods = new ArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			Prod prod = new Prod();
			prod.setId(i + 1);
			prod.setCode("600100");
			prod.setMktId(1);
			prod.setAcctId(1);
			prod.setIsAtomic(true);
			prod.setName("≤‚ ‘¥˙¬Î");
			prod.setProdType(Prod.PROD_TYPE_STOCK_A);
			prods.add(prod);
		}
		
		DefaultMongoExecutor.getInstance("Sample").insert(prods);
	}
	
	public static void insertAllMkts(){
		Collection<Object> objs = new ArrayList<Object>();
		for (int i = 0; i < 2; i++) {
			Mkt mkt = new Mkt();
			mkt.setId(1 + i);
			mkt.setCode("00" + i);
			mkt.setName("Test");
			objs.add(mkt);
		}
		
		DefaultMongoExecutor.getInstance("Sample").insert(objs);
	}
	
	public static void insertAllAccts(){
		Collection<Object> objs = new ArrayList<Object>();
		for (int i = 0; i < 2; i++) {
			Acct acct = new Acct();
			acct.setId(1 + i);
			acct.setCode("" + i);
			acct.setName("Test Acct");
			objs.add(acct);
		}
		
		DefaultMongoExecutor.getInstance("Sample").insert(objs);
	}
}
