package com.hardrock.sample;

import java.util.ArrayList;
import java.util.Collection;

import com.hardrock.mongo.MongoQueryCondition;
import com.hardrock.sample.model.Acct;
import com.hardrock.sample.model.Mkt;
import com.hardrock.sample.model.Prod;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class Sample1 {
	public static void main(String[] args) {
		
	}
	
	/**
	 * �µĹ�����ѯ����
	 */
	public static void findProdByMktCodeNew(){
		Mkt mkt = (Mkt) MongoObjectProxy.create(Mkt.class);
		mkt.setCode("001");
		
		Prod prod = (Prod) MongoObjectProxy.create(Prod.class);
//		prod.setMktId(mkt.getId());
//		prod.foreignTo(mkt);
		
		SampleMongoQuery<Prod> query = new SampleMongoQuery<Prod>(Prod.class);
		query.setQueryCondition(new MongoQueryCondition(prod.buildCriteria()));
		System.out.println(query.findAll());
	}
	
	/**
	 * �ϵĹ�����ѯ
	 */
	public static void findProdByMktCodeOld(){
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
		prod.setName("���Դ���");
		prod.setProdType(Prod.PROD_TYPE_STOCK_A);
		prod.save();
	}
	
	public static void deleteOneRecord(){
		Prod prod = new Prod();
		prod.setId(1);
		prod.delete();
	}
	
	public static void deleteAllRecords(){
		SampleMongoExecutor.deleteAll(Prod.class.getSimpleName());
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
			prod.setName("���Դ���");
			prod.setProdType(Prod.PROD_TYPE_STOCK_A);
			prods.add(prod);
		}
		
		SampleMongoExecutor.insert(prods);
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
		
		SampleMongoExecutor.insert(objs);
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
		
		SampleMongoExecutor.insert(objs);
	}
}
