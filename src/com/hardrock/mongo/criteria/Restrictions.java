package com.hardrock.mongo.criteria;

import java.util.Collection;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class Restrictions {
	/**
	 * Comparison Selectors
	 * $eq 	Matches values that are equal to a specified value.
	 * $gt 	Matches values that are greater than a specified value.
	 * $gte Matches values that are greater than or equal to a specified value.
	 * $lt 	Matches values that are less than a specified value.
	 * $lte Matches values that are less than or equal to a specified value.
	 * $ne 	Matches all values that are not equal to a specified value.
	 * $in 	Matches any of the values specified in an array.
	 * $nin Matches none of the values specified in an array.
	 */
	
	public static Criterion eq(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, obj);
//		above is the same as criterion.getBo().append(property, new BasicDBObject("$eq", obj));
		return criterion;
	}
	
	public static Criterion ne(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$ne", obj));
		return criterion;
	}
	
	public static Criterion lt(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$lt", obj));
		return criterion;
	}
	
	public static Criterion lte(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$lte", obj));
		return criterion;
	}
	
	public static Criterion gt(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$gt", obj));
		return criterion;
	}
	
	public static Criterion gte(String property, Object obj){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$gte", obj));
		return criterion;
	}
	
	public static Criterion in(String property, Object[] objs){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for(Object obj : objs){
			dl.add(obj);
		}
		criterion.getBo().append(property, new BasicDBObject("$in", dl));
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
	
	public static Criterion nin(String property, Object[] objs){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for(Object obj : objs){
			dl.add(obj);
		}
		criterion.getBo().append(property, new BasicDBObject("$nin", dl));
		return criterion;
	}
	
	public static Criterion nin(String property, Collection<? extends Object> objs){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for(Object obj : objs){
			dl.add(obj);
		}
		criterion.getBo().append(property, new BasicDBObject("$nin", dl));
		return criterion;
	}
	
	/**
	 *  Logical Selectors
	 *  $or 	Joins query clauses with a logical OR returns all documents that match the conditions of either clause.
	 *  $and 	Joins query clauses with a logical AND returns all documents that match the conditions of both clauses.
	 *  $not 	Inverts the effect of a query expression and returns documents that do not match the query expression.
	 *  $nor 	Joins query clauses with a logical NOR returns all documents that fail to match both clauses.
	 */
	
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
	
	public static Criterion not(Criterion cri){
		Criterion criterion = new Criterion();
		criterion.getBo().append("$not", cri.getBo());
		return criterion;
	}
	
	public static Criterion nor(Criterion ...args){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		for (int i = 0; i < args.length; i++) {
			dl.add(args[i].getBo());
		}
		criterion.getBo().append("$nor", dl);
		return criterion;
	}
	
	/**
	 * Evaluation Selectors
	 * $mod 	Performs a modulo operation on the value of a field and selects documents with a specified result.
	 * $regex 	Selects documents where values match a specified regular expression.
	 * $text 	Performs text search.
	 * $where 	Matches documents that satisfy a JavaScript expression. 
	 */
	
	public static Criterion mod(String property, int divisor, int remainder){
		Criterion criterion = new Criterion();
		BasicDBList dl = new BasicDBList();
		dl.add(divisor);
		dl.add(remainder);
		criterion.getBo().append(property, new BasicDBObject("$mod", dl));
		return criterion;
	}
	
	/**
	 * Element Selectors
	 * $exists 	Matches documents that have the specified field.
	 * $type 	Selects documents if a field is of the specified type.
	 */
}
