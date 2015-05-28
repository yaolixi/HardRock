package com.hardrock.sample;

import java.util.Collection;
import java.util.Date;

import com.hardrock.mongo.MongoObject;
import com.hardrock.sample.model.Mkt;
import com.hardrock.sample.model.Prod;
import com.hardrock.sample.model.ProdStatus;

public class ObjectQuerySample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		findByDate();
	}
	
	/**
	 * 单独条件查询
	 */
	public static void findProdbyId(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.setId(1);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	/**
	 * 根据一个外键关联查询
	 */
	public static void findProdbyFK(){
		
		Mkt mkt = MongoObjectProxy.create(Mkt.class);
		mkt.setCode("000");
		
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.bindForeignKey("mktId", mkt);
		Collection<MongoObject> prods = prod.sortDesc("id").retainFields(new String[]{"name", "code"}).limit(1).skip(1).find();
		System.out.println(prods);
		
//		SampleMongoQuery<Prod> query = new SampleMongoQuery<Prod>(Prod.class);
//		query.setQueryCondition(new MongoQueryCondition(prod.buildCriteria()));
//		System.out.println(query.findAll());
	}
	
	/**
	 * 
	 */
	public static void findBy2LevelFK(){
		
		Mkt mkt = MongoObjectProxy.create(Mkt.class);
		mkt.setCode("001");
		
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.bindForeignKey("mktId", mkt);
		
		ProdStatus status = MongoObjectProxy.create(ProdStatus.class);
		status.bindForeignKey("prodId", prod);
		
		Collection<MongoObject> result = status.find();
		System.out.println(result);
	}
	
	public static void findByDate(){
		
		ProdStatus status = MongoObjectProxy.create(ProdStatus.class);
		
		Collection<MongoObject> result = status.lt("statusBeginDate", new Date()).lt("statusEndDate", new Date()).find();
		System.out.println(result);
	}
	
	public static void createProdStatus(){
		ProdStatus status = new ProdStatus();
		status.setProdId(10);
		status.setStatus("P");
		status.setStatusBeginDate(new Date());
		status.setStatusEndDate(new Date());
		status.save();
	}
}
