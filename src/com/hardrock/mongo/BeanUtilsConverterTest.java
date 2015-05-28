package com.hardrock.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BeanUtilsConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testProd() {
//		MongoClient mongoClient = SingletonMongoClient.getInstance();
//		DB db = mongoClient.getDB(MongoUtil.ISYS_DB_NAME);
//		DBCollection coll = db.getCollection(Prod.class.getSimpleName());
//		
//		BasicDBObject bo = (BasicDBObject)coll.findOne(new BasicDBObject("id", 101108));
//		
//		Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
//		Prod aObj = GSON.fromJson(bo.toString(), Prod.class);
//		Prod obj = (Prod) new BeanUtilsConverter().convert(bo, Prod.class);
//		assertEquals(obj.toString(), aObj.toString());
	}
	
	@Test
	public void testIndicatorTmpl() {
//		MongoClient mongoClient = SingletonMongoClient.getInstance();
//		DB db = mongoClient.getDB(MongoUtil.ISYS_DB_NAME);
//		DBCollection coll = db.getCollection(IndicatorTmpl.class.getSimpleName());
//		
//		BasicDBObject bo = (BasicDBObject)coll.findOne(new BasicDBObject("id", 4));
//		
//		Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
//		IndicatorTmpl aObj = GSON.fromJson(bo.toString(), IndicatorTmpl.class);
//		IndicatorTmpl obj = (IndicatorTmpl) new BeanUtilsConverter().convert(bo, IndicatorTmpl.class);
//		assertEquals(obj.toString(), aObj.toString());
	}
	
	public void testMkt() {
//		MongoClient mongoClient = SingletonMongoClient.getInstance();
//		DB db = mongoClient.getDB(MongoUtil.ISYS_DB_NAME);
//		DBCollection coll = db.getCollection(Mkt.class.getSimpleName());
//		
//		BasicDBObject bo = (BasicDBObject)coll.findOne(new BasicDBObject("id", 1));
//		
//		Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
//		Mkt aObj = GSON.fromJson(bo.toString(), Mkt.class);
//		Mkt obj = (Mkt) new BeanUtilsConverter().convert(bo, Mkt.class);
//		System.out.println(aObj);
//		System.out.println(obj);
//		assertEquals(obj.toString(), aObj.toString());
	}
	
	public void testAcctOrder() {
//		MongoClient mongoClient = SingletonMongoClient.getInstance();
//		DB db = mongoClient.getDB(MongoUtil.ISYS_DB_NAME);
//		DBCollection coll = db.getCollection(AcctOrder.class.getSimpleName());
//		
//		BasicDBObject bo = (BasicDBObject)coll.findOne(new BasicDBObject( "orderRefId",158424405003L));
//		
//		Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
//		AcctOrder aObj = GSON.fromJson(bo.toString(), AcctOrder.class);
//		AcctOrder obj = (AcctOrder) new BeanUtilsConverter().convert(bo, AcctOrder.class);
//		System.out.println(aObj.getExtInfo().toString());
//		System.out.println(obj.getExtInfo().toString());
//		assertEquals(obj.toString(), aObj.toString());
		
	}
	
	public void testSsTask() {
//		MongoClient mongoClient = SingletonMongoClient.getInstance();
//		DB db = mongoClient.getDB(MongoUtil.ISYS_DB_NAME);
//		DBCollection coll = db.getCollection(SsTask.class.getSimpleName());
//		
//		BasicDBObject bo = (BasicDBObject)coll.findOne(new BasicDBObject( "id",200002));
//		
//		Gson GSON = GsonTypeAdapter.getGsonBuilder(GsonAdapterType.DESERIALIZER).create();
//		SsTask aObj = GSON.fromJson(bo.toString(), SsTask.class);
//		SsTask obj = (SsTask) new BeanUtilsConverter().convert(bo, SsTask.class);
//		assertEquals(obj.toString(), aObj.toString());
	}
}
