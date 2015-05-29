package com.hardrock.sample.test;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import com.hardrock.mongo.MongoObject;
import com.hardrock.mongo.criteria.Criteria;
import com.hardrock.mongo.criteria.Restrictions;
import com.hardrock.mongo.util.MongoObjectProxy;
import com.hardrock.sample.model.Mkt;
import com.hardrock.sample.model.Prod;
import com.hardrock.sample.model.ProdStatus;

public class QueryByExampleTest {
	
	/**
	 * query by example
	 */
	@Test
	public void testFindProdbyId(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.setId(1);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	/**
	 * query by foreign key
	 */
	@Test
	public void testFindProdbyForeignKey(){
		Mkt mkt = MongoObjectProxy.create(Mkt.class);
		mkt.setCode("000");
		
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.bindForeignKey("mktId", mkt);
		Collection<MongoObject> prods = prod.sortDesc("id").retainFields(new String[]{"name", "code"}).limit(1).skip(1).find();
		System.out.println(prods);
	}
	
	/**
	 * query by 2 level foreign key
	 */
	@Test
	public void testFindBy2LevelForeignKey(){
		
		Mkt mkt = MongoObjectProxy.create(Mkt.class);
		mkt.setCode("001");
		
		Prod prod = MongoObjectProxy.create(Prod.class);
		prod.bindForeignKey("mktId", mkt);
		
		ProdStatus status = MongoObjectProxy.create(ProdStatus.class);
		status.bindForeignKey("prodId", prod);
		
		Collection<MongoObject> result = status.find();
		System.out.println(result);
	}
	
	/**
	 * query by date range
	 */
	@Test
	public void testFindByDate(){
		ProdStatus status = MongoObjectProxy.create(ProdStatus.class);
		status.setProdId(10);
		
		Criteria criteria = new Criteria()
				.add(Restrictions.lt("statusBeginDate", new Date()))
				.add(Restrictions.eq("prodId", 10));
		
		status.addCriteria(criteria);
		
		System.out.println(status.find());
	}
	
	/**
	 * query with or criteria
	 */
	@Test
	public void testFindWithOr(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		Criteria criteria = new Criteria().add(Restrictions.or(Restrictions.eq("id", 10), Restrictions.eq("mktId", 2)));
		prod.addCriteria(criteria);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	/**
	 * query with and and or criteria
	 */
	@Test
	public void findWithAndOR(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		
		Criteria criteria = new Criteria().add(
					Restrictions.or(
							Restrictions.and(Restrictions.eq("id", 9), Restrictions.eq("mktId", 1)), 
							Restrictions.and(Restrictions.eq("id", 10), Restrictions.eq("mktId", 2))
						)
					);
		prod.addCriteria(criteria);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	@Test
	public void testQueryWithLt(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		
		Criteria criteria = new Criteria().add(Restrictions.lt("prodType", "T"));
		prod.addCriteria(criteria);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	@Test
	public void testQueryWithMod(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		
		Criteria criteria = new Criteria().add(Restrictions.mod("id", 3, 1));
		prod.addCriteria(criteria);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
	
	@Test
	public void testQueryWithWhere(){
		Prod prod = MongoObjectProxy.create(Prod.class);
		
		Criteria criteria = new Criteria().add(Restrictions.where("this.mktId==2 || this.id==9"));
		prod.addCriteria(criteria);
		Collection<MongoObject> prods = prod.find();
		System.out.println(prods);
	}
}
