package com.hardrock.mongo.criteria;

import java.util.Collection;

import org.bson.BSONObject;

import com.hardrock.mongo.util.BSONType;
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
	 * $text performs a text search on the content of the fields indexed with a text index
	 * 
	 * Restrictions
     * A query can specify, at most, one $text expression.
     * The $text query can not appear in $nor expressions.
     * To use a $text query in an $or expression, all clauses in the $or array must be indexed.
     * You cannot use hint() if the query includes a $text query expression.
     * You cannot specify $natural sort order if the query includes a $text expression.
     * You cannot combine the $text expression, which requires a special text index, with a query operator that requires a different type of special index. For example you cannot combine $text expression with the $near operator.
	 * 
	 * @param search
	 * @param language
	 * @return
	 */
	public static Criterion text(String search, String language){
		Criterion criterion = new Criterion();
		if(language == null){
			criterion.getBo().append("$text", new BasicDBObject("$search", search));
		}
		else{
			criterion.getBo().append("$text", new BasicDBObject("$search", search).append("$language", language));
		}
		return criterion;
	}
	
	/**
	 * Provides regular expression capabilities for pattern matching strings in queries. 
	 * MongoDB uses Perl compatible regular expressions (i.e. ¡°PCRE¡± ) version 8.36 with UTF-8 support.

		To use $regex, use one of the following syntax:

		{ <field>: { $regex: /pattern/, $options: '<options>' } }
		{ <field>: { $regex: 'pattern', $options: '<options>' } }
		{ <field>: { $regex: /pattern/<options> } }

	 * @param property
	 * @param regex
	 * @return
	 */
	public static Criterion regex(String property, String regex){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$regex", regex));
		return criterion;
	}
	
	/**
	 * 
	 * @param property
	 * @param regex
	 * @param options   imxs
	 * @return
	 */
	public static Criterion regex(String property, String regex, String options){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$regex", regex).append("$options", options));
		return criterion;
	}
	
	/**
	 * 
	 * Use the $where operator to pass either a string containing a JavaScript expression or a full JavaScript function to the query system. 
	 * The $where provides greater flexibility, but requires that the database processes the JavaScript expression or function for each document in the collection. 
	 * Reference the document in the JavaScript expression or function using either this or obj .
	 * 
	 * @param script
	 * @return
	 */
	public static Criterion where(String script){
		Criterion criterion = new Criterion();
		criterion.getBo().append("$where", script);
		return criterion;
	}
	
	/**
	 * Element Selectors
	 * $exists 	Matches documents that have the specified field.
	 * $type 	Selects documents if a field is of the specified type.
	 */
	
	/**
	 * When <boolean> is true, $exists matches the documents that contain the field, including documents where the field value is null. 
	 * If <boolean> is false, the query returns only the documents that do not contain the field.
	 * @param property
	 * @param exists
	 * @return
	 */
	public static Criterion exists(String property, boolean exists){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$exists", exists));
		return criterion;
	}
	
	/**
	 * $type selects the documents where the value of the field is an instance of the specified numeric BSON type. 
	 * This is useful when dealing with highly unstructured data where data types are not predictable.
	 * 
	 * When applied to arrays, $type matches any inner element that is of the specified type. 
	 * Without projection this means that the entire array will match if any element has the right type. 
	 * With projection, the results will include just those elements of the requested type.
	 * @param property
	 * @param bsonType
	 * @return
	 */
	public static Criterion type(String property, BSONType bsonType){
		Criterion criterion = new Criterion();
		criterion.getBo().append(property, new BasicDBObject("$type", bsonType.getNumber()));
		return criterion;
	}
}
